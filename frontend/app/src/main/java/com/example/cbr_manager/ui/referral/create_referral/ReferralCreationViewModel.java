package com.example.cbr_manager.ui.referral.create_referral;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReferralCreationViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ReferralCreationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is user creation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}




