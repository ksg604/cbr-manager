package com.example.cbr_manager.service.visit;

import android.content.Context;

import com.example.cbr_manager.data.storage.RoomDB;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VisitDBService {
    private VisitDao visitDao;
    private static VisitDBService Instance;

    public static VisitDBService getInstance(Context context){
        if(Instance == null){
            Instance = new VisitDBService(context);
        }
        return Instance;
    }

    private VisitDBService(Context context){
        visitDao = RoomDB.getDatabase(context).visitDao();
    }

    public void insert(Visit visit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () -> {
            visitDao.insert(visit);
            return 0;
        };
        Future<Integer> future = executor.submit(callable);

        executor.shutdown();
    }

    public void delete(Visit visit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () -> {
            visitDao.delete(visit);
            return 0;
        };
        Future<Integer> future = executor.submit(callable);

        executor.shutdown();
    }

    public void update(Visit visit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () -> {
            visitDao.update(visit);
            return 0;
        };
        Future<Integer> future = executor.submit(callable);

        executor.shutdown();
    }


    public List<Visit> readAll() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Visit>> callable = () -> {
            List<Visit> visits;
            visits = visitDao.readAll();
            return visits;
        };
        Future<List<Visit>> future = executor.submit(callable);

        executor.shutdown();
        return future.get();
    }


    public Visit getById(int id) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Visit[] visit = {new Visit()};
        Callable<Visit> callable = () -> {
            visit[0] = visitDao.getById(id);
            return visit[0];
        };
        Future<Visit> future = executor.submit(callable);

        executor.shutdown();
        return future.get();
    }

    public void clearAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () ->{
            visitDao.clearAll();
            return 0;
        };
        Future<Integer> future = executor.submit(callable);

        executor.shutdown();
    }



}
