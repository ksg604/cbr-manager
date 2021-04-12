package com.example.cbr_manager.service.referral;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ReferralDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Referral referral);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Referral> referrals);

    @Delete
    void delete(Referral referral);

    @Update
    void update(Referral referral);

    @Query("DELETE FROM referral")
    void clearAll();

    @Query("SELECT * FROM referral")
    LiveData<List<Referral>> getReferralsLiveData();

    @Query("SELECT * FROM referral WHERE id = :id")
    Single<Referral> getReferralSingle(int id);

    @Query("SELECT * FROM referral WHERE id = :id")
    LiveData<Referral> getReferralLiveData(int id);

    @Query("SELECT * FROM referral WHERE serverId = :id")
    Referral getReferralByServerId(int id);
}
