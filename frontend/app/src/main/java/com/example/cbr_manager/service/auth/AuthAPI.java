package com.example.cbr_manager.service.auth;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/*
Define our API request endpoints here
 */
public interface AuthAPI {
    @POST("api/token-auth/")
    Call<AuthDetail> getToken(@Body LoginUserPass loginUserPass);

    @POST("api/token-auth/")
    Single<AuthDetail> authenticate(@Body LoginUserPass loginUserPass);
}
