package ru.nsu.fit.lesson11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void storageWork(boolean external) {
        final Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build();
        final OneTimeWorkRequest writeRequest = new OneTimeWorkRequest.Builder(WriteWorker.class)
                .setInputData(new Data.Builder()
                        .putBoolean(WriteWorker.CACHE_TAG, false)
                        .putBoolean(WriteWorker.EXTERNAL_TAG, external)
                        .build())
                .setConstraints(constraints)
                .build();
        final OneTimeWorkRequest writeCacheRequest = new OneTimeWorkRequest.Builder(WriteWorker.class)
                .setInputData(new Data.Builder()
                        .putBoolean(WriteWorker.CACHE_TAG, true)
                        .putBoolean(WriteWorker.EXTERNAL_TAG, external)
                        .build())
                .setConstraints(constraints)
                .build();
        final OneTimeWorkRequest readRequest = new OneTimeWorkRequest.Builder(ReadWorker.class)
                .setInputData(new Data.Builder()
                        .putBoolean(ReadWorker.CACHE_TAG, false)
                        .putBoolean(ReadWorker.EXTERNAL_TAG, external)
                        .build())
                .build();
        final OneTimeWorkRequest readCacheRequest = new OneTimeWorkRequest.Builder(ReadWorker.class)
                .setInputData(new Data.Builder()
                        .putBoolean(ReadWorker.CACHE_TAG, true)
                        .putBoolean(ReadWorker.EXTERNAL_TAG, external)
                        .build())
                .build();
        WorkManager.getInstance(this)
                .beginWith(Arrays.asList(writeRequest, writeCacheRequest))
                .then(readRequest)
                .then(readCacheRequest)
                .enqueue();
    }

    public void buttonInternalClicked(@NonNull View view) {
        storageWork(false);
    }

    public void buttonExternalClicked(@NonNull View view) {
        storageWork(true);
    }
}