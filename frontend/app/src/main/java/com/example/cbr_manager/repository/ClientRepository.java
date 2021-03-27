package com.example.cbr_manager.repository;

import com.example.cbr_manager.data.storage.RoomDB;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.utils.Helper;

import org.threeten.bp.ZonedDateTime;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ClientRepository {

    private ClientDao clientDao;
    private ClientAPI clientAPI;
    private String authHeader;

    private List<Client> localClient;

    @Inject
    ClientRepository(ClientDao clientDao, ClientAPI clientAPI, String authHeader) {
        this.clientAPI = clientAPI;
        this.clientDao = clientDao;
        this.authHeader = authHeader;
        this.localClient = new ArrayList<>();
    }

    public Observable<Client> getAllClient(){
        return clientAPI.getClientsObs(authHeader)
                .flatMap(Observable::fromIterable)
                .doOnNext(client -> clientDao.insert(client))
                .onErrorResumeNext(this::getLocalClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private ObservableSource<? extends Client> getLocalClient(Throwable throwable) {
        if(throwable instanceof SocketTimeoutException) {
            return clientDao.getClientsObs().flatMap(Observable::fromIterable);
        }
        return Observable.error(throwable);
    }

    public Single<Client> getClient(int id) {
        return clientAPI.getClientSingle(authHeader, id)
                .onErrorResumeNext(throwable -> clientDao.getClientSingle(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Client> insert(Client client) {
        return clientAPI.createClientSingle(authHeader, client)
                .onErrorResumeNext(throwable -> handleOfflineCreateClient(client, throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private SingleSource<? extends Client> handleOfflineCreateClient(Client client, Throwable throwable) {
        if(throwable instanceof SocketTimeoutException) {
            long id = clientDao.insert(client);
            client.setId((int)id);
            return Single.just(client);
        }
        return Single.error(throwable);
    }

    public Single<ResponseBody> uploadPhoto(File file, int clientId) {
        //TODO: Handle image caching offline on error (picasso?)
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
        return clientAPI.uploadPhotoSingle(authHeader, clientId, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Client> update(Client client) {
        return clientAPI.modifyClientSingle(authHeader, client.getId(), client)
                .onErrorResumeNext(throwable -> handleOfflineModifyClient(client, throwable))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private SingleSource<? extends Client> handleOfflineModifyClient(Client client, Throwable throwable) {
        if(throwable instanceof SocketTimeoutException) {
            clientDao.update(client);
            return Single.just(client);
        }
        return Single.error(throwable);
    }

    // Below are functions for synchronizing data from local and remote server

    private void uploadNewClient(List<Client> localClient) {
        for (int i = 0; i < localClient.size(); i++) {
            if (localClient.get(i).isNewClient()) {
                localClient.get(i).setNewClient(false);
                clientAPI.createClient(authHeader, localClient.get(i));
                File file = new File(localClient.get(i).getPhotoURL());
                RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
                clientAPI.uploadPhoto(authHeader, localClient.get(i).getId(), body);
                clientDao.delete(localClient.get(i));
            }
        }
    }

    private boolean matchID(Client client1, Client client2) {
        return client1.getId() == client2.getId();
    }

    private boolean compareZoneDateTime(String date1, String date2) {
        ZonedDateTime zdt1 = Helper.parseUTCDateTime(date1);
        ZonedDateTime zdt2 = Helper.parseUTCDateTime(date1);
        int result = zdt1.compareTo(zdt2);
        return result > 0;
    }

    private void updateLocal(List<Client> serverClient) {
        localClient = clientDao.getClients();
        int[] localUpdated = new int[localClient.size()];
        for (int i = 0; i < localClient.size(); i++) {
            for (int j = 0; j < serverClient.size(); j++) {
                if (matchID(localClient.get(i), serverClient.get(j))
                        && compareZoneDateTime(localClient.get(i).getUpdatedAt(), serverClient.get(j).getUpdatedAt())) {
                    // Need to modify and update since we know clients in local is modified after server client of same id
                    if (localUpdated[i] != 1) {
                        localUpdated[i] = 1;
                        clientAPI.modifyClient(authHeader, localClient.get(i).getId(), localClient.get(i));
                        File file = new File(localClient.get(i).getPhotoURL());
                        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
                        clientAPI.uploadPhoto(authHeader, localClient.get(i).getId(), body);
                    }
                }
            }

        }
    }

    private void downloadLocal(List<Client> serverClient) {
        clientDao.clearAll();
        for (int i = 0; i < serverClient.size(); i++) {
            clientDao.insert(serverClient.get(i));
        }
    }

    private void setNewClient(List<Client> localClient) {
        for (int i = 0; i < localClient.size(); i++) {
            localClient.get(i).setNewClient(false);
            clientDao.update(localClient.get(i));
        }
    }


    // Sync function concatenating multiple observable
    public Observable<List<Client>> sync() {
        Observable<List<Client>> uploadNew = clientDao.getClientsObs()
                .doOnNext(this::uploadNewClient);

        Observable<List<Client>> updateLocal = clientAPI.getClientsObs(authHeader)
                .doOnNext(this::updateLocal);

        Observable<List<Client>> downloadLocal = clientAPI.getClientsObs(authHeader)
                .doOnNext(this::downloadLocal);

        Observable<List<Client>> setNew = clientDao.getClientsObs()
                .doOnNext(this::setNewClient);

        return Observable.concat(uploadNew, updateLocal, downloadLocal, setNew)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
















}
