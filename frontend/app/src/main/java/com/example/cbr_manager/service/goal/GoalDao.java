package com.example.cbr_manager.service.goal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Goal goal);

    @Delete
    void delete(Goal goal);

    @Update
    void update(Goal goal);

    @Query("SELECT * FROM goal")
    List<Goal> readAll();

    @Query("SELECT * FROM goal WHERE goal_id = :goalId")
    Goal getById(int goalId);

    @Query("DELETE FROM goal")
    void clearAll();
}
