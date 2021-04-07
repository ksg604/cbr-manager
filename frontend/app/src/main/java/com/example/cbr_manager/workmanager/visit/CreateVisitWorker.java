package com.example.cbr_manager.workmanager.visit;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Data;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.service.visit.VisitAPI;
import com.example.cbr_manager.service.visit.VisitDao;
import com.example.cbr_manager.utils.Helper;

import org.jetbrains.annotations.NotNull;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;

@HiltWorker
public class CreateVisitWorker extends RxWorker {

    public static final String KEY_AUTH_HEADER = "KEY_AUTH_HEADER";
    public static final String KEY_VISIT_OBJ_ID = "KEY_VISIT_OBJ_ID";
    private static final String TAG = CreateVisitWorker.class.getSimpleName();
    private final VisitAPI visitAPI;
    private final VisitDao visitDao;

    @AssistedInject
    public CreateVisitWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params, VisitAPI visitAPI, VisitDao visitDao) {
        super(context, params);

        this.visitAPI = visitAPI;
        this.visitDao = visitDao;
    }

    public static Data buildInputData(String authHeader, int visitId) {
        Data.Builder builder = new Data.Builder();
        builder.putString(CreateVisitWorker.KEY_AUTH_HEADER, authHeader);
        builder.putInt(CreateVisitWorker.KEY_VISIT_OBJ_ID, visitId);
        return builder.build();
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        String authHeader = getInputData().getString(KEY_AUTH_HEADER);
        int visitObjId = getInputData().getInt(KEY_VISIT_OBJ_ID, -1);

        return visitDao.getVisitAsSingle(visitObjId)
                .flatMap(visit -> visitAPI.createVisitSingle(authHeader, visit)
                        .doOnSuccess(visitResult -> onSuccessfulCreateVisit(visit, visitResult)))
                .map(visitSingle -> {
                    Log.d(TAG, "created Visit: " + visitSingle.getId());
                    return Result.success();
                })
                .onErrorReturn(this::handleReturnResult);
    }

    private void onSuccessfulCreateVisit(Visit visit, Visit visitResult) {
        Integer localId = visit.getId();
        visitResult.setId(localId);
        visitDao.update(visitResult);
    }

    @NotNull
    private Result handleReturnResult(Throwable throwable) {
        if (Helper.isConnectionError(throwable)) {
            return Result.retry();
        }
        return Result.failure();
    }
}
