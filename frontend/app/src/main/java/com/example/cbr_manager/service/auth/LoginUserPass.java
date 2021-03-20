package com.example.cbr_manager.service.auth;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;

public class LoginUserPass {
    @ColumnInfo(name = "auth_username")
    @Expose
    public String username;
    @ColumnInfo(name = "auth_password")
    @Expose
    public String password;

    public LoginUserPass(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

