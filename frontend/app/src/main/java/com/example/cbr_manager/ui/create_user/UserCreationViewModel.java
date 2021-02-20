package com.example.cbr_manager.ui.create_user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserCreationViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public UserCreationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is user creation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}




