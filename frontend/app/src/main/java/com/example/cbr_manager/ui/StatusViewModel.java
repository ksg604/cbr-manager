package com.example.cbr_manager.ui;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cbr_manager.repository.StatusRepository;
import com.example.cbr_manager.service.sync.Status;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;

@HiltViewModel
public class StatusViewModel extends ViewModel {
    private final StatusRepository statusRepository;

    private final SavedStateHandle savedStateHandle;

    @Inject
    public StatusViewModel(SavedStateHandle savedStateHandle, StatusRepository statusRepository) {
        this.savedStateHandle = savedStateHandle;

        this.statusRepository = statusRepository;
    }

    public Single<Status> getStatus() {
        return this.statusRepository.getStatus();
    }

    public void insert(Status status) {
        statusRepository.insert(status);
    }
}
