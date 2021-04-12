package com.example.cbr_manager.service.referral;

import java.util.List;

import io.reactivex.Completable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Completable;
import io.reactivex.Single;

/*
Define our API request endpoints here
 */
public interface ReferralAPI {
    @GET("api/referrals/")
    Call<List<Referral>> getReferrals(@Header("Authorization") String authHeader);

    @GET("api/referrals/")
    Single<List<Referral>> getReferralsAsSingle(@Header("Authorization") String authHeader);

    @GET("api/referrals/{id}/")
    Call<Referral> getReferral(@Header("Authorization") String authHeader, @Path("id") int id);

    @GET("api/referrals/{id}/")
    Single<Referral> getReferralSingle(@Header("Authorization") String authHeader, @Path("id") int id);

    @PUT("api/referrals/{id}/")
    Call<Referral> modifyReferral(@Header("Authorization") String authHeader, @Path("id") int id, @Body Referral referral);

    @PUT("api/referrals/{id}/")
    Single<Referral> modifyReferralSingle(@Header("Authorization") String authHeader, @Path("id") int id, @Body Referral referral);

    @POST("api/referrals/")
    Call<Referral> createReferral(@Header("Authorization") String authHeader, @Body Referral referral);

    @POST("api/referrals/")
    Single<Referral> createReferralSingle(@Header("Authorization") String authHeader, @Body Referral referral);

    @Multipart
    @POST("api/referrals/{id}/upload/")
    Call<ResponseBody> uploadPhoto(@Header("Authorization") String authHeader, @Path("id") int id, @Part MultipartBody.Part photo);

    @Multipart
    @POST("api/referrals/{id}/upload/")
    Single<ResponseBody> uploadPhotoSingle(@Header("Authorization") String authHeader, @Path("id") int id, @Part MultipartBody.Part photo);

  }
