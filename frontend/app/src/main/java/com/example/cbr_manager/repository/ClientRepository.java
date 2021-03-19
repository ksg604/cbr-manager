package com.example.cbr_manager.repository;

import com.example.cbr_manager.data.storage.RoomDB;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.service.client.ClientSync;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ClientRepository {

    private ClientDao clientDao;

    private ClientAPI clientAPI;

    private String authHeader;

    private List<Client> clientCache;

    @Inject
    ClientRepository(ClientDao clientDao, ClientAPI clientAPI, String authHeader) {
        this.clientAPI = clientAPI;
        this.clientDao = clientDao;
        this.authHeader = authHeader;
        this.clientCache = new ArrayList<>();
    }

    public Observable<List<Client>> getAllClient(){
        clientCache.clear();
        return clientAPI.getAllClients(authHeader)
                .subscribeOn(Schedulers.io())
                .doOnNext(client -> clientCache.addAll(client))
                .doOnError((e) -> clientDao.getAllClients())
                .doOnComplete(() -> insertList(clientCache));
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
        RoomDB.databaseWriteExecutor.execute(()->{
            for(int i = 0; i < clients.size(); i++){
                clientDao.insert(clients.get(i));
            }
        });

    }

    public List<Client> getCache() {
        return this.clientCache;
    }

}
