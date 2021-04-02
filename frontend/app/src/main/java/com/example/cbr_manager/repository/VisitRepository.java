package com.example.cbr_manager.repository;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.service.visit.VisitAPI;
import com.example.cbr_manager.service.visit.VisitDao;
import com.example.cbr_manager.workmanager.visit.CreateVisitWorker;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

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

    public Single<Visit> getVisit(int id) {
        return visitAPI.getVisitObs(authHeader, id)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> visitDao.getVisit(id))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private ObservableSource<? extends Visit> handleLocalVisitsFallback(Throwable throwable) {
        return visitDao.getVisits().toObservable().flatMap(Observable::fromIterable);
    }

    public Single<Visit> createVisit(Visit visit) {
        return visitDao.insertSingle(visit)
                .map(aLong -> {
                    visit.setId(aLong.intValue());
                    return visit;
                })
                .doOnSuccess(visit1 -> {
                    Constraints constraints = new Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build();
                    Data.Builder builder = new Data.Builder();
                    builder.putString(CreateVisitWorker.KEY_AUTH_HEADER, authHeader);
                    builder.putInt(CreateVisitWorker.KEY_VISIT_OBJ_ID, visit1.getId());
                    Data data = builder.build();

                    OneTimeWorkRequest createVisitRequest =
                            new OneTimeWorkRequest.Builder(CreateVisitWorker.class)
                                    .setConstraints(constraints)
                                    .setInputData(data)
                                    .build();
                    workManager.enqueue(createVisitRequest);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Single<Visit> offlineCreateVisit(Visit visit) {
        long id = visitDao.insert(visit);
        return visitDao.getVisit((int) id);
    }
}
