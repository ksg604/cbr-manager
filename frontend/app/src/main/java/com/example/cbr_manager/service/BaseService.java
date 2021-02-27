package com.example.cbr_manager.service;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.service.auth.AuthResponse;
import com.example.cbr_manager.utils.Helper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseService {
    protected static final String BASE_URL = BuildConfig.API_URL;

    protected final AuthResponse authToken;

    protected final String authHeader;

    protected final Class retroFitAPIClass;

    public BaseService(AuthResponse authToken, Class retroFitAPIClass) {
        this.authToken = authToken;
        this.authHeader = Helper.formatTokenHeader(this.authToken);
        this.retroFitAPIClass = retroFitAPIClass;
    }

    protected <T> T getAPI() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return (T) new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(retroFitAPIClass);
    }
}
