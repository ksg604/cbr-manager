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

    private final String authHeader;

    private ClientAPI clientAPI;

    public ClientService(AuthToken auth) {
        this.authToken = auth;

        this.authHeader = Helper.formatTokenHeader(this.authToken);

        this.clientAPI = getClientAPI();
    }

    private ClientAPI getClientAPI() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ClientAPI.class);
    }

    public Call<List<Client>> getClients() {
        return this.clientAPI.getClients(authHeader);
    }

    public Call<Client> modifyClient(Client client) {
        return this.clientAPI.modifyClient(authHeader, client.getId(), client);
    }

    public Call<Client> getClient(int clientId) {
        return this.clientAPI.getClient(authHeader, clientId);
    }

}