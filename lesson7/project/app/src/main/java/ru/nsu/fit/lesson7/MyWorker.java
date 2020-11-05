package ru.nsu.fit.lesson7;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Range;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.sleep;

public class MyWorker extends Worker {

    static final private String WORKER_TAG = MyWorker.class.getSimpleName();

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Log.i(WORKER_TAG, "Started work");
            for (int i = 0; i < 5; ++i) {
                sleep(300);
            }
            Log.i(WORKER_TAG, "Ended work");
            return new Random().nextBoolean()
                    ? Result.success()
                    : Result.failure();
        }
        catch (Exception error) {
            error.printStackTrace();
            return Result.failure();
        }
    }
}
