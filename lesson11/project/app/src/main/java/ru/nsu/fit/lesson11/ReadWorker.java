package ru.nsu.fit.lesson11;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;

public class ReadWorker extends Worker {
    public static final String CACHE_TAG = "ru.nsu.fit.lesson11.CACHE_TAG";
    public static final String EXTERNAL_TAG = "ru.nsu.fit.lesson11.EXTERNAL_TAG";

    public ReadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        final Data inputData = getInputData();
        final boolean forCache = inputData.getBoolean(CACHE_TAG, false);
        final boolean external = inputData.getBoolean(EXTERNAL_TAG, false);
        final File targetDir = forCache
            ? external
                ? getApplicationContext().getExternalFilesDir(null)
                : getApplicationContext().getFilesDir()
            : external
                ? getApplicationContext().getExternalCacheDir()
                : getApplicationContext().getCacheDir();

        if (targetDir == null) {
            return Result.failure();
        }

        final File[] files = targetDir.listFiles();
        if (files == null) {
            return Result.failure();
        }

        for (final File file : files) {
            Log.i(ReadWorker.class.getSimpleName(), file.getAbsolutePath());
        }

        return Result.success();
    }
}
