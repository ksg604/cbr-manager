package com.example.cbr_manager.service.referral;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ReferralAPI {
    @GET("api/referrals/")
    Call<List<Referral>> getReferrals(@Header("Authorization") String authHeader);
}