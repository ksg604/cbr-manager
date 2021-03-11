package com.example.cbr_manager.service.auth;

import com.example.cbr_manager.service.user.User;

public class AuthDetail {
    public String token;

    public User user;

    public AuthDetail(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
