package com.example.cbr_manager.service.alert;

import com.example.cbr_manager.service.BaseService;

import java.util.List;

import retrofit2.Call;

public class AlertService extends BaseService {

    private AlertAPI alertAPI;

    public AlertService(String authToken) {
        super(authToken, AlertAPI.class);
        this.alertAPI = buildRetrofitAPI();
    }

    public Call<List<Alert>> getAlerts() {
        return this.alertAPI.getAlerts(authHeader);
    }

    public Call<Alert> createAlert(Alert alert) {
        // note: alert id for the alert object can be anything. default it manually to -1.
        return this.alertAPI.createAlert(authHeader, alert);
    }

    public Call<Alert> getAlert(int alertId) {
        return this.alertAPI.getAlert(authHeader, alertId);
    }

}