package com.example.cbr_manager.service.alert;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/*
Define our API request endpoints here
 */
public interface AlertAPI {
    @GET("api/alerts/")
    Call<List<Alert>> getAlerts(@Header("Authorization") String authHeader);

    @GET("api/alerts/{id}")
    Call<Alert> getAlert(@Header("Authorization") String authHeader, @Path("id") int id);

    @PUT("api/alerts/{id}")
    Call<Alert> modifyAlert(@Header("Authorization") String authHeader, @Path("id") int id, @Body Alert alert);

    @POST("api/alerts/")
    Call<Alert> createAlert(@Header("Authorization") String authHeader, @Body Alert alert);
}
