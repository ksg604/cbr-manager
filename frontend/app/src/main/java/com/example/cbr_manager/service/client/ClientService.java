package com.example.cbr_manager.service.client;

import com.example.cbr_manager.service.BaseService;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

@Deprecated
public class ClientService extends BaseService {

    private ClientAPI clientAPI;

    public ClientService(String authToken) {
        super(authToken, ClientAPI.class);
        this.clientAPI = buildRetrofitAPI();
    }

    public Call<List<ClientHistoryRecord>> getClientHistoryRecords(int clientId) {
        return this.clientAPI.getClientHistoryRecords(authHeader, clientId);
    }

}