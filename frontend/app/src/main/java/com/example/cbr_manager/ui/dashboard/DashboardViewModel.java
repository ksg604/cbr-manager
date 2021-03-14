package com.example.cbr_manager.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> myText;

    public DashboardViewModel() {
        myText = new MutableLiveData<>();
        myText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return myText;
    }
}