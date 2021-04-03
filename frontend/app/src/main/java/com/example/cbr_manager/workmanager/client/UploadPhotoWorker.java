package com.example.cbr_manager.workmanager.client;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.service.client.Client;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@HiltWorker
public class UploadPhotoWorker extends RxWorker{

    public static final String KEY_AUTH_HEADER = "KEY_AUTH_HEADER";
    public static final String KEY_CLIENT_OBJ_ID = "KEY_CLIENT_OBJ_ID";
    private static final String TAG = UploadPhotoWorker.class.getSimpleName();
    private final ClientAPI clientAPI;
    private final ClientDao clientDao;

    @AssistedInject
    public UploadPhotoWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params, ClientAPI clientAPI, ClientDao clientDao) {
        super(context, params);

        this.clientAPI = clientAPI;
        this.clientDao = clientDao;
    }

    public static Data buildInputData(String authHeader, int clientId){
        Data.Builder builder = new Data.Builder();
        builder.putString(UploadPhotoWorker.KEY_AUTH_HEADER, authHeader);
        builder.putInt(UploadPhotoWorker.KEY_CLIENT_OBJ_ID, clientId);
        return builder.build();
    }

    @NonNull
    @Override
    public Single<ListenableWorker.Result> createWork() {
        String authHeader = getInputData().getString(KEY_AUTH_HEADER);
        int clientObjId = getInputData().getInt(KEY_CLIENT_OBJ_ID, -1);
        File photo = new File(clientDao.getClient(clientObjId).getPhotoURL());
        RequestBody requestFile = RequestBody.create(photo, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", photo.getName(), requestFile);

        return clientDao.getClientObs(clientObjId)
                .flatMap(client -> clientAPI.uploadPhotoSingle(authHeader, clientObjId, body))
                .map(photoSingle -> ListenableWorker.Result.success())
                .onErrorReturn(this::handleReturnResult);
    }

    @NotNull
    private ListenableWorker.Result handleReturnResult(Throwable throwable) {
        if (throwable instanceof ConnectException || throwable instanceof SocketTimeoutException) {
            return ListenableWorker.Result.retry();
        }
        return ListenableWorker.Result.failure();
    }
}
