package com.example.cbr_manager.ui.visits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VisitsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VisitsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is visits fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}