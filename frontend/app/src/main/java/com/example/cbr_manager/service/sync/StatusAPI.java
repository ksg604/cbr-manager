package com.example.cbr_manager.service.sync;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface StatusAPI {
    @GET("api/status/")
    Single<Status> getStatus(@Header("Authorization") String authHeader);
}
