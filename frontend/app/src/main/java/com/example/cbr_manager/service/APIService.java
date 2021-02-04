package com.example.cbr_manager.service;

import com.example.cbr_manager.service.auth.AuthService;
import com.example.cbr_manager.service.auth.AuthToken;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.example.cbr_manager.service.client.ClientService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIService {

    public static AuthService authService;
    public static ClientService clientService;
    private static APIService INSTANCE = null;

    private APIService() {
    }

    public static APIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new APIService();
        }
        return (INSTANCE);
    }

    private static void authenticate(LoginUserPass loginUserPass) {
        authService = new AuthService(loginUserPass);
        authService.fetchAuthToken().enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                if (response.isSuccessful()){
                    AuthToken token = response.body();

                    authService.setAuthToken(token);
                    initializeServices(token);
                }
            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                // Todo: not sure proper method of error handling
            }
        });
    }

    private static void initializeServices(AuthToken token){
        clientService = new ClientService(token);
    }
}
