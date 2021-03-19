package com.example.cbr_manager.service.auth;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cbr_manager.service.user.User;

import io.reactivex.Completable;
import io.reactivex.Single;


@Dao
public interface AuthDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(AuthDetail authDetail);

    @Delete
    Completable delete(AuthDetail authDetail);

    // Read client by id
    @Query("SELECT * FROM authdetail WHERE AuthDetailId = 0")
    Single<AuthDetail> getAuthDetail();
}
