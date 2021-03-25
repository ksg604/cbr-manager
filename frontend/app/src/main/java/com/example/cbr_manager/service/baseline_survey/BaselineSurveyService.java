package com.example.cbr_manager.service.baseline_survey;

import com.example.cbr_manager.service.BaseService;

import java.util.List;

import retrofit2.Call;

public class BaselineSurveyService extends BaseService {
    private final BaselineSurveyAPI baselineSurveyAPI;

    public BaselineSurveyService(String authToken) {
        super(authToken, BaselineSurveyAPI.class);
        this.baselineSurveyAPI = buildRetrofitAPI();
    }

    public Call<BaselineSurvey> modifyBaselineSurvey(BaselineSurvey baselineSurvey) {
        return this.baselineSurveyAPI.modifyBaselineSurvey(authHeader, baselineSurvey.getId(), baselineSurvey);
    }

    public Call<List<BaselineSurvey>> getBaselineSurveys() {
        return this.baselineSurveyAPI.getBaselineSurveys(authHeader);
    }

    public Call<BaselineSurvey> getBaselineSurvey(int surveyId) {
        return this.baselineSurveyAPI.getBaselineSurvey(authHeader, surveyId);
    }

    public Call<BaselineSurvey> createBaselineSurvey(BaselineSurvey baselineSurvey) {
        return this.baselineSurveyAPI.createBaselineSurvey(authHeader, baselineSurvey);
    }
}
