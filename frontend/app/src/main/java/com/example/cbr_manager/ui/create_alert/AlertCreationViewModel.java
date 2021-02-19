package com.example.cbr_manager.ui.create_alert;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlertCreationViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AlertCreationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is user creation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}




