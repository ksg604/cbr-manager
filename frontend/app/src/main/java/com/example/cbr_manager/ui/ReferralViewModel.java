package com.example.cbr_manager.ui;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cbr_manager.repository.ReferralRepository;
import com.example.cbr_manager.service.referral.Referral;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.Single;

@HiltViewModel
public class ReferralViewModel extends ViewModel {
    private final SavedStateHandle savedStateHandle;
    private ReferralRepository referralRepository;

    @Inject
    public ReferralViewModel(SavedStateHandle savedStateHandle, ReferralRepository referralRepository) {
        this.savedStateHandle = savedStateHandle;
        this.referralRepository = referralRepository;
    }

    public Single<Referral> getReferral(int id) {
        return referralRepository.getReferral(id);
    }

    public Single<List<Referral>> getReferrals(){
        return referralRepository.getReferrals();
    }

}
