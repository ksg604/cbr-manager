package com.example.cbr_manager.data.storage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientDao;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.service.visit.VisitDao;

@Database(entities = {Client.class, Visit.class}, version = 1, exportSchema = false)
@TypeConverters({TimeStampConverter.class})
public abstract class RoomDB extends RoomDatabase {

    public abstract ClientDao clientDao();
    public abstract VisitDao visitDao();

    private static volatile RoomDB Instance;

    public static RoomDB getDatabase(final Context context){
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
