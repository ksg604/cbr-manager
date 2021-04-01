package com.example.cbr_manager.repository;

import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.service.visit.VisitAPI;
import com.example.cbr_manager.service.visit.VisitDao;

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


    @Inject
    VisitRepository(VisitAPI visitAPI, VisitDao visitDao, String authHeader) {
        this.visitAPI = visitAPI;
        this.visitDao = visitDao;
        this.authHeader = authHeader;
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
        if (throwable instanceof SocketTimeoutException) {
            return visitDao.getVisits().toObservable().flatMap(Observable::fromIterable);
        }
        return Observable.error(throwable);
    }

    public Single<Visit> createVisit(Visit visit){
        return visitAPI.createVisitObs(authHeader, visit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
