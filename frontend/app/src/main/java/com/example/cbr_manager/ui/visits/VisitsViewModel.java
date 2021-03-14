package com.example.cbr_manager.ui.visits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VisitsViewModel extends ViewModel {

    private MutableLiveData<String> myText;

    public VisitsViewModel() {
        myText = new MutableLiveData<>();
        myText.setValue("This is visits fragment");
    }

    public LiveData<String> getText() {
        return myText;
    }
}