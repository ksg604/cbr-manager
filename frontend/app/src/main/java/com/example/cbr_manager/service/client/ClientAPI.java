package com.example.cbr_manager.service.client;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/*
Define our API request endpoints here
 */
public interface ClientAPI {
    @GET("api/clients/")
    Call<List<Client>> getClients(@Header("Authorization") String authHeader);

    @GET("api/clients/{id}")
    Call<Client> getClient(@Header("Authorization") String authHeader, @Path("id") int id);

    @PUT("api/clients/{id}")
    Call<Client> modifyClient(@Header("Authorization") String authHeader, @Path("id") int id, @Body Client client);

    @POST("api/clients/")
    Call<Client> createClient(@Header("Authorization") String authHeader, @Body Client client);

    @Multipart
    @POST("api/clients/")
    Call<Client> createClient2(@Header("Authorization") String authHeader, @Part("first_name") RequestBody firstName);
}
