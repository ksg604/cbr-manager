package com.example.cbr_manager.service;

import com.example.cbr_manager.service.auth.AuthService;
import com.example.cbr_manager.service.auth.AuthToken;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.example.cbr_manager.service.client.ClientService;


public class APIService {
    public AuthService authService;

    public ClientService clientService;

    public APIService(LoginUserPass loginUserPass) {
        this.authenticate(loginUserPass);

        // initialize all API services
        AuthToken token = this.authService.getAuthToken();

        clientService = new ClientService(token);
    }

    private void authenticate(LoginUserPass loginUserPass){
        authService = new AuthService(loginUserPass);
        this.authService.fetchAuthToken();
    }
}
