package com.example.cbr_manager.workmanager.goal;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Data;
import androidx.work.RxWorker;
import androidx.work.WorkerParameters;

import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.service.goal.GoalAPI;
import com.example.cbr_manager.service.goal.GoalDao;
import com.example.cbr_manager.utils.Helper;

import org.jetbrains.annotations.NotNull;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.Single;

@HiltWorker
public class CreateGoalWorker extends RxWorker {

    public static final String KEY_AUTH_HEADER = "KEY_AUTH_HEADER";
    public static final String KEY_GOAL_OBJ_ID = "KEY_GOAL_OBJ_ID";
    private static final String TAG = CreateGoalWorker.class.getSimpleName();
    private final GoalAPI goalAPI;
    private final GoalDao goalDao;

    @AssistedInject
    public CreateGoalWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params, GoalAPI goalAPI, GoalDao goalDao) {
        super(context, params);

        this.goalAPI = goalAPI;
        this.goalDao = goalDao;
    }

    public static Data buildInputData(String authHeader, int goalId) {
        Data.Builder builder = new Data.Builder();
        builder.putString(CreateGoalWorker.KEY_AUTH_HEADER, authHeader);
        builder.putInt(CreateGoalWorker.KEY_GOAL_OBJ_ID, goalId);
        return builder.build();
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        String authHeader = getInputData().getString(KEY_AUTH_HEADER);
        int goalObjId = getInputData().getInt(KEY_GOAL_OBJ_ID, -1);

        return goalDao.getGoalAsSingle(goalObjId)
                .flatMap(localGoal -> goalAPI.createGoalSingle(authHeader, localGoal)
                        .doOnSuccess(serverGoal -> updateDBEntry(localGoal, serverGoal)))
                .map(goalSingle -> {
                    Log.d(TAG, "created Goal: " + goalSingle.getId());
                    return Result.success();
                })
                .onErrorReturn(this::handleReturnResult);
    }

    private void updateDBEntry(Goal localGoal, Goal goalResult) {
        Integer localId = localGoal.getId();
        goalResult.setId(localId);
        goalDao.update(goalResult);
    }

    @NotNull
    private Result handleReturnResult(Throwable throwable) {
        if (Helper.isConnectionError(throwable)) {
            return Result.retry();
        }
        return Result.failure();
    }
}
