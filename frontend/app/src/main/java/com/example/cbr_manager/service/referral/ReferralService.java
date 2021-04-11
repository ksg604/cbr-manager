package com.example.cbr_manager.service.referral;

import com.example.cbr_manager.service.BaseService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReferralService extends BaseService {

    ReferralAPI referralAPI;

    public ReferralService(String authToken) {
        super(authToken, ReferralAPI.class);
        this.referralAPI = this.buildRetrofitAPI();
    }

    public Call<List<Referral>> getReferrals() {
        return referralAPI.getReferrals(authHeader);
    }

    public Call<Referral> getReferral(int id) {
        return referralAPI.getReferral(authHeader, id);
    }

    public Call<Referral> modifyReferral(Referral referral) {
        return referralAPI.modifyReferral(authHeader, referral.getId(), referral);
    }

    public Call<Referral> createReferral(Referral referral) {
        return referralAPI.createReferral(authHeader, referral);
    }

    public Call<ResponseBody> uploadReferralPhoto(File file, int referralId) {
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
        return referralAPI.uploadPhoto(authHeader, referralId, body);
    }
    @Override
    protected ReferralAPI buildRetrofitAPI() {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();

        ReferralSerializer referralSerializer = new ReferralSerializer();
        gsonBuilder.registerTypeAdapter(Referral.class, referralSerializer);

        Gson gson = gsonBuilder.create();

        return (ReferralAPI) new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(retroFitAPIClass);
    }

}
