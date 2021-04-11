package com.example.cbr_manager.service.goal;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GoalAPI {
    @PUT("api/goals/{id}/")
    Single<Goal> modifyGoalAsSingle(@Header("Authorization") String authHeader, @Path("id") int id, @Body Goal goal);

    @GET("api/goals/")
    Single<List<Goal>> getGoalsAsSingle(@Header("Authorization") String authHeader);

    @GET("api/goals/{id}")
    Single<Goal> geGoalAsSingle(@Header("Authorization") String authHeader, @Path("id") int id);

    @POST("api/goals/")
    Single<Goal> createGoalSingle(@Header("Authorization") String authHeader, @Body Goal goal);
}


