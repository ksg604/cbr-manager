package com.example.cbr_manager.ui.referral.referral_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReferralListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReferralListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}