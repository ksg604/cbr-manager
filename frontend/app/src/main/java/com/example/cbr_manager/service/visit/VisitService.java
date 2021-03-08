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

    public Call<Visit> createVisitManual(Visit visit) {
        // TODO: This method currently cannot properly pass a client object to the back end.
        // For now, createVisit is used instead, as was done before,
        // but this fails to pass all but the required fields to the visit table.


        RequestBody userCreator = RequestBody.create(Integer.toString(visit.getUserId()), MediaType.parse("text/plain"));
        RequestBody client = RequestBody.create(Integer.toString(visit.getClientId()), MediaType.parse("text/plain"));
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
        RequestBody cbrWorkerName = RequestBody.create(visit.getCbrWorkerName(), MediaType.parse("text/plain"));
        RequestBody locationVisitGPS = RequestBody.create(visit.getLocationVisitGPS(), MediaType.parse("text/plain"));
        RequestBody locationVisitDropDown = RequestBody.create(visit.getLocationDropDown(), MediaType.parse("text/plain"));
        RequestBody villageNoVisit = RequestBody.create(visit.getVillageNoVisit().toString(), MediaType.parse("text/plain"));

        RequestBody wheelChairHealthProvision = RequestBody.create(Boolean.toString(visit.isWheelchairHealthProvision()),
                MediaType.parse("text/plain"));
        RequestBody prostheticHealthProvision = RequestBody.create(Boolean.toString(visit.isProstheticHealthProvision()),
                MediaType.parse("text/plain"));
        RequestBody orthoticHealthProvision = RequestBody.create(Boolean.toString(visit.isOrthoticHealthProvision()),
                MediaType.parse("text/plain"));
        RequestBody repairsHealthProvision = RequestBody.create(Boolean.toString(visit.isRepairsHealthProvision()),
                MediaType.parse("text/plain"));
        RequestBody referralHealthProvision = RequestBody.create(Boolean.toString(visit.isReferralHealthProvision()),
                MediaType.parse("text/plain"));
        RequestBody adviceHealthProvision = RequestBody.create(Boolean.toString(visit.isAdviceHealthProvision()),
                MediaType.parse("text/plain"));
        RequestBody advocacyHealthProvision = RequestBody.create(Boolean.toString(visit.isAdvocacyHealthProvision()),
                MediaType.parse("text/plain"));
        RequestBody encouragementHealthProvision = RequestBody.create(Boolean.toString(visit.isEncouragementHealthProvision()),
                MediaType.parse("text/plain"));

        RequestBody wheelChairHealthProvisionText = RequestBody.create(visit.getWheelchairHealthProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody prostheticHealthProvisionText = RequestBody.create(visit.getProstheticHealthProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody orthoticHealthProvisionText = RequestBody.create(visit.getOrthoticHealthProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody repairsHealthProvisionText = RequestBody.create(visit.getOrthoticHealthProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody referralHealthProvisionText = RequestBody.create(visit.getReferralHealthProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody adviceHealthProvisionText = RequestBody.create(visit.getAdviceHealthProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody advocacyHealthProvisionText = RequestBody.create(visit.getAdvocacyHealthProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody encouragementHealthProvisionText = RequestBody.create(visit.getEncouragementHealthProvisionText(),
                MediaType.parse("text/plain"));

        RequestBody goalMetHealthProvision = RequestBody.create(visit.getGoalMetHealthProvision(),
                MediaType.parse("text/plain"));
        RequestBody conclusionHealthProvision = RequestBody.create(visit.getConclusionHealthProvision(),
                MediaType.parse("text/plain"));

        RequestBody referralEducationProvision = RequestBody.create(Boolean.toString(visit.isReferralEducationProvision()),
                MediaType.parse("text/plain"));
        RequestBody adviceEducationProvision = RequestBody.create(Boolean.toString(visit.isAdviceEducationProvision()),
                MediaType.parse("text/plain"));
        RequestBody advocacyEducationProvision = RequestBody.create(Boolean.toString(visit.isAdvocacyEducationProvision()),
                MediaType.parse("text/plain"));
        RequestBody encouragementEducationProvision = RequestBody.create(Boolean.toString(visit.isEncouragementEducationProvision()),
                MediaType.parse("text/plain"));

        RequestBody referralEducationProvisionText = RequestBody.create(visit.getReferralEducationProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody adviceEducationProvisionText = RequestBody.create(visit.getAdviceEducationProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody advocacyEducationProvisionText = RequestBody.create(visit.getAdvocacyEducationProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody encouragementEducationProvisionText = RequestBody.create(visit.getEncouragementEducationProvisionText(),
                MediaType.parse("text/plain"));

        RequestBody goalMetEducationProvision = RequestBody.create(visit.getGoalMetEducationProvision(),
                MediaType.parse("text/plain"));
        RequestBody conclusionEducationProvision = RequestBody.create(visit.getConclusionEducationProvision(),
                MediaType.parse("text/plain"));

        RequestBody referralSocialProvision = RequestBody.create(Boolean.toString(visit.isReferralSocialProvision()),
                MediaType.parse("text/plain"));
        RequestBody adviceSocialProvision = RequestBody.create(Boolean.toString(visit.isAdviceSocialProvision()),
                MediaType.parse("text/plain"));
        RequestBody advocacySocialProvision = RequestBody.create(Boolean.toString(visit.isAdvocacySocialProvision()),
                MediaType.parse("text/plain"));
        RequestBody encouragementSocialProvision = RequestBody.create(Boolean.toString(visit.isEncouragementSocialProvision()),
                MediaType.parse("text/plain"));

        RequestBody referralSocialProvisionText = RequestBody.create(visit.getReferralSocialProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody adviceSocialProvisionText = RequestBody.create(visit.getAdviceSocialProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody advocacySocialProvisionText = RequestBody.create(visit.getAdvocacySocialProvisionText(),
                MediaType.parse("text/plain"));
        RequestBody encouragementSocialProvisionText = RequestBody.create(visit.getEncouragementSocialProvisionText(),
                MediaType.parse("text/plain"));

        RequestBody goalMetSocialProvision = RequestBody.create(visit.getGoalMetSocialProvision(),
                MediaType.parse("text/plain"));
        RequestBody conclusionSocialProvision = RequestBody.create(visit.getConclusionSocialProvision(),
                MediaType.parse("text/plain"));

        return this.visitAPI.createVisitManual(
                authHeader, visit, userCreator, client, isCBRPurpose, isDisabilityReferralPurpose,
                isDisabilityFollowUpPurpose, isHealthProvision, isEducationProvision, isSocialProvision,
                cbrWorkerName, locationVisitGPS, locationVisitDropDown, villageNoVisit,
                wheelChairHealthProvision, prostheticHealthProvision, orthoticHealthProvision,
                repairsHealthProvision, referralHealthProvision, adviceHealthProvision,
                advocacyHealthProvision, encouragementHealthProvision, wheelChairHealthProvisionText,
                prostheticHealthProvisionText, orthoticHealthProvisionText, repairsHealthProvisionText,
                referralHealthProvisionText, adviceHealthProvisionText, advocacyHealthProvisionText,
                encouragementHealthProvisionText, goalMetHealthProvision, conclusionHealthProvision,
                referralEducationProvision, adviceEducationProvision, advocacyEducationProvision,
                encouragementEducationProvision, referralEducationProvisionText, adviceEducationProvisionText,
                advocacyEducationProvisionText, encouragementEducationProvisionText, goalMetEducationProvision,
                conclusionEducationProvision, referralSocialProvision, adviceSocialProvision,
                advocacySocialProvision, encouragementSocialProvision, referralSocialProvisionText,
                adviceSocialProvisionText, advocacySocialProvisionText, encouragementSocialProvisionText,
                goalMetSocialProvision, conclusionSocialProvision);
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
