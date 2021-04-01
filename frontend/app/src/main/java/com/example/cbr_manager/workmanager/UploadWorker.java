package com.example.cbr_manager.workmanager;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;

@HiltWorker
public class UploadWorker extends Worker {

    @AssistedInject
    public UploadWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.
//        uploadImages();

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}
