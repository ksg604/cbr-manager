package com.example.cbr_manager.service;

import com.example.cbr_manager.service.auth.AuthService;
import com.example.cbr_manager.service.baseline_survey.BaselineSurveyService;
import com.example.cbr_manager.service.client.ClientService;
import com.example.cbr_manager.service.referral.ReferralService;
import com.example.cbr_manager.service.user.UserService;


public class APIService {

    private static APIService INSTANCE = null;
    public AuthService authService;
    @Deprecated
    public ClientService clientService;
    public UserService userService;
    public ReferralService referralService;
    public BaselineSurveyService baselineSurveyService;
    private String token;

    private APIService() {
    }

    public static APIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new APIService();
        }
        return (INSTANCE);
    }

    public void initializeServices(String token) {
        this.token = token;
        this.clientService = new ClientService(token);
        this.userService = new UserService(token);
        this.referralService = initializeReferralService(token);
        this.baselineSurveyService = new BaselineSurveyService(token);
    }

    @Deprecated // use AuthViewModel!
    public boolean isAuthenticated() {
        return token != null && !token.equals("");
    }

    private ReferralService initializeReferralService(String authResponse) {
        return new ReferralService(authResponse);
    }

    public ReferralService getReferralService() {
        return referralService;
    }
}
