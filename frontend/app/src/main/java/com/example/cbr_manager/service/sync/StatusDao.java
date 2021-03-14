package com.example.cbr_manager.service.sync;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;


@Dao
public interface StatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Status status);

    @Update
    void update(Status status);

    @Query("SELECT * FROM status WHERE id = 1")
    Single<Status> getStatus();
}
