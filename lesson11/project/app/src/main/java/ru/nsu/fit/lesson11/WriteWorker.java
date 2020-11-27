package ru.nsu.fit.lesson11;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class WriteWorker extends Worker {
    public static final String CACHE_TAG = "ru.nsu.fit.lesson11.CACHE_TAG";
    public static final String EXTERNAL_TAG = "ru.nsu.fit.lesson11.EXTERNAL_TAG";

    public WriteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
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
        final File file = new File(targetDir, UUID.randomUUID().toString());
        try {
            final FileOutputStream stream = new FileOutputStream(file);
            stream.write("My content".getBytes());
            stream.close();
        } catch (IOException ignore) {
            return Result.failure();
        }
        return Result.success();
    }
}