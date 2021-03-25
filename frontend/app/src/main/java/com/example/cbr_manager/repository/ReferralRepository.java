package com.example.cbr_manager.repository;

import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.referral.ReferralAPI;
import com.example.cbr_manager.service.referral.ReferralDao;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReferralRepository {

    private ReferralDao referralDao;
    private ReferralAPI referralAPI;
    private String authHeader;

    @Inject
    ReferralRepository(ReferralDao referralDao, ReferralAPI referralAPI, String authHeader) {
        this.referralDao = referralDao;
        this.referralAPI = referralAPI;
        this.authHeader = authHeader;
    }

    public Observable<Referral> getReferrals() {
        return referralAPI.getReferralsObs(authHeader)
                .flatMap(Observable::fromIterable)
                .onErrorResumeNext(this::getLocalReferrals)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private ObservableSource<? extends Referral> getLocalReferrals(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            return referralDao.getReferrals().flatMap(Observable::fromIterable);
        }
        return Observable.error(throwable);
    }

    public Single<Referral> getReferral(int id) {
        return referralAPI.getReferralSingle(authHeader, id)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> referralDao.getReferral(id))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
