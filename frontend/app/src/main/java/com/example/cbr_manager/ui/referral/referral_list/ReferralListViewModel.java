package com.example.cbr_manager.ui.referral.referral_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReferralListViewModel extends ViewModel {

    private MutableLiveData<String> mutableLiveDataText;

    public ReferralListViewModel() {
        mutableLiveDataText = new MutableLiveData<>();
        mutableLiveDataText.setValue("This is referral list fragment");
    }

    public LiveData<String> getText() {
        return mutableLiveDataText;
    }
}