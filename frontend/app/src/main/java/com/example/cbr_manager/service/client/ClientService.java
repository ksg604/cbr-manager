package com.example.cbr_manager.service.client;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.helper.Helper;
import com.example.cbr_manager.service.auth.AuthToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientService {

    private static final String BASE_URL = BuildConfig.API_URL;

    private final AuthToken authToken;

    private ClientAPI clientAPI;

    public ClientService(AuthToken auth) {
        this.authToken = auth;

        initClientAPI();
    }

    public Call<List<Client>> getClients() {
        String authHeader = Helper.formatTokenHeader(this.authToken);

        return this.clientAPI.getClients(authHeader);
    }

    private void initClientAPI() {
        this.clientAPI = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ClientAPI.class);
    }

}