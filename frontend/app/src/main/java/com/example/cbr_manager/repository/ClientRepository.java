package com.example.cbr_manager.repository;

import com.example.cbr_manager.data.storage.RoomDB;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ClientRepository {

    private ClientDao clientDao;

    private ClientAPI clientAPI;

    private String authHeader;


    @Inject
    ClientRepository(ClientDao clientDao, ClientAPI clientAPI, String authHeader) {
        this.clientAPI = clientAPI;
        this.clientDao = clientDao;
        this.authHeader = authHeader;
    }

    public Single<List<Client>> getAllClient(){
        return clientAPI.getAllClients(authHeader)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(client -> clientDao.insertAll(client))
                .onErrorResumeNext((e) -> clientDao.getAllClients());
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


}
