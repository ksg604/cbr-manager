package com.example.cbr_manager.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.cbr_manager.repository.GoalRepository;
import com.example.cbr_manager.service.goal.Goal;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Completable;
import io.reactivex.Single;

@HiltViewModel
public class GoalViewModel extends ViewModel {
    private final GoalRepository goalRepository;

    private final SavedStateHandle savedStateHandle;

    @Inject
    public GoalViewModel(SavedStateHandle savedStateHandle, GoalRepository goalRepository) {
        this.savedStateHandle = savedStateHandle;
        this.goalRepository = goalRepository;
    }

    public LiveData<List<Goal>> getAllGoals() {
        return this.goalRepository.getGoalsAsLiveData();
    }

    public Completable modifyGoal(Goal goal) {
        return this.goalRepository.modifyGoal(goal);
    }

//    public LiveData<Goal> getGoal(int id) {
//        return goalRepository.getGoal(id);
//    }

//    public Single<Goal> getGoalAsSingle(int id) {
//        return goalRepository.getGoalAsSingle(id);
//    }

    public Single<Goal> createGoal(Goal goal) {
        return this.goalRepository.createGoal(goal);
    }

//    public Completable modifyGoal(Goal goal) {
//        return this.goalRepository.modifyGoalgoal);
//    }


}
