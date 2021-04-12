package com.example.cbr_manager.workmanager.alert;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Data;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.alert.AlertAPI;
import com.example.cbr_manager.service.alert.AlertDao;
import com.example.cbr_manager.utils.Helper;

import org.jetbrains.annotations.NotNull;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;

@HiltWorker
public class CreateAlertWorker extends RxWorker {

    public static final String KEY_AUTH_HEADER = "KEY_AUTH_HEADER";
    public static final String KEY_ALERT_OBJ_ID = "KEY_ALERT_OBJ_ID";
    private static final String TAG = CreateAlertWorker.class.getSimpleName();
    private final AlertAPI alertAPI;
    private final AlertDao alertDao;

    @AssistedInject
    public CreateAlertWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params, AlertAPI alertAPI, AlertDao alertDao) {
        super(context, params);

        this.alertAPI = alertAPI;
        this.alertDao = alertDao;
    }

    public static Data buildInputData(String authHeader, int alertId){
        Data.Builder builder = new Data.Builder();
        builder.putString(CreateAlertWorker.KEY_AUTH_HEADER, authHeader);
        builder.putInt(CreateAlertWorker.KEY_ALERT_OBJ_ID, alertId);
        return builder.build();
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        String authHeader = getInputData().getString(KEY_AUTH_HEADER);
        int alertObjId = getInputData().getInt(KEY_ALERT_OBJ_ID, -1);

        return alertDao.getAlertSingle(alertObjId)
                .flatMap(localAlert -> alertAPI.createAlertSingle(authHeader, localAlert)
                        .doOnSuccess(serverAlert -> updateDBEntry(localAlert, serverAlert)))
                .map(alertSingle -> {
                    Log.d(TAG, "created Alert: " + alertSingle.getId());
                    return Result.success();
                })
                .onErrorReturn(this::handleReturnResult);
    }

    private void updateDBEntry(Alert localAlert, Alert serverAlert) {
        Integer localId = localAlert.getId();
        serverAlert.setId(localId);
        alertDao.update(serverAlert);
    }

    @NotNull
    private Result handleReturnResult(Throwable throwable) {
        if (Helper.isConnectionError(throwable)) {
            return Result.retry();
        }
        return Result.failure();
    }
}
