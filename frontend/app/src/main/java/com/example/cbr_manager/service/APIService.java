package com.example.cbr_manager.service;

import com.example.cbr_manager.service.auth.AuthService;
import com.example.cbr_manager.service.auth.AuthToken;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.example.cbr_manager.service.client.ClientService;
import com.example.cbr_manager.service.user.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIService {

    private static APIService INSTANCE = null;
    public AuthService authService;
    public ClientService clientService;
    public UserService userService;

    private APIService() {
    }

    public static APIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new APIService();
        }
        return (INSTANCE);
    }

    public void authenticate(LoginUserPass loginUserPass, Callback<AuthToken> listener) {
        authService = new AuthService(loginUserPass);
        authService.fetchAuthToken().enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                if (response.isSuccessful()) {
                    AuthToken token = response.body();
                    authService.setAuthToken(token);
                    initializeServices(token);

                    listener.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                // Todo: not sure proper method of error handling

                listener.onFailure(call, t);
            }
        });
    }

    public void initializeServices(AuthToken token) {
        this.clientService = new ClientService(token);
        this.userService = new UserService(token);
    }

    public boolean isAuthenticated() {
        // Todo needs a better check, maybe a specific endpoint to check validity of auth token
        return authService.getAuthToken() != null;
    }

    public void testInitializeUserService() {
        this.userService = new UserService();
    }
}
