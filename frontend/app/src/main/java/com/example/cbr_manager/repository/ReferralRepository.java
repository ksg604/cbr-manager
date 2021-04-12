package com.example.cbr_manager.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.referral.ReferralAPI;
import com.example.cbr_manager.service.referral.ReferralDao;
import com.example.cbr_manager.workmanager.referral.UploadPhotoWorker;
import com.example.cbr_manager.workmanager.referral.CreateReferralWorker;
import com.example.cbr_manager.workmanager.referral.ModifyReferralWorker;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ReferralRepository {

    private final ReferralAPI referralAPI;
    private final ReferralDao referralDao;
    private final String authHeader;
    private WorkManager workManager;

    @Inject
    ReferralRepository(ReferralAPI referralAPI, ReferralDao referralDao, String authHeader, WorkManager workManager) {
        this.referralAPI = referralAPI;
        this.referralDao = referralDao;
        this.authHeader = authHeader;
        this.workManager = workManager;
    }

    public LiveData<List<Referral>> getReferralsAsLiveData() {
        fetchReferralsAsync();
        return referralDao.getReferralsLiveData();
    }

    private void fetchReferralsAsync() {
        referralAPI.getReferralsAsSingle(authHeader)
                .doOnSuccess(referrals -> {
                            for (Referral referral : referrals) {
                                insertReferralToLocalDB(referral);
                            }
                        }
                )
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSingleObserver<List<Referral>>() {
                    @Override
                    public void onSuccess(@NonNull List<Referral> referrals) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    private void insertReferralToLocalDB(Referral referral) {
        Referral localReferral = referralDao.getReferralByServerId(referral.getServerId());
        if (localReferral != null) {
            referral.setId(localReferral.getId());
            referralDao.update(referral);
        }
        else {
            referralDao.insert(referral);
        }
    }

    public LiveData<Referral> getReferral(int id) {
        return referralDao.getReferralLiveData(id);
    }

    public Single<Referral> createReferral(Referral referral) {
        return Single.fromCallable(() -> referralDao.insert(referral))
                .map(aLong -> {
                    referral.setId(aLong.intValue());
                    return referral;
                })
                .doOnSuccess(this::enqueueCreateReferral)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable modifyReferral(Referral referral) {
        return Completable.fromAction(() -> referralDao.update(referral))
                .doOnComplete(() -> enqueueModifyReferral(referral))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable uploadPhoto(File file, Referral referral) {
        Uri path = Uri.fromFile(file);
        referral.setPhotoURL(path.toString());
        return Completable.fromAction(() -> referralDao.update(referral))
                .doOnComplete(() -> enqueueUploadPhoto(referral, file.getAbsolutePath()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private UUID enqueueCreateReferral(Referral referral) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest createReferralRequest =
                new OneTimeWorkRequest.Builder(CreateReferralWorker.class)
                        .setConstraints(constraints)
                        .setInputData(CreateReferralWorker.buildInputData(authHeader, referral.getId()))
                        .build();
        workManager.enqueue(createReferralRequest);
        return createReferralRequest.getId();
    }

    private void enqueueUploadPhoto(Referral referral, String filePath) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest uploadPhotoRequest =
                new OneTimeWorkRequest.Builder(UploadPhotoWorker.class)
                        .setConstraints(constraints)
                        .setInputData(UploadPhotoWorker.buildInputData(authHeader, referral.getId(), filePath))
                        .build();
        workManager.enqueue(uploadPhotoRequest);
    }

    private void enqueueModifyReferral(Referral referral) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest modifyReferralRequest =
                new OneTimeWorkRequest.Builder(ModifyReferralWorker.class)
                        .setConstraints(constraints)
                        .setInputData(ModifyReferralWorker.buildInputData(authHeader, referral.getId()))
                        .build();
        workManager.enqueue(modifyReferralRequest);
    }

    public Single<Referral> getReferralAsSingle(int id) {
        return referralDao.getReferralSingle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
