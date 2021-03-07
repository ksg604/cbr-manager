package com.example.cbr_manager.service.visit;

import com.example.cbr_manager.service.client.Client;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface VisitAPI {

    @GET("api/visits/")
    Call<List<Visit>> getVisits(@Header("Authorization") String authHeader);

    @GET("api/visits/{id}")
    Call<Visit> getVisit(@Header("Authorization") String authHeader, @Path("id") int id);

    @POST("api/visits/")
    Call<Visit> createVisit(@Header("Authorization") String authHeader, @Body Visit visit);

    @Multipart
    @POST("api/visits/")
    Call<Visit> createVisitManual(@Header("Authorization") String authHeader,
                                    @Body Visit visit,
                                    @Part("user_creator") RequestBody userCreator,
                                    @Part("client") RequestBody client,
                                    @Part("is_cbr_purpose") RequestBody isCBRPurpose,
                                    @Part("is_disability_referral_purpose") RequestBody isDisabilityReferralPurpose,
                                    @Part(" is_disability_follow_up_purpose ") RequestBody isDisabilityFollowUpPurpose,
                                    @Part("is_health_provision") RequestBody isHealthProvision,
                                    @Part("is_education_provision") RequestBody isEducationProvision,
                                    @Part("is_social_provision") RequestBody isSocialProvision,
                                    @Part("cbr_worker_name") RequestBody cbrWorkerName,
                                    @Part("location_visit_gps") RequestBody locationVisitGPS,
                                    @Part("location_drop_down") RequestBody locationDropDown,
                                    @Part("village_no_visit") RequestBody villageNoVisit,
                                    @Part("wheelchair_health_provision") RequestBody wheelchairHealthProvision,
                                    @Part("prosthetic_health_provision") RequestBody prostheticHealthProvision,
                                    @Part("orthotic_health_provision") RequestBody orthoticHealthProvision,
                                    @Part("repairs_health_provision") RequestBody repairsHealthProvision,
                                    @Part("referral_health_provision") RequestBody referralHealthProvision,
                                    @Part("advice_health_provision") RequestBody adviceHealthProvision,
                                    @Part("advocacy_health_provision") RequestBody advocacyHealthProvision,
                                    @Part("encouragement_health_provision") RequestBody encouragementHealthProvision,
                                    @Part("wheelchair_health_provision_text") RequestBody wheelchairHealthProvisionText,
                                    @Part("prosthetic_health_provision_text") RequestBody prostheticHealthProvisionText,
                                    @Part("orthotic_health_provision_text") RequestBody orthoticHealthProvisionText,
                                    @Part("repairs_health_provision_text") RequestBody repairsHealthProvisionText,
                                    @Part("referral_health_provision_text") RequestBody referralHealthProvisionText,
                                    @Part("advice_health_provision_text") RequestBody adviceHealthProvisionText,
                                    @Part("advocacy_health_provision_text") RequestBody advocacyHealthProvisionText,
                                    @Part("encouragement_health_provision_text") RequestBody encouragementHealthProvisionText,
                                    @Part("goal_met_health_provision") RequestBody goalMetHealthProvision,
                                    @Part("conclusion_health_provision") RequestBody conclusionHealthProvision,
                                    @Part("referral_education_provision") RequestBody referralEducationProvision,
                                    @Part("advice_education_provision") RequestBody adviceEducationProvision,
                                    @Part("advocacy_education_provision") RequestBody advocacyEducationProvision,
                                    @Part("encouragement_education_provision") RequestBody encouragementEducationProvision,
                                    @Part("referral_education_provision_text") RequestBody referralEducationProvisionText,
                                    @Part("advice_education_provision_text") RequestBody adviceEducationProvisionText,
                                    @Part("advocacy_education_provision_text") RequestBody advocacyEducationProvisionText,
                                    @Part("encouragement_education_provision_text") RequestBody encouragementEducationProvisionText,
                                    @Part("goal_met_education_provision") RequestBody goalMetEducationProvision,
                                    @Part("conclusion_education_provision") RequestBody conclusionEducationProvision,
                                    @Part("referral_social_provision") RequestBody referralSocialProvision,
                                    @Part("advice_social_provision") RequestBody adviceSocialProvision,
                                    @Part("advocacy_social_provision") RequestBody advocacySocialProvision,
                                    @Part("encouragement_social_provision") RequestBody encouragementSocialProvision,
                                    @Part("referral_social_provision_text") RequestBody referralSocialProvisionText,
                                    @Part("advice_social_provision_text") RequestBody adviceSocialProvisionText,
                                    @Part("advocacy_social_provision_text") RequestBody advocacySocialProvisionText,
                                    @Part("encouragement_social_provision_text") RequestBody encouragementSocialProvisionText,
                                    @Part("goal_met_social_provision") RequestBody goalMetSocialProvision,
                                    @Part("conclusion_social_provision") RequestBody conclusionSocialProvision);

    }
