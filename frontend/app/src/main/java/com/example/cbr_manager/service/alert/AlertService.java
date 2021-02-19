package com.example.cbr_manager.service.alert;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.helper.Helper;
import com.example.cbr_manager.service.auth.AuthResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlertService {

    private static final String BASE_URL = BuildConfig.API_URL;

    private final AuthResponse authToken;

    private final String authHeader;

    private AlertAPI alertAPI;

    public AlertService(AuthResponse auth) {
        this.authToken = auth;

        this.authHeader = Helper.formatTokenHeader(this.authToken);

        this.alertAPI = getAlertAPI();
    }

    public AlertService() {
        this.authToken = null;

        this.authHeader = null;

        this.alertAPI = getAlertAPI();
    }

    private AlertAPI getAlertAPI() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(AlertAPI.class);
    }

    public Call<List<Alert>> getAlerts() {
        return this.alertAPI.getAlerts(authHeader);
    }

    public Call<Alert> createAlert(Alert alert){
        // note: alert id for the alert object can be anything. default it manually to -1.
        return this.alertAPI.createAlert(authHeader, alert);
    }

    public Call<Alert> getAlert(int alertId) {
        return this.alertAPI.getAlert(authHeader, alertId);
    }

}