package com.example.cbr_manager.repository;

import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.workmanager.client.CreateClientWorker;
import com.example.cbr_manager.workmanager.client.ModifyClientWorker;
import com.example.cbr_manager.workmanager.client.UploadPhotoWorker;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

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

    public LiveData<List<Client>> getClientsAsLiveData(){
        clientAPI.getClientsAsSingle(authHeader)
                .doOnSuccess(clientDao::insertAll)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<Client>>() {
                    @Override
                    public void onSuccess(@NonNull List<Client> clients) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
        return clientDao.getClientsLiveData();
    }

    public LiveData<Client> getClient(int id) {
        clientAPI.getClientSingle(authHeader, id)
                .doOnSuccess(clientDao::insert)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<Client>() {
                    @Override
                    public void onSuccess(@NonNull Client client) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
        return clientDao.getClientLiveData(id);
    }

    public Single<Client> createClient(Client client) {
        return Single.fromCallable(() -> clientDao.insert(client))
                .map(aLong -> {
                    client.setId(aLong.intValue());
                    return client;
                })
                .doOnSuccess(this::enqueueCreateClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable uploadPhoto(File file, Client client) {
        client.setPhotoURL(file.getAbsolutePath());
        return Completable.fromAction(() -> clientDao.update(client))
                .doOnComplete(() -> enqueueUploadPhoto(client))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable modifyClient(Client client) {
        return Completable.fromAction(() -> clientDao.update(client))
                .doOnComplete(() -> enqueueModifyClient(client))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private UUID enqueueCreateClient(Client client) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest createClientRequest =
                new OneTimeWorkRequest.Builder(CreateClientWorker.class)
                        .setConstraints(constraints)
                        .setInputData(CreateClientWorker.buildInputData(authHeader, client.getId()))
                        .build();
        workManager.enqueue(createClientRequest);
        return createClientRequest.getId();
    }

    private void enqueueUploadPhoto( Client client) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest uploadPhotoRequest =
                new OneTimeWorkRequest.Builder(UploadPhotoWorker.class)
                        .setConstraints(constraints)
                        .setInputData(UploadPhotoWorker.buildInputData(authHeader, client.getId(), client.getPhotoURL()))
                        .build();
        workManager.enqueue(uploadPhotoRequest);
    }

    private void enqueueModifyClient(Client client) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest modifyClientRequest =
                new OneTimeWorkRequest.Builder(ModifyClientWorker.class)
                        .setConstraints(constraints)
                        .setInputData(CreateClientWorker.buildInputData(authHeader, client.getId()))
                        .build();
        workManager.enqueue(modifyClientRequest);
    }

}
