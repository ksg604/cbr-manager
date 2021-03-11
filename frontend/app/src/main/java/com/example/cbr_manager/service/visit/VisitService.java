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
