package com.example.cbr_manager.service.auth;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

import com.example.cbr_manager.service.user.User;
import com.google.gson.annotations.Expose;

@Entity(tableName = "authdetail")
public class AuthDetail {
    @PrimaryKey
    @ColumnInfo(name = "AuthDetailId")
    public int id = 0;

    @Expose
    public String token;

    @Embedded
    @Expose
    public LoginUserPass loginUserPass;

    @Embedded
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    @Expose
    public User user;

    @Ignore
    public AuthDetail(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public AuthDetail(String token, User user, LoginUserPass loginUserPass) {
        this.token = token;
        this.user = user;
        this.loginUserPass = loginUserPass;
    }
}
