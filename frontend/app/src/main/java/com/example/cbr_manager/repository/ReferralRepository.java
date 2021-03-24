package com.example.cbr_manager.repository;

import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.referral.ReferralAPI;
import com.example.cbr_manager.service.referral.ReferralDao;

import java.net.SocketTimeoutException;
import java.util.List;

import javax.inject.Inject;

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

    public Single<List<Referral>> getReferrals() {
        return referralAPI.getReferralsSingle(authHeader)
                .subscribeOn(Schedulers.io())
                .map(referrals -> {
                    for (Referral referral :
                            referrals) {
                        referralDao.insert(referral);
                    }
                    return referrals;
                })
                .onErrorResumeNext(this::handleOfflineReferrals)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private SingleSource<? extends List<Referral>> handleOfflineReferrals(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            return referralDao.getReferrals();
        }
        return Single.error(throwable);
    }

    public Single<Referral> getReferral(int id) {
        return referralAPI.getReferralSingle(authHeader, id)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> referralDao.getReferral(id))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
