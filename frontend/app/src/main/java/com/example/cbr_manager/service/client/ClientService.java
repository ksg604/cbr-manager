package com.example.cbr_manager.service.client;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.helper.Helper;
import com.example.cbr_manager.service.auth.AuthToken;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

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
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(ClientAPI.class);
    }

    public Call<List<Client>> getClients() {
        return this.clientAPI.getClients(authHeader);
    }

    public Call<Client> modifyClient(Client client) {
        return this.clientAPI.modifyClient(authHeader, client.getId(), client);
    }

    public Call<Client> createClient(Client client) {
        // note: client id for the client object can be anything. default it manually to -1.
        return this.clientAPI.createClient(authHeader, client);
    }

    public Call<Client> getClient(int clientId) {
        return this.clientAPI.getClient(authHeader, clientId);
    }

    public Call<Client> createClient2(Client client) {
        RequestBody firstName = RequestBody.create(client.getFirstName(), MediaType.parse("text/plain"));
        return this.clientAPI.createClient2(authHeader, firstName);
    }

}