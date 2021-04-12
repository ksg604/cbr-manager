package com.example.cbr_manager.service.alert;

import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.alert.Alert;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/*
Define our API request endpoints here
 */
public interface AlertAPI {
    @GET("api/alerts/")
    Call<List<Alert>> getAlerts(@Header("Authorization") String authHeader);

    @GET("api/alerts/")
    Single<List<Alert>> getAlertsAsSingle(@Header("Authorization") String authHeader);

    @GET("api/alerts/")
    Observable<List<Alert>> getAlertsObs(@Header("Authorization") String authHeader);

    @POST("api/alerts/")
    Call<Alert> createAlert(@Header("Authorization") String authHeader, @Body Alert alert);

    @POST("api/alerts/")
    Single<Alert> createAlertSingle(@Header("Authorization") String authHeader, @Body Alert alert);

    @GET("api/alerts/{id}/")
    Call<Alert> getAlert(@Header("Authorization") String authHeader, @Path("id") int id);

    @GET("api/alerts/{id}/")
    Single<Alert> getAlertSingle(@Header("Authorization") String authHeader, @Path("id") int id);

    @PUT("api/alerts/{id}/")
    Call<Alert> modifyAlert(@Header("Authorization") String authHeader, @Path("id") int id, @Body Alert alert);

    @PUT("api/alerts/{id}/")
    Single<Alert> modifyAlertSingle(@Header("Authorization") String authHeader, @Path("id") int id, @Body Alert alert);


}