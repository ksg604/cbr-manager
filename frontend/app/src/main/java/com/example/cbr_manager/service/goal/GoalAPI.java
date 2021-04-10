package com.example.cbr_manager.service.goal;

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

public interface GoalAPI {
    @GET("api/Goals/")
    Call<List<Goal>> getGoals(@Header("Authorization") String authHeader);

    @GET("api/goals/")
    Observable<List<Goal>> getGoalsObs(@Header("Authorization") String authHeader);

    @POST("api/goals/")
    Call<Goal> createGoal(@Header("Authorization") String authHeader, @Body Goal goal);

    @POST("api/goals/")
    Single<Goal> createGoalSingle(@Header("Authorization") String authHeader, @Body Goal goal);

    @GET("api/goals/{id}/")
    Call<Goal> getGoal(@Header("Authorization") String authHeader, @Path("id") int id);

    @GET("api/goals/{id}/")
    Single<Goal> getGoalSingle(@Header("Authorization") String authHeader, @Path("id") int id);

    @PUT("api/goals/{id}/")
    Call<Goal> updateGoal(@Header("Authorization") String authHeader, @Path("id") int id, @Body Goal goal);

    @Multipart
    @POST("api/Goals/{id}/upload/")
    Call<ResponseBody> uploadPhoto(@Header("Authorization") String authHeader, @Path("id") int id, @Part MultipartBody.Part photo);
}