package com.example.cbr_manager.service.auth;

import com.example.cbr_manager.BuildConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthService {

    private static final String BASE_URL = BuildConfig.API_URL;

    private final LoginUserPass loginUserPass;

    private AuthToken authToken;

    public AuthService(LoginUserPass loginUserPass) {
        this.loginUserPass = loginUserPass; // Todo: credentials are stored in plaintext!
    }

    public void registerToken() {
        AuthAPI authAPI = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(AuthAPI.class);

        Call<AuthToken> getTokenCall = authAPI.getToken(loginUserPass);
        getTokenCall.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                if (response.isSuccessful()) {
                    setAuthToken(response.body());
                }
            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                // Todo: not sure proper method of error handling
            }
        });
    }

    public AuthToken getAuthToken() {
        return this.authToken;
    }

    private void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}