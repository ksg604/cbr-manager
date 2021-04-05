package com.example.cbr_manager.service.client;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

import com.example.cbr_manager.service.client.Client;

import java.util.List;

@Dao
public interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Client client);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Client> clients);

    @Delete
    void delete(Client client);

    @Update
    void update(Client client);

    @Query("SELECT * FROM client")
    LiveData<List<Client>> getClientsLiveData();

    @Query("SELECT * FROM client WHERE client_id = :clientId")
    Single<Client> getClientSingle(int clientId);

    @Query("SELECT * FROM client WHERE client_id = :clientId")
    LiveData<Client> getClientLiveData(int clientId);

    @Query("DELETE FROM client")
    void clearAll();

}
