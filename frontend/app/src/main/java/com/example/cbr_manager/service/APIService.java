package com.example.cbr_manager.service;

import com.example.cbr_manager.service.auth.AuthService;
import com.example.cbr_manager.service.auth.AuthToken;
import com.example.cbr_manager.service.auth.LoginUserPass;


public class APIService {
    private final AuthService authService;

    public APIService(LoginUserPass loginUserPass) {
        // initialize all API services
        authService = new AuthService(loginUserPass);
    }

    public void authenticate() {
        authService.registerToken();
    }

    public AuthToken getAuthToken(){
        return authService.getAuthToken();
    }

    public void otherAPIMethods(){
    }
}
