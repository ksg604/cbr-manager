package com.example.cbr_manager.service.client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/*
Define our API request endpoints here
 */
public interface ClientAPI {
    @GET("api/clients/")
    Call<List<Client>> getClients(@Header("Authorization") String authHeader);

    @GET("api/clients/{id}")
    Call<Client> getClient(@Header("Authorization") String authHeader, @Path("id") int id);
}
