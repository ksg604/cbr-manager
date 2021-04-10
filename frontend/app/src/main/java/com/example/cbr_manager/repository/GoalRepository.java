package com.example.cbr_manager.repository;

import com.example.cbr_manager.service.goal.*;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GoalRepository {

    private GoalDao goalDao;
    private GoalAPI goalAPI;
    private String authHeader;

    @Inject
    GoalRepository(GoalDao goalDao, GoalAPI goalAPI, String authHeader) {
        this.goalDao = goalDao;
        this.goalAPI = goalAPI;
        this.authHeader = authHeader;
    }

    public Observable<Goal> getGoals() {
        return goalAPI.getGoalsObs(authHeader)
                .flatMap(Observable::fromIterable)
                .doOnNext(goal -> goalDao.insert(goal))
                .onErrorResumeNext(this::getLocalGoals)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private ObservableSource<? extends Goal> getLocalGoals(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            return goalDao.getGoals().flatMap(Observable::fromIterable);
        }
        return Observable.error(throwable);
    }

    public Single<Goal> getGoal(int id) {
        return goalAPI.getGoalSingle(authHeader, id)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> goalDao.getGoal(id))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Goal> createGoal(Goal goal) {
        return goalAPI.createGoalSingle(authHeader, goal)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> handleOfflineCreateGoal(goal, throwable))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private SingleSource<? extends Goal> handleOfflineCreateGoal(Goal goal, Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            long id = goalDao.insert(goal);
            goal.setId((int) id);
            return Single.just(goal);
        }
        return Single.error(throwable);
    }
}
