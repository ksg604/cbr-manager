package com.example.cbr_manager.service.referral;

import com.example.cbr_manager.service.BaseService;
import com.example.cbr_manager.service.auth.AuthResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReferralService extends BaseService {

    ReferralAPI referralAPI;

    public ReferralService(AuthResponse authToken) {
        super(authToken, ReferralAPI.class);
        this.referralAPI = this.getAPI();
    }

    public Call<List<Referral>> getReferrals() {
        return referralAPI.getReferrals(authHeader);
    }

    public Call<Referral> getReferral(int id) {
        return referralAPI.getReferral(authHeader, id);
    }

    public Call<Referral> createReferral(Referral referral) {
        return referralAPI.createReferral(authHeader, referral);
    }

    @Override
    protected ReferralAPI getAPI() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithModifiers();

        ReferralSerializer referralSerializer = new ReferralSerializer();
        gsonBuilder.registerTypeAdapter(Referral.class, referralSerializer);

        Gson gson = gsonBuilder.create();

        return (ReferralAPI) new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(retroFitAPIClass);
    }


}
