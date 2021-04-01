package com.example.cbr_manager.service.goal;

import com.example.cbr_manager.service.BaseService;

import java.util.List;

import retrofit2.Call;

public class GoalService extends BaseService {
    private final GoalAPI goalAPI;

    public GoalService(String authToken) {
        super(authToken, GoalAPI.class);
        this.goalAPI = buildRetrofitAPI();
    }

    public Call<Goal> modifyGoal(Goal goal) {
        return this.goalAPI.modifyGoal(authHeader, goal.getId(), goal);
    }

    public Call<List<Goal>> getGoals() {
        return this.goalAPI.getGoals(authHeader);
    }

    public Call<Goal> getGoal(int goalId) {
        return this.goalAPI.getGoal(authHeader, goalId);
    }

    public Call<Goal> createGoal(Goal goal) {
        return this.goalAPI.createGoal(authHeader, goal);
    }
}
