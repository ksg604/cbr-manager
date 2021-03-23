package com.example.cbr_manager.service.baseline_survey;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BaselineSurveyAPI {

    @GET("api/surveys/")
    Call<List<BaselineSurvey>> getBaselineSurveys(@Header("Authorization") String authHeader);

    @GET("api/surveys/{id}")
    Call<BaselineSurvey> getBaselineSurvey(@Header("Authorization") String authHeader, @Path("id") int id);

    @PUT("api/surveys/{id}/")
    Call<BaselineSurvey> modifyBaselineSurvey(@Header("Authorization") String authHeader, @Path("id") int id, @Body BaselineSurvey baselineSurvey);

    @POST("api/surveys/")
    Call<BaselineSurvey> createBaselineSurvey(@Header("Authorization") String authHeader, @Body BaselineSurvey baselineSurvey);
}
