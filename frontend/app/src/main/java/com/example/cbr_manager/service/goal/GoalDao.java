package com.example.cbr_manager.service.goal;

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
public interface GoalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Goal> goals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Goal goal);

    @Delete
    Completable delete(Goal goal);

    @Query("SELECT * FROM referral")
    Observable<List<Goal>> getGoals();

    @Query("SELECT * FROM goal WHERE goalId = :goalId")
    Single<Goal> getGoal(int goalId);
}
