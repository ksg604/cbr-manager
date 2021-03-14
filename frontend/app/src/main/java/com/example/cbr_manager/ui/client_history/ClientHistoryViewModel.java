package com.example.cbr_manager.ui.client_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClientHistoryViewModel extends ViewModel {

    private MutableLiveData<String> myText;

    public ClientHistoryViewModel() {
        myText = new MutableLiveData<>();
        myText.setValue("This is client history fragment");
    }

    public LiveData<String> getText() {
        return myText;
    }
}