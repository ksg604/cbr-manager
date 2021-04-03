package com.example.cbr_manager.repository;

import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.service.visit.VisitAPI;
import com.example.cbr_manager.service.visit.VisitDao;
import com.example.cbr_manager.workmanager.visit.CreateVisitWorker;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class VisitRepository {

    private final VisitAPI visitAPI;
    private final VisitDao visitDao;
    private final String authHeader;
    private WorkManager workManager;

    @Inject
    VisitRepository(VisitAPI visitAPI, VisitDao visitDao, String authHeader, WorkManager workManager) {
        this.visitAPI = visitAPI;
        this.visitDao = visitDao;
        this.authHeader = authHeader;
        this.workManager = workManager;
    }

    public LiveData<List<Visit>> getVisitsAsLiveData() {
        visitAPI.getVisitsAsSingle(authHeader)
                .doOnSuccess(visitDao::insertAll)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<Visit>>() {
                    @Override
                    public void onSuccess(@NonNull List<Visit> visits) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
        return visitDao.getVisitsAsLiveData();
    }

    public LiveData<Visit> getVisitAsLiveData(int id) {
        visitAPI.getVisitAsSingle(authHeader, id)
                .doOnSuccess(visitDao::insert)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<Visit>() {
                    @Override
                    public void onSuccess(@NonNull Visit visit) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
        return visitDao.getVisitAsLiveData(id);
    }

    public Single<Visit> createVisit(Visit visit) {
        return visitDao.insertSingle(visit)
                .map(aLong -> {
                    visit.setId(aLong.intValue());
                    return visit;
                })
                .doOnSuccess(this::enqueueCreateVisit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private UUID enqueueCreateVisit(Visit visit) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest createVisitRequest =
                new OneTimeWorkRequest.Builder(CreateVisitWorker.class)
                        .setConstraints(constraints)
                        .setInputData(CreateVisitWorker.buildInputData(authHeader, visit.getId()))
                        .build();
        workManager.enqueue(createVisitRequest);
        return createVisitRequest.getId();
    }
}
