package com.example.cbr_manager.service.auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("api/token-auth/")
    Call<AuthToken> getToken(@Body LoginUserPass loginUserPass);
}
