package com.example.cbr_manager.service.goal;

import android.content.Context;

import com.example.cbr_manager.data.storage.RoomDB;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GoalDBService {

    private GoalDao goalDao;
    private static GoalDBService Instance;

    public static GoalDBService getInstance(Context context){
        if(Instance == null){
            Instance = new GoalDBService(context);
        }
        return Instance;
    }

    private GoalDBService(Context context){
        goalDao = RoomDB.getDatabase(context).goalDao();
    }

    public void insert(Goal goal){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () -> {
            goalDao.insert(goal);
            return 0;
        };
        Future<Integer> future = executor.submit(callable);

        executor.shutdown();
    }

    public void delete(Goal goal){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () -> {
            goalDao.delete(goal);
            return 0;
        };
        Future<Integer> future = executor.submit(callable);

        executor.shutdown();
    }

    public void update(Goal goal){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () -> {
            goalDao.update(goal);
            return 0;
        };
        Future<Integer> future = executor.submit(callable);

        executor.shutdown();
    }


    public List<Goal> readAll() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Goal>> callable = () -> {
            List<Goal> goals;
            goals = goalDao.readAll();
            return goals;
        };
        Future<List<Goal>> future = executor.submit(callable);

        executor.shutdown();
        return future.get();
    }


    public Goal getById(int id) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Goal[] goal = {new Goal()};
        Callable<Goal> callable = () -> {
            goal[0] = goalDao.getById(id);
            return goal[0];
        };
        Future<Goal> future = executor.submit(callable);

        executor.shutdown();
        return future.get();
    }

    public void clearAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () ->{
            goalDao.clearAll();
            return 0;
        };
        Future<Integer> future = executor.submit(callable);

        executor.shutdown();
    }
}
