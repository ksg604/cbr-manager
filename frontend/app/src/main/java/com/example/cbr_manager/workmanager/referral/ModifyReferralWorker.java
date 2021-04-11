package com.example.cbr_manager.workmanager.referral;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Data;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.referral.ReferralAPI;
import com.example.cbr_manager.service.referral.ReferralDao;
import com.example.cbr_manager.utils.Helper;

import org.jetbrains.annotations.NotNull;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;

@HiltWorker
public class ModifyReferralWorker extends RxWorker {

    public static final String KEY_AUTH_HEADER = "KEY_AUTH_HEADER";
    public static final String KEY_REFERRAL_OBJ_ID = "KEY_REFERRAL_OBJ_ID";
    private static final String TAG = ModifyReferralWorker.class.getSimpleName();
    private final ReferralAPI referralAPI;
    private final ReferralDao referralDao;

    @AssistedInject
    public ModifyReferralWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params, ReferralAPI referralAPI, ReferralDao referralDao) {
        super(context, params);

        this.referralAPI = referralAPI;
        this.referralDao = referralDao;
    }

    public static Data buildInputData(String authHeader, int referralId) {
        Data.Builder builder = new Data.Builder();
        builder.putString(ModifyReferralWorker.KEY_AUTH_HEADER, authHeader);
        builder.putInt(ModifyReferralWorker.KEY_REFERRAL_OBJ_ID, referralId);
        return builder.build();
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        String authHeader = getInputData().getString(KEY_AUTH_HEADER);
        int referralObjId = getInputData().getInt(KEY_REFERRAL_OBJ_ID, -1);

        return referralDao.getReferralSingle(referralObjId)
                .flatMap(localReferral -> referralAPI.modifyReferralSingle(authHeader, localReferral.getServerId(), localReferral)
                        .doOnSuccess(serverReferral -> updateDBEntry(localReferral, serverReferral)))
                .map(referralSingle -> {
                    Log.d(TAG, "modified Referral: " + referralSingle.getId());
                    return Result.success();
                })
                .onErrorReturn(this::handleReturnResult);
    }

    private void updateDBEntry(Referral localReferral, Referral serverReferral) {
        Integer localId = localReferral.getId();
        serverReferral.setId(localId);
        referralDao.update(serverReferral);
    }

    @NotNull
    private Result handleReturnResult(Throwable throwable) {
        if (Helper.isConnectionError(throwable)) {
            return Result.retry();
        }
        return Result.failure();
    }
}
