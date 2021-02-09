package com.example.cbr_manager.service.auth;

import com.example.cbr_manager.BuildConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUserPass {
    public String username;
    public String password;

    public LoginUserPass(String username, String password){
        this.username = username;
        this.password = password;
    }
}

