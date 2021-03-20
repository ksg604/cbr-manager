package com.example.cbr_manager.service.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Single;


@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(User user);

    @Delete
    Completable delete(User user);

    // Read client by id
    @Query("SELECT * FROM user WHERE id = :userId")
    Single<User> getUser(int userId);
}
