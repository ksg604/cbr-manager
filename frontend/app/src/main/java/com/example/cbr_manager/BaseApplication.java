package com.example.cbr_manager;

import androidx.multidex.MultiDexApplication;

import com.jakewharton.threetenabp.AndroidThreeTen;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public final class BaseApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
