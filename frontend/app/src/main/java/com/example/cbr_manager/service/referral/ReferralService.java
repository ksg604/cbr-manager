package com.example.cbr_manager.service.referral;

import com.example.cbr_manager.service.BaseService;
import com.example.cbr_manager.service.auth.AuthResponse;

import java.util.List;

import retrofit2.Call;

public class ReferralService extends BaseService {

    ReferralAPI referralAPI;

    public ReferralService(AuthResponse authToken) {
        super(authToken, ReferralAPI.class);
        this.referralAPI = this.getAPI();
    }

    public Call<List<Referral>> getReferrals() {
        return referralAPI.getReferrals(authHeader);
    }
}
