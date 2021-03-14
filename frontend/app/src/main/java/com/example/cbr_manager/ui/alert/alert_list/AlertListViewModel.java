package com.example.cbr_manager.ui.alert.alert_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlertListViewModel extends ViewModel {

    private MutableLiveData<String> myText;

    public AlertListViewModel() {
        myText = new MutableLiveData<>();
        myText.setValue("This is alert list fragment");
    }

    public LiveData<String> getText() {
        return myText;
    }
}