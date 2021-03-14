package com.example.cbr_manager.ui.alert.create_alert;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlertCreationViewModel extends ViewModel {
    private MutableLiveData<String> myText;

    public AlertCreationViewModel() {
        myText = new MutableLiveData<>();
        myText.setValue("This is user creation fragment");
    }

    public LiveData<String> getText() {
        return myText;
    }

}




