package com.example.cbr_manager.service;

import com.example.cbr_manager.service.alert.AlertService;
import com.example.cbr_manager.service.auth.AuthDetail;
import com.example.cbr_manager.service.auth.AuthService;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.example.cbr_manager.service.client.ClientService;
import com.example.cbr_manager.service.referral.ReferralService;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.service.user.UserService;
import com.example.cbr_manager.service.visit.VisitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class APIService {

    private static APIService INSTANCE = null;
    public AuthService authService;
    public ClientService clientService;
    public UserService userService;
    public VisitService visitService;
    public AlertService alertService;
    public ReferralService referralService;

    private APIService() {
    }

    public static APIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new APIService();
        }
        return (INSTANCE);
    }

    public void initializeServices(String token) {
        this.clientService = new ClientService(token);
        this.userService = new UserService(token);
        this.visitService = new VisitService(token);
        this.alertService = new AlertService(token);
        this.referralService = initializeReferralService(token);
    }

    public boolean isAuthenticated() {
        // Todo needs a better check, maybe a specific endpoint to check validity of auth token
        return authService.getAuthDetail() != null;
    }

    private ReferralService initializeReferralService(String authResponse) {
        return new ReferralService(authResponse);
    }

    public ReferralService getReferralService() {
        return referralService;
    }
}
