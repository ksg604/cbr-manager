package com.example.cbr_manager.di;
import android.content.Context;

import com.example.cbr_manager.service.client.ClientSync;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SyncModule {
    @Provides
    @Singleton
    ClientSync providesClientSync(@ApplicationContext Context context) {
        return ClientSync.getInstance(context);
    }
}
