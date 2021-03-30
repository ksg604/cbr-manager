package com.example.cbr_manager.ui;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cbr_manager.repository.AuthRepository;
import com.example.cbr_manager.service.auth.AuthDetail;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.example.cbr_manager.service.user.User;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Single;

@HiltViewModel
public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final SavedStateHandle savedStateHandle;

    @Inject
    public AuthViewModel(SavedStateHandle savedStateHandle, AuthRepository authRepository) {
        this.savedStateHandle = savedStateHandle;
        this.authRepository = authRepository;
    }

    public Single<AuthDetail> login(LoginUserPass loginUserPass) {
        return authRepository.login(loginUserPass);
    }

    public Single<AuthDetail> cachedLogin() {
        return authRepository.cachedLogin();
    }

    public Boolean isAuthenticated() {
        return authRepository.isAuthenticated();
    }

    public Single<User> getUser() {
        return authRepository.getUser();
    }

    public void logout(){
        authRepository.logout();
    }
}
