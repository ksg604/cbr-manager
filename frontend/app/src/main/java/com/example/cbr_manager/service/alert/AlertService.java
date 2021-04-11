package com.example.cbr_manager.service.alert;

import com.example.cbr_manager.service.BaseService;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.alert.AlertAPI;

import java.util.List;

import retrofit2.Call;

public class AlertService extends BaseService {

    AlertAPI alertAPI;

    public AlertService(String authToken) {
        super(authToken, AlertAPI.class);
        this.alertAPI = this.buildRetrofitAPI();
    }

    public Call<List<Alert>> getAlerts() {
        return alertAPI.getAlerts(authHeader);
    }

    public Call<Alert> getAlert(int id) {
        return alertAPI.getAlert(authHeader, id);
    }

    public Call<Alert> createAlert(Alert alert) {
        return alertAPI.createAlert(authHeader, alert);
    }
}