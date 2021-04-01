package com.example.cbr_manager.service.visit;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface VisitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Visit visit);

    @Delete
    void delete(Visit visit);

    @Update
    void update(Visit visit);

    @Query("SELECT * FROM visit")
    List<Visit> readAll();

    @Query("SELECT * FROM visit WHERE visit_id = :visitId")
    Visit getById(int visitId);

    @Query("DELETE FROM visit")
    void clearAll();

    @Query("SELECT * FROM visit")
    Single<List<Visit>> getVisits();

    @Query("SELECT * FROM visit WHERE visit_id = :id")
    Single<Visit> getVisit(int id);
}
