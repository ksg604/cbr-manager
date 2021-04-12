package com.example.cbr_manager.service.alert;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cbr_manager.service.alert.Alert;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface AlertDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Alert alert);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Alert> alerts);

    @Delete
    void delete(Alert alert);

    @Update
    void update(Alert alert);

    @Query("DELETE FROM alert")
    void clearAll();

    @Query("SELECT * FROM alert")
    LiveData<List<Alert>> getAlertsLiveData();

    @Query("SELECT * FROM alert WHERE id = :alertId")
    Single<Alert> getAlertSingle(int alertId);

    @Query("SELECT * FROM alert WHERE id = :alertId")
    LiveData<Alert> getAlertLiveData(int alertId);

    @Query("SELECT * FROM alert WHERE serverId = :alertId")
    Alert getAlertByServerId(int alertId);
}
