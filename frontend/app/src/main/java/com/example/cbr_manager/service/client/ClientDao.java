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
    void insertAll(List<Client> client);

    @Delete
    void delete(Client client);

    // This function update every client where the @PrimaryKey matches, in this case it is id
    @Update
    void update(Client client);

    // Read all clients in client table
    @Query("SELECT * FROM client")
    List<Client> getClients();

    @Query("SELECT * FROM client")
    Single<List<Client>> getAllClients();

    // Read client by id
    @Query("SELECT * FROM client WHERE client_id = :clientId")
    Client getClient(int clientId);

    // Test function for clearing local database
    @Query("DELETE FROM client")
    void clearAll();

}
