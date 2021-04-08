package com.example.cbr_manager.workmanager.client;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Data;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.utils.Helper;

import org.jetbrains.annotations.NotNull;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;

@HiltWorker
public class ModifyClientWorker extends RxWorker {

    public static final String KEY_AUTH_HEADER = "KEY_AUTH_HEADER";
    public static final String KEY_CLIENT_OBJ_ID = "KEY_CLIENT_OBJ_ID";
    private static final String TAG = ModifyClientWorker.class.getSimpleName();
    private final ClientAPI clientAPI;
    private final ClientDao clientDao;

    @AssistedInject
    public ModifyClientWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params, ClientAPI clientAPI, ClientDao clientDao) {
        super(context, params);

        this.clientAPI = clientAPI;
        this.clientDao = clientDao;
    }

    public static Data buildInputData(String authHeader, int clientId) {
        Data.Builder builder = new Data.Builder();
        builder.putString(CreateClientWorker.KEY_AUTH_HEADER, authHeader);
        builder.putInt(CreateClientWorker.KEY_CLIENT_OBJ_ID, clientId);
        return builder.build();
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        String authHeader = getInputData().getString(KEY_AUTH_HEADER);
        int clientObjId = getInputData().getInt(KEY_CLIENT_OBJ_ID, -1);

        return clientDao.getClientSingle(clientObjId)
                .flatMap(localClient -> clientAPI.modifyClientSingle(authHeader, clientObjId, localClient)
                        .doOnSuccess(serverClient -> updateDBEntry(localClient, serverClient)))
                .map(clientSingle -> {
                    Log.d(TAG, "modified Client: " + clientSingle.getId());
                    return Result.success();
                })
                .onErrorReturn(this::handleReturnResult);
    }

    private void updateDBEntry(Client localClient, Client serverClient) {
        Integer localId = localClient.getId();
        serverClient.setId(localId);
        clientDao.update(serverClient);
    }

    @NotNull
    private Result handleReturnResult(Throwable throwable) {
        if (Helper.isConnectionError(throwable)) {
            return Result.retry();
        }
        return Result.failure();
    }
}
