package com.example.cbr_manager.repository;

import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.service.visit.VisitAPI;
import com.example.cbr_manager.service.visit.VisitDao;
import com.example.cbr_manager.utils.Helper;
import com.example.cbr_manager.workmanager.visit.CreateVisitWorker;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public Observable<Visit> getVisits() {
        return visitAPI.getVisitsObs(authHeader)
                .flatMap(Observable::fromIterable)
                .doOnNext(visitDao::insert)
                .onErrorResumeNext(this::handleLocalVisitsFallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<List<Visit>> getVisitsAsLiveData() {
        return visitDao.getVisitsAsLiveData();
    }

    public Single<Visit> getVisit(int id) {
        return visitAPI.getVisitObs(authHeader, id)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {
                    if (Helper.isConnectionError(throwable)){
                        return visitDao.getVisit(id);
                    }
                    return Single.error(throwable);
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private ObservableSource<? extends Visit> handleLocalVisitsFallback(Throwable throwable) {
        return visitDao.getVisits().toObservable().flatMap(Observable::fromIterable);
    }



    public Single<Visit> createVisit(Visit visit) {
        return visitDao.SingleInsert(visit)
                .map(aLong -> {
                    visit.setId(aLong.intValue());
                    return visit;
                })
                .doOnSuccess(visit1 -> {
                    UUID id =  enqueueCreateVisit(visit1);
                })
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
