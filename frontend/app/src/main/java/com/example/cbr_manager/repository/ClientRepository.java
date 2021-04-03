package com.example.cbr_manager.repository;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cbr_manager.data.storage.RoomDB;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.utils.Helper;
import com.example.cbr_manager.workmanager.client.CreateClientWorker;
import com.example.cbr_manager.workmanager.visit.CreateVisitWorker;

import org.threeten.bp.ZonedDateTime;

import java.io.File;
import java.net.ConnectException;
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
                .onErrorResumeNext(throwable -> clientDao.getClient(id))
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

    public Single<ResponseBody> uploadPhoto(File file, int clientId) {
        //TODO: Handle image caching offline on error (picasso?)
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
        return clientAPI.uploadPhotoSingle(authHeader, clientId, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




}
