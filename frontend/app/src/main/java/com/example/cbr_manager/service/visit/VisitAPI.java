package com.example.cbr_manager.service.visit;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VisitAPI {
    @PUT("api/visits/{id}/")
    Single<Visit> modifyVisitAsSingle(@Header("Authorization") String authHeader, @Path("id") int id, @Body Visit visit);

    @GET("api/visits/")
    Single<List<Visit>> getVisitsAsSingle(@Header("Authorization") String authHeader);

    @GET("api/visits/{id}")
    Single<Visit> getVisitAsSingle(@Header("Authorization") String authHeader, @Path("id") int id);

    @POST("api/visits/")
    Single<Visit> createVisitSingle(@Header("Authorization") String authHeader, @Body Visit visit);
}


