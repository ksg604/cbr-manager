package com.example.cbr_manager.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cbr_manager.repository.ReferralRepository;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.referral.Referral;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Completable;
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

    public LiveData<Referral> getReferral(int id){
        return referralRepository.getReferral(id);
    }

    public LiveData<List<Referral>> getReferralsAsLiveData() {
        return referralRepository.getReferralsAsLiveData();
    }

    public Single<Referral> createReferral(Referral referral) {
        return referralRepository.createReferral(referral);
    }

    public Completable modifyReferral(Referral referral) {
        return referralRepository.modifyReferral(referral);
    }

    public Completable uploadPhoto(File file, Referral referral) {
        return this.referralRepository.uploadPhoto(file, referral);
    }
}
