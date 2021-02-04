package com.example.cbr_manager.service.client;

import com.example.cbr_manager.service.auth.AuthToken;
import com.example.cbr_manager.service.auth.LoginUserPass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/*
Define our API request endpoints here
 */
public interface ClientAPI {
    @POST("api/token-auth/")
    Call<List<Client>> getClients(@Header("Authorization") String authHeader);
}
