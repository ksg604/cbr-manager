package com.example.cbr_manager.service.goal;

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
public interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Goal goal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Goal> goals);

    @Delete
    void delete(Goal goal);

    @Update
    void update(Goal goal);

    @Query("DELETE FROM goal")
    void clearAll();

    @Query("SELECT * FROM goal")
    LiveData<List<Goal>> getGoalsAsLiveData();

    @Query("SELECT * FROM goal WHERE goalId = :id")
    Single<Goal> getGoalAsSingle(int id);

    @Query("SELECT * FROM goal WHERE goalId = :id")
    LiveData<Goal> getGoalAsLiveData(int id);

    @Query("SELECT * FROM goal WHERE serverId = :id")
    Goal getGoalByServerId(int id);
}
