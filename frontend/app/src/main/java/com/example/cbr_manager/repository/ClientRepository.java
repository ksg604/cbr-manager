package com.example.cbr_manager.repository;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.workmanager.client.CreateClientWorker;
import com.example.cbr_manager.workmanager.client.UploadPhotoWorker;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ClientRepository {

    private final ClientDao clientDao;
    private final ClientAPI clientAPI;
    private final String authHeader;
    private WorkManager workManager;

    @Inject
    ClientRepository(ClientDao clientDao, ClientAPI clientAPI, String authHeader, WorkManager workManager) {
        this.clientAPI = clientAPI;
        this.clientDao = clientDao;
        this.authHeader = authHeader;
        this.workManager = workManager;
    }

    public Observable<Client> getAllClient(){
        return clientAPI.getClientsObs(authHeader)
                .flatMapIterable(clients -> clients)
                .doOnNext(clientDao::insert)
                .onErrorResumeNext(this::getLocalClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Client> getClient(int id) {
        return clientAPI.getClientSingle(authHeader, id)
                .onErrorResumeNext(throwable -> clientDao.getClientObs(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private ObservableSource<? extends Client> getLocalClient(Throwable throwable) {
        return clientDao.getClients().toObservable().flatMap(Observable::fromIterable);
    }


    public Single<Client> createClient(Client client) {
        return clientDao.SingleInsert(client)
                .map(aLong -> {
                    client.setId(aLong.intValue());
                    return client;
                })
                .doOnSuccess(this::enqueueCreateClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void enqueueCreateClient(Client client) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest createClientRequest =
                new OneTimeWorkRequest.Builder(CreateClientWorker.class)
                        .setConstraints(constraints)
                        .setInputData(CreateClientWorker.buildInputData(authHeader, client.getId()))
                        .build();
        workManager.enqueue(createClientRequest);
    }

    public Single<Client> uploadPhoto(File file, Client client) {
        client.setPhotoURL(file.getAbsolutePath());
        return clientDao.SingleInsert(client)
                .map(aLong -> {
                    client.setId(aLong.intValue());
                    return client;
                })
                .doOnSuccess(this::enqueueUploadPhoto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void enqueueUploadPhoto( Client client) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest uploadPhotoRequest =
                new OneTimeWorkRequest.Builder(UploadPhotoWorker.class)
                        .setConstraints(constraints)
                        .setInputData(UploadPhotoWorker.buildInputData(authHeader, client.getId()))
                        .build();
        workManager.enqueue(uploadPhotoRequest);
    }


}
