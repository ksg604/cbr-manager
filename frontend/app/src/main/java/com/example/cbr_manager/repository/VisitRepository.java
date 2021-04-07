package com.example.cbr_manager.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.service.visit.VisitAPI;
import com.example.cbr_manager.service.visit.VisitDao;
import com.example.cbr_manager.workmanager.visit.CreateVisitWorker;
import com.example.cbr_manager.workmanager.visit.ModifyVisitWorker;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Completable;
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

    private final static String TAG= VisitRepository.class.getSimpleName();

    @Inject
    VisitRepository(VisitAPI visitAPI, VisitDao visitDao, String authHeader, WorkManager workManager) {
        this.visitAPI = visitAPI;
        this.visitDao = visitDao;
        this.authHeader = authHeader;
        this.workManager = workManager;
    }

    public LiveData<List<Visit>> getVisitsAsLiveData() {
        visitAPI.getVisitsAsSingle(authHeader)
                .doOnSuccess(visits -> {
                            for (Visit v :
                                    visits) {
                                insertVisit(v);
                            }
                        }
                )
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
        visitDao.getVisitAsSingle(id).map(Visit::getServerId)
                .flatMap(serverId -> visitAPI.getVisitAsSingle(authHeader, serverId))
                .doOnSuccess(this::insertVisit)
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
        return Single.fromCallable(() -> visitDao.insert(visit))
                .map(aLong -> {
                    visit.setId(aLong.intValue());
                    return visit;
                })
                .doOnSuccess(this::enqueueCreateVisit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable updateVisit(Visit visit) {
        return Completable.fromAction(() -> visitDao.update(visit))
                .subscribeOn(Schedulers.io())
                .doOnComplete(() -> enqueueModifyVisit(visit))
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

    private void enqueueModifyVisit(Visit visit) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest createVisitRequest =
                new OneTimeWorkRequest.Builder(ModifyVisitWorker.class)
                        .setConstraints(constraints)
                        .setInputData(ModifyVisitWorker.buildInputData(authHeader, visit.getId()))
                        .build();
        workManager.enqueue(createVisitRequest);
    }

    private void insertVisit(Visit visit){
        Visit localVisit = visitDao.getVisitByServerId(visit.getServerId());
        if (localVisit != null) {
            visit.setId(localVisit.getId());
            visitDao.update(visit);
        } else {
            visitDao.insert(visit);
        }
    }
}
