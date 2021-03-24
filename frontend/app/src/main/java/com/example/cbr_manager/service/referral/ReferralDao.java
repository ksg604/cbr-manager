package com.example.cbr_manager.service.referral;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cbr_manager.service.client.Client;

import java.sql.Ref;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface ReferralDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Referral referral);

    @Delete
    Completable delete(Referral referral);

    @Query("SELECT * FROM referral")
    Single<List<Referral>> getReferrals();

    @Query("SELECT * FROM referral WHERE referralId = :referralId")
    Single<Referral> getReferral(int referralId);
}
