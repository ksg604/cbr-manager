package com.example.cbr_manager.service.auth;

import androidx.room.ColumnInfo;

import com.example.cbr_manager.BuildConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUserPass {
    @ColumnInfo(name = "auth_username")
    public String username;
    @ColumnInfo(name = "auth_password")
    public String password;

    public LoginUserPass(String username, String password){
        this.username = username;
        this.password = password;
    }
}

