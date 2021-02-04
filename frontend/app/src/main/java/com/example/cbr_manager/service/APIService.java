package com.example.cbr_manager.service;

import com.example.cbr_manager.service.auth.AuthService;
import com.example.cbr_manager.service.auth.LoginUserPass;


public class APIService {
    public final AuthService authService;

    public APIService(LoginUserPass loginUserPass) {
        // initialize all API services
        authService = new AuthService(loginUserPass);
    }

    public void authenticate() {
        authService.fetchAuthToken();
    }
}
