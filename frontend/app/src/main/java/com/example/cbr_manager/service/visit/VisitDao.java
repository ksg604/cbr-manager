package com.example.cbr_manager.service.visit;

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
public interface VisitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Visit visit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Visit> visits);

    @Delete
    void delete(Visit visit);

    @Update
    void update(Visit visit);

    @Query("DELETE FROM visit")
    void clearAll();

    @Query("SELECT * FROM visit")
    LiveData<List<Visit>> getVisitsAsLiveData();

    @Query("SELECT * FROM visit WHERE visit_id = :id")
    Single<Visit> getVisitAsSingle(int id);

    @Query("SELECT * FROM visit WHERE visit_id = :id")
    LiveData<Visit> getVisitAsLiveData(int id);

    @Query("SELECT * FROM visit WHERE serverId = :id")
    Visit getVisitByServerId(int id);
}
