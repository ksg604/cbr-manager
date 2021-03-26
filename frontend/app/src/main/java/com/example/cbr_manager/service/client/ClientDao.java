package com.example.cbr_manager.service.client;

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
    void insert(Client client);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRx(Client client);

    @Delete
    void delete(Client client);

    @Update
    void update(Client client);

    @Update
    Completable updateRx(Client client);

    @Query("SELECT * FROM client")
    List<Client> getClients();

    @Query("SELECT * FROM client")
    Observable<List<Client>> getAllClients();

    @Query("SELECT * FROM client WHERE client_id = :clientId")
    Client getClient(int clientId);

    @Query("DELETE FROM client")
    void clearAll();

}
