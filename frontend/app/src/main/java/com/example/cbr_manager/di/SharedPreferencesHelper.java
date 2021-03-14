package com.example.cbr_manager.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class SharedPreferencesHelper {

    private final String AUTH_TOKEN_KEY = "AUTH_TOKEN_KEY";
    private final SharedPreferences sharedPref;

    @Inject
    SharedPreferencesHelper(@ApplicationContext Context context) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getAuthToken() {
        return sharedPref.getString(AUTH_TOKEN_KEY, "");
    }

    public void setAuthToken(String token) {
        sharedPref.edit().putString(AUTH_TOKEN_KEY, token).apply();
    }
}
