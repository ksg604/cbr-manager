package com.example.cbr_manager.ui;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cbr_manager.repository.ReferralRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReferralViewModel extends ViewModel {
    private final SavedStateHandle savedStateHandle;
    private ReferralRepository referralRepository;

    @Inject
    public ReferralViewModel(SavedStateHandle savedStateHandle, ReferralRepository referralRepository) {
        this.savedStateHandle = savedStateHandle;
        this.referralRepository = referralRepository;
    }
}
