package com.example.cbr_manager.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cbr_manager.repository.AlertRepository;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.alert.Alert;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;

@HiltViewModel
public class AlertViewModel extends ViewModel {
    private final AlertRepository alertRepository;

    private final SavedStateHandle savedStateHandle;

    @Inject
    public AlertViewModel(SavedStateHandle savedStateHandle, AlertRepository alertRepository) {
        this.savedStateHandle = savedStateHandle;
        this.alertRepository = alertRepository;
    }

    public LiveData<List<Alert>> getAllAlerts() {
        return this.alertRepository.getAlertsAsLiveData();
    }

    public LiveData<List<Alert>> getAllAlertsOffline() {
        return this.alertRepository.getAlertsAsLiveDataOffline();
    }

    public LiveData<Alert> getAlert(int id) {
        return alertRepository.getAlert(id);
    }

    public Single<Alert> getAlertAsSingle(int id) {
        return alertRepository.getAlertAsSingle(id);
    }

    public Single<Alert> createAlert(Alert alert) {
        return this.alertRepository.createAlert(alert);
    }

    public Completable modifyAlert(Alert alert) {
        return this.alertRepository.modifyAlert(alert);
    }

    public Completable modifyAlertOffline(Alert alert) {
        return this.alertRepository.modifyAlertOffline(alert);
    }

}
