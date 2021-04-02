package com.example.cbr_manager.workmanager.visit;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import com.example.cbr_manager.service.visit.VisitAPI;
import com.example.cbr_manager.service.visit.VisitDao;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;

@HiltWorker
public class CreateVisitWorker extends RxWorker {

    public static final String KEY_AUTH_HEADER = "KEY_AUTH_HEADER";
    public static final String KEY_VISIT_OBJ_ID = "KEY_AUTH_HEADER";
    private static final String TAG = CreateVisitWorker.class.getSimpleName();
    private VisitAPI visitAPI;
    private VisitDao visitDao;

    @AssistedInject
    public CreateVisitWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params, VisitAPI visitAPI, VisitDao visitDao) {
        super(context, params);

        this.visitAPI = visitAPI;
        this.visitDao = visitDao;
    }

//    @Override
//    public Result doWork() {
//        String authHeader  = getInputData().getString(KEY_AUTH_HEADER);
//        int visitObjId  = getInputData().getInt(KEY_VISIT_OBJ_ID, -1);
//
//        Visit visit = visitDao.getVisitSync(visitObjId);
//
//
//        visitAPI.createVisitObs(authHeader, visit).subscribe(new DisposableSingleObserver<Visit>() {
//            @Override
//            public void onSuccess(@io.reactivex.annotations.NonNull Visit visit) {
//
//            }
//
//            @Override
//            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//            }
//        });
//        Log.d(TAG, "doWork: ");
//
//        // Indicate whether the work finished successfully with the Result
//        return Result.success();
//    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        String authHeader = getInputData().getString(KEY_AUTH_HEADER);
        int visitObjId = getInputData().getInt(KEY_VISIT_OBJ_ID, -1);

        return visitDao.getVisit(visitObjId)
                .flatMap(visit -> visitAPI.createVisitObs(authHeader, visit)
                        .doOnSuccess(visitResult -> {
                            visit.setId(visitResult.getId());
                            visitDao.update(visit);
                        }))
                .map(visitSingle -> Result.success())
                .onErrorReturn(throwable -> Result.failure())
                .onErrorReturnItem(Result.retry());
    }
}
