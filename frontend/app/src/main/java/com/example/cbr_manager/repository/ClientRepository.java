package com.example.cbr_manager.repository;

import com.example.cbr_manager.data.storage.RoomDB;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.utils.Helper;

import org.threeten.bp.ZonedDateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    public Observable<List<Client>> getAllClient(){
        return clientAPI.getAllClients(authHeader)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::insertList)
                .doOnError((e) -> clientDao.getAllClients())
                .doOnComplete(() -> {} );
    }

    public Disposable insert(Client client, File file) {
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
        return clientAPI.createClientRx(authHeader, client)
                .doOnComplete(() -> clientAPI.uploadPhotoRx(authHeader, client.getId(), body))
                .mergeWith(clientDao.insertRx(client))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Disposable update(Client client, File file) {
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
        return clientAPI.modifyClientRx(authHeader, client.getId(), client)
                .doOnComplete(() -> clientAPI.uploadPhotoRx(authHeader, client.getId(), body))
                .mergeWith(clientDao.updateRx(client))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void insertList(List<Client> clients) {
        for(int i = 0; i < clients.size(); i++) {
            clientDao.insert(clients.get(i));
        }
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
        Observable<List<Client>> uploadNew = clientDao.getAllClients()
                .doOnNext(this::uploadNewClient);

        Observable<List<Client>> updateLocal = clientAPI.getAllClients(authHeader)
                .doOnNext(this::updateLocal);

        Observable<List<Client>> downloadLocal = clientAPI.getAllClients(authHeader)
                .doOnNext(this::downloadLocal);

        Observable<List<Client>> setNew = clientDao.getAllClients()
                .doOnNext(this::setNewClient);

        return Observable.concat(uploadNew, updateLocal, downloadLocal, setNew)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
















}
