package com.example.cbr_manager.service.client;

import java.util.List;

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

/*
Define our API request endpoints here
 */
public interface ClientAPI {
    @GET("api/clients/")
    Call<List<Client>> getClients(@Header("Authorization") String authHeader);

    @GET("api/clients/{id}")
    Call<Client> getClient(@Header("Authorization") String authHeader, @Path("id") int id);

    @PUT("api/clients/{id}/")
    Call<Client> modifyClient(@Header("Authorization") String authHeader, @Path("id") int id, @Body Client client);

    @POST("api/clients/")
    Call<Client> createClient(@Header("Authorization") String authHeader, @Body Client client);

    @Multipart
    @POST("api/clients/{id}/upload/")
    Call<ResponseBody> uploadPhoto(@Header("Authorization") String authHeader, @Path("id") int id, @Part MultipartBody.Part photo);

    @Multipart
    @POST("api/clients/")
    Call<Client> createClientManual(@Header("Authorization") String authHeader,
                                    @Part("first_name") RequestBody firstName,
                                    @Part("last_name") RequestBody lastName,
                                    @Part("location") RequestBody location,
                                    @Part("consent") RequestBody consent,
                                    @Part("village_no") RequestBody villageNo,
                                    @Part("gender") RequestBody gender,
                                    @Part("age") RequestBody age,
                                    @Part("contact_client") RequestBody contactClient,
                                    @Part("care_present") RequestBody carePresent,
                                    @Part("contact_care") RequestBody contactCare,
                                    @Part("disability") RequestBody disability,
                                    @Part("health_risk") RequestBody healthRisk,
                                    @Part("social_risk") RequestBody socialRisk,
                                    @Part("education_risk") RequestBody educationRisk);
}
