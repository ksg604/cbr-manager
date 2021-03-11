package com.example.cbr_manager.service;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.utils.Helper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseService {
    protected static final String BASE_URL = BuildConfig.API_URL;

    protected final String authToken;

    protected final String authHeader;

    protected final Class retroFitAPIClass;

    public BaseService(String authToken, Class retroFitAPIClass) {
        this.authToken = authToken;
        this.authHeader = Helper.formatTokenHeader(this.authToken);
        this.retroFitAPIClass = retroFitAPIClass;
    }

    protected <T> T buildRetrofitAPI() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return (T) new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(retroFitAPIClass);
    }
}
