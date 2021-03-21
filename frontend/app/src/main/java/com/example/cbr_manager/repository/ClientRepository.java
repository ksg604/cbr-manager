package com.example.cbr_manager.repository;

import com.example.cbr_manager.data.storage.RoomDB;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.service.client.ClientSync;
import com.example.cbr_manager.utils.Helper;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

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

    public void insert(Client client) {
        RoomDB.databaseWriteExecutor.execute(()->{
            clientDao.insert(client);
        });
    }

    public void update(Client client) {
        RoomDB.databaseWriteExecutor.execute(()->{
            clientDao.update(client);
        });
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
                if (matchID(localClient.get(i), serverClient.get(j)) && compareZoneDateTime(localClient.get(i).getUpdatedAt(), serverClient.get(j).getUpdatedAt())) {
                    // Need to modify and update since we know clients in local is modified after server client of same id
                    if (localUpdated[i] != 1) {
                        localUpdated[i] = 1;
                        clientAPI.modifyClient(authHeader, localClient.get(i).getId(), localClient.get(i));
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
