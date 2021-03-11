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
    public User currentUser;
    public ReferralService referralService;

    private APIService() {
    }

    public static APIService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new APIService();
        }
        return (INSTANCE);
    }

    public void authenticate(LoginUserPass loginUserPass, Callback<AuthDetail> listener) {
        authService = new AuthService(loginUserPass);
        authService.fetchAuthToken().enqueue(new Callback<AuthDetail>() {
            @Override
            public void onResponse(Call<AuthDetail> call, Response<AuthDetail> response) {
                if (response.isSuccessful()) {
                    AuthDetail authResponse = response.body();
                    authService.setAuthDetail(authResponse);
                    initializeServices(authResponse.token);

                    setCurrentUser(authResponse.user);
                }
                listener.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<AuthDetail> call, Throwable t) {
                // Todo: not sure proper method of error handling

                listener.onFailure(call, t);
            }
        });
    }

    public void authenticate(String token, Callback<User> listener) {
        authService = new AuthService(null);
        initializeServices(token);
        userService.getCurrentUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    authService.setAuthDetail(new AuthDetail(token, user));
                    setCurrentUser(user);
                }
                listener.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onFailure(call, t);
            }
        });
    }

    private void initializeServices(String token) {
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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private ReferralService initializeReferralService(String authResponse) {
        return new ReferralService(authResponse);
    }

    public ReferralService getReferralService() {
        return referralService;
    }
}
