package com.example.cbr_manager.service.referral;

import java.util.List;

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

public interface ReferralAPI {
    @GET("api/referrals/")
    Call<List<Referral>> getReferrals(@Header("Authorization") String authHeader);

    @POST("api/referrals/")
    Call<Referral> createReferral(@Header("Authorization") String authHeader, @Body Referral referral);

    @GET("api/referrals/{id}/")
    Call<Referral> getReferral(@Header("Authorization") String authHeader, @Path("id") int id);

    @PUT("api/referrals/{id}/")
    Call<Referral> updateReferral(@Header("Authorization") String authHeader, @Path("id") int id, @Body Referral referral);

    @Multipart
    @POST("api/referrals/{id}/upload/")
    Call<ResponseBody> uploadPhoto(@Header("Authorization") String authHeader, @Path("id") int id, @Part MultipartBody.Part photo);
}