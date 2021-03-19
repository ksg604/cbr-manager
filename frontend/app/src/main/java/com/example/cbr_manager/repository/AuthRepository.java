package com.example.cbr_manager.repository;

import com.example.cbr_manager.di.SharedPreferencesHelper;
import com.example.cbr_manager.service.auth.AuthAPI;
import com.example.cbr_manager.service.auth.AuthDetail;
import com.example.cbr_manager.service.auth.AuthDetailDao;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.service.user.UserAPI;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class AuthRepository {

    private UserAPI userAPI;

    private AuthDetailDao authDetailDao;

    private AuthAPI authAPI;

    private String authHeader;

    private SharedPreferencesHelper sharedPreferencesHelper;

    @Inject
    AuthRepository(AuthDetailDao authDetailDao, UserAPI userAPI, AuthAPI authAPI, String authHeader, SharedPreferencesHelper sharedPreferencesHelper) {
        this.authDetailDao = authDetailDao;
        this.authAPI = authAPI;
        this.authHeader = authHeader;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.userAPI = userAPI;
    }

    public Single<AuthDetail> login(LoginUserPass loginUserPass) {
        return authAPI.authenticate(loginUserPass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(authDetail -> {
                    authDetailDao.insert(authDetail).subscribeOn(Schedulers.io()).subscribe();
                    sharedPreferencesHelper.setAuthToken(authDetail.token);
                })
                .doOnError(throwable -> {
                    if (throwable instanceof HttpException) {
                        sharedPreferencesHelper.setAuthToken("");
                    }
                });
    }


    public Single<AuthDetail> cachedLogin() {
        return userAPI.getCurrentUserObs(authHeader)
                .subscribeOn(Schedulers.io())
                .flatMap(user -> authDetailDao.getAuthDetail())
                .onErrorResumeNext(throwable -> authDetailDao.getAuthDetail());
    }

    public Single<User> getUser() {
        return authDetailDao.getAuthDetail()
                .subscribeOn(Schedulers.io())
                .map(authDetail -> authDetail.user);
    }

    public Single<Boolean> isAuthenticated() {
        return userAPI.getCurrentUserObs(authHeader)
                .subscribeOn(Schedulers.io())
                .flatMap(user -> Single.just(true))
                .onErrorReturnItem(false);
    }


}
