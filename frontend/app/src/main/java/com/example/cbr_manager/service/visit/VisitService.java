package com.example.cbr_manager.service.visit;

import com.example.cbr_manager.BuildConfig;
import com.example.cbr_manager.service.BaseService;

import java.util.List;

import retrofit2.Call;

public class VisitService extends BaseService {
    private final VisitAPI visitAPI;

    public VisitService(String authToken) {
        super(authToken, VisitAPI.class);
        this.visitAPI = buildRetrofitAPI();
    }

    public Call<Visit> modifyVisit(Visit visit) {
        return this.visitAPI.modifyVisit(authHeader, visit.getId(), visit);
    }
}
