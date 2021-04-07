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

    private final static String TAG = VisitRepository.class.getSimpleName();
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
        fetchVisitsAsync();
        return visitDao.getVisitsAsLiveData();
    }

    private void fetchVisitsAsync() {
        visitAPI.getVisitsAsSingle(authHeader)
                .doOnSuccess(visits -> {
                            for (Visit v :
                                    visits) {
                                insertVisitToLocalDB(v);
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
    }

    private void insertVisitToLocalDB(Visit visit) {
        Visit localVisit = visitDao.getVisitByServerId(visit.getServerId());
        if (localVisit != null) {
            visit.setId(localVisit.getId());
            visitDao.update(visit);
        } else {
            visitDao.insert(visit);
        }
    }

    public LiveData<Visit> getVisitAsLiveData(int id) {
        fetchVisitAsync(id);
        return visitDao.getVisitAsLiveData(id);
    }

    private void fetchVisitAsync(int id) {
        visitDao.getVisitAsSingle(id)
                .map(Visit::getServerId)
                .flatMap(serverId -> visitAPI.getVisitAsSingle(authHeader, serverId))
                .doOnSuccess(this::insertVisitToLocalDB)
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<Visit>() {
                    @Override
                    public void onSuccess(@NonNull Visit visit) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    public Single<Visit> createVisit(Visit visit) {
        return Single.fromCallable(() -> visitDao.insert(visit))
                .map(aLong -> {
                    visit.setId(aLong.intValue());
                    return visit;
                })
                .doOnSuccess(this::enqueueCreateVisitWorker)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable updateVisit(Visit visit) {
        return Completable.fromAction(() -> visitDao.update(visit))
                .subscribeOn(Schedulers.io())
                .doOnComplete(() -> enqueueModifyVisitWorker(visit))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private UUID enqueueCreateVisitWorker(Visit visit) {
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

    private void enqueueModifyVisitWorker(Visit visit) {
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
}
