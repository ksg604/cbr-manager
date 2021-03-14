package com.example.cbr_manager.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.cbr_manager.data.storage.RoomDB;
import com.example.cbr_manager.service.sync.StatusDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RoomModule {

    @Singleton
    @Provides
    RoomDB provideRoomDb(@ApplicationContext Context context) {
        return Room.databaseBuilder(context,
                RoomDB.class, RoomDB.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    StatusDao provideStatusDao(RoomDB roomDB) {
        return roomDB.statusDao();
    }
}
