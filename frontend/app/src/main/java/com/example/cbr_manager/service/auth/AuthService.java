package com.example.cbr_manager.service.auth;

import com.example.cbr_manager.BuildConfig;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthService {

    private static final String BASE_URL = BuildConfig.API_URL;

    private final LoginUserPass loginUserPass;

    private AuthToken authToken;

    public AuthService(LoginUserPass loginUserPass) {
        this.loginUserPass = loginUserPass; // Todo: credentials are stored in plaintext!
    }

    public Call<AuthToken> fetchAuthToken() {
        AuthAPI authAPI = getAuthAPI();
        return authAPI.getToken(loginUserPass);
    }

    public AuthToken getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    private AuthAPI getAuthAPI() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(AuthAPI.class);
    }
}