package com.example.cbr_manager.service.referral;

import com.example.cbr_manager.service.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReferralAPI {
    @GET("api/referrals/")
    Call<List<Referral>> getReferrals(@Header("Authorization") String authHeader);

    @GET("api/referrals/{id}/")
    Call<Referral> getReferral(@Header("Authorization") String authHeader, @Path("id") int id);

    @POST("api/referrals/")
    Call<Referral> createReferral(@Header("Authorization") String authHeader, @Body Referral referral);
}