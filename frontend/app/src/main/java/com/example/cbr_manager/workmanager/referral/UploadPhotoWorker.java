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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

@HiltWorker
public class UploadPhotoWorker extends RxWorker{

    public static final String KEY_AUTH_HEADER = "KEY_AUTH_HEADER";
    public static final String KEY_CLIENT_OBJ_ID = "KEY_CLIENT_OBJ_ID";
    public static final String KEY_CLIENT_PHOTO_PATH = "KEY_CLIENT_PHOTO_PATH";
    private static final String TAG = UploadPhotoWorker.class.getSimpleName();
    private final ReferralAPI referralAPI;
    private final ReferralDao referralDao;

    @AssistedInject
    public UploadPhotoWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params, ReferralAPI referralAPI, ReferralDao referralDao) {
        super(context, params);

        this.referralAPI = referralAPI;
        this.referralDao = referralDao;
    }

    public static Data buildInputData(String authHeader, int referralId, String photoPath){
        Data.Builder builder = new Data.Builder();
        builder.putString(UploadPhotoWorker.KEY_AUTH_HEADER, authHeader);
        builder.putInt(UploadPhotoWorker.KEY_CLIENT_OBJ_ID, referralId);
        builder.putString(UploadPhotoWorker.KEY_CLIENT_PHOTO_PATH, photoPath);
        return builder.build();
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        String authHeader = getInputData().getString(KEY_AUTH_HEADER);
        int referralObjId = getInputData().getInt(KEY_CLIENT_OBJ_ID, -1);
        String photoPath = getInputData().getString(KEY_CLIENT_PHOTO_PATH);
        File photo = new File(photoPath);
        RequestBody requestFile = RequestBody.create(photo, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", photo.getName(), requestFile);

        return referralDao.getReferralSingle(referralObjId)
                .flatMap(referral -> referralAPI.uploadPhotoSingle(authHeader, referral.getServerId(), body)
                        .doOnSuccess(responseBody -> onSuccessfulUpload(responseBody, referral)))
                .map(photoSingle -> {
                    Log.d(TAG, "uploaded referral: " + photoSingle.string());
                    return Result.success();
                })
                .onErrorReturn(this::handleReturnResult);
    }

    private void onSuccessfulUpload(ResponseBody responseBody, Referral referral) throws IOException, JSONException {
        JSONObject json = new JSONObject(responseBody.string());
        String url = json.getString("url");
        referral.setPhotoURL(url);
        referralDao.update(referral);

    }

    @NotNull
    private Result handleReturnResult(Throwable throwable) {
        if (Helper.isConnectionError(throwable)) {
            return Result.retry();
        }
        return Result.failure();
    }
}
