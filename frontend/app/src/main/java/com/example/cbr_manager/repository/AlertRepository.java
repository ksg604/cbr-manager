package com.example.cbr_manager.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.alert.AlertAPI;
import com.example.cbr_manager.service.alert.AlertDao;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.workmanager.alert.CreateAlertWorker;
import com.example.cbr_manager.workmanager.alert.ModifyAlertWorker;

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

public class AlertRepository {

    private final AlertDao alertDao;
    private final AlertAPI alertAPI;
    private final String authHeader;
    private WorkManager workManager;

    @Inject
    AlertRepository(AlertDao alertDao, AlertAPI alertAPI, String authHeader, WorkManager workManager) {
        this.alertAPI = alertAPI;
        this.alertDao = alertDao;
        this.authHeader = authHeader;
        this.workManager = workManager;
    }

    public LiveData<List<Alert>> getAlertsAsLiveData(){
        fetchAlertAsync();
        return alertDao.getAlertsLiveData();
    }

    public LiveData<List<Alert>> getAlertsAsLiveDataOffline(){
        return alertDao.getAlertsLiveData();
    }

    private void fetchAlertAsync() {
        alertAPI.getAlertsAsSingle(authHeader)
                .doOnSuccess(alerts -> {
                        for(Alert alert : alerts) {
                            insertAlertToLocalDB(alert);
                        }
                    }
                )
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<Alert>>() {
                    @Override
                    public void onSuccess(@NonNull List<Alert> alerts) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    private void insertAlertToLocalDB(Alert alert) {
        Alert localAlert = alertDao.getAlertByServerId(alert.getServerId());
        if(localAlert != null) {
            alert.setId(localAlert.getId());
//            alertDao.update(alert);
        }
        else {
            alertDao.insert(alert);
        }
    }

    public LiveData<Alert> getAlert(int id) {
        return alertDao.getAlertLiveData(id);
    }

    public Single<Alert> createAlert(Alert alert) {
        return Single.fromCallable(() -> alertDao.insert(alert))
                .map(aLong -> {
                    alert.setId(aLong.intValue());
                    return alert;
                })
                .doOnSuccess(this::enqueueCreateAlert)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private UUID enqueueCreateAlert(Alert alert) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest createAlertRequest =
                new OneTimeWorkRequest.Builder(CreateAlertWorker.class)
                        .setConstraints(constraints)
                        .setInputData(CreateAlertWorker.buildInputData(authHeader, alert.getId()))
                        .build();
        workManager.enqueue(createAlertRequest);
        return createAlertRequest.getId();
    }

    public Single<Alert> getAlertAsSingle(int id) {
        return alertDao.getAlertSingle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable modifyAlertOffline(Alert alert) {
        return Completable.fromAction(() -> alertDao.update(alert))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable modifyAlert(Alert alert) {
        return Completable.fromAction(() -> alertDao.update(alert))
                .doOnComplete(() -> enqueueModifyAlert(alert))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void enqueueModifyAlert(Alert alert) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest modifyAlertRequest =
                new OneTimeWorkRequest.Builder(ModifyAlertWorker.class)
                        .setConstraints(constraints)
                        .setInputData(ModifyAlertWorker.buildInputData(authHeader, alert.getId()))
                        .build();
        workManager.enqueue(modifyAlertRequest);
    }
}
