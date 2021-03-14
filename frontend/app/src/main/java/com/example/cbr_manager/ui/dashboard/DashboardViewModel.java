package com.example.cbr_manager.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mutableLiveDataText;

    public DashboardViewModel() {
        mutableLiveDataText = new MutableLiveData<>();
        mutableLiveDataText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mutableLiveDataText;
    }
}