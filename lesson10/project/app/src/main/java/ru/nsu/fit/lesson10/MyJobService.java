package ru.nsu.fit.lesson10;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class MyJobService extends JobIntentService {
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyJobService.class, 1000, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendBroadcast(intent);
    }
}
