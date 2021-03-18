package com.example.cbr_manager.di;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.service.client.ClientAPI;
import com.example.cbr_manager.service.sync.StatusAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {

    @Singleton
    @Provides
    Gson provideGSonBuilder() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
    }


    @Singleton
    @Provides
    StatusAPI provideStatusAPI(Retrofit retrofit) {
        return retrofit.create(StatusAPI.class);
    }

    @Singleton
    @Provides
    ClientAPI provideClientAPI(Retrofit retrofit) {
        return retrofit.create(ClientAPI.class);
    }

    @Singleton
    @Provides
    String provideAuthHeader(SharedPreferencesHelper helper) {
        return helper.getAuthToken();
    }
}
