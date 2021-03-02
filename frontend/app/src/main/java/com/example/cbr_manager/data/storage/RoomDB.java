package com.example.cbr_manager.data.storage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cbr_manager.service.client.Client;

@Database(entities = {Client.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    public abstract ClientDao clientDao();

    private static volatile RoomDB Instance;

    static RoomDB getDatabase(final Context context){
        if (Instance == null){
            synchronized (RoomDB.class){
                if(Instance == null){
                    Instance = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, "app_database").build();
                }
            }
        }
        return Instance;
    }

}
