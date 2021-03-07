package com.example.cbr_manager.service.visit;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.utils.Helper;
import com.example.cbr_manager.service.auth.AuthResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitService {
    private static final String BASE_URL = BuildConfig.API_URL;

    private final AuthResponse authToken;

    private final String authHeader;

    private VisitAPI visitAPI;

    public VisitService(AuthResponse authToken) {
        this.authToken = authToken;
        this.authHeader = Helper.formatTokenHeader(this.authToken);
        this.visitAPI = getVisitAPI();
    }

    public VisitService() {
        this.authToken = null;
        this.authHeader = null;
        this.visitAPI = getVisitAPI();
    }


    private VisitAPI getVisitAPI() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(VisitAPI.class);
    }

    public Call<Client> createVisitManual(Visit visit) {
        // TODO: Add more client fields when finalized. Restricted to manual fields for now.
        // Need to manually build client request object because of its image field

        RequestBody userCreator = RequestBody.create(Integer.toString(visit.getUserId()), MediaType.parse("text/plain"));
        RequestBody isCBRPurpose = RequestBody.create(Boolean.toString(visit.isCBRPurpose()), MediaType.parse("text/plain"));
        RequestBody isDisabilityReferralPurpose = RequestBody.create(Boolean.toString(visit.isDisabilityReferralPurpose()),
                MediaType.parse("text/plain"));
        RequestBody isDisabilityFollowUpPurpose = RequestBody.create(Boolean.toString(visit.isDisabilityFollowUpPurpose()),
                MediaType.parse("text/plain"));
        RequestBody isHealthProvision = RequestBody.create(Boolean.toString(visit.isHealthProvision()),
                MediaType.parse("text/plain"));
        RequestBody isSocialProvision = RequestBody.create(Boolean.toString(visit.isSocialProvision()),
                MediaType.parse("text/plain"));
        RequestBody isEducationProvision = RequestBody.create(Boolean.toString(visit.isEducationProvision()),
                MediaType.parse("text/plain"));
        RequestBody villageNo = RequestBody.create(client.getVillageNo().toString(), MediaType.parse("text/plain"));
        RequestBody gender = RequestBody.create(client.getGender(), MediaType.parse("text/plain"));
        RequestBody age = RequestBody.create(client.getAge().toString(), MediaType.parse("text/plain"));
        RequestBody contactClient = RequestBody.create(client.getContactClient().toString(), MediaType.parse("text/plain"));
        RequestBody carePresent = RequestBody.create(client.getCarePresent(), MediaType.parse("text/plain"));
        RequestBody contactCare = RequestBody.create(client.getContactCare().toString(), MediaType.parse("text/plain"));
        RequestBody disability = RequestBody.create(client.getDisability(), MediaType.parse("text/plain"));
        RequestBody healthRisk = RequestBody.create(client.getHealthRisk().toString(), MediaType.parse("text/plain"));
        RequestBody socialRisk = RequestBody.create(client.getSocialRisk().toString(), MediaType.parse("text/plain"));
        RequestBody educationRisk = RequestBody.create(client.getEducationRisk().toString(), MediaType.parse("text/plain"));

        return this.visitAPI.createVisitManual(
                authHeader,

        );
    }

    public Call<List<Visit>> getVisits() {
        return this.visitAPI.getVisits(authHeader);
    }

    public Call<Visit> getVisit(int visitID) {
        return this.visitAPI.getVisit(authHeader, visitID);
    }

    public Call<Visit> createVisit(Visit visit) {
        return this.visitAPI.createVisit(authHeader, visit);
    }




}
