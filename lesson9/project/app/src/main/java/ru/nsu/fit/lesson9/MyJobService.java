package ru.nsu.fit.lesson9;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class MyJobService extends JobIntentService {
    private static final int JOB_ID = 1000;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyJobService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        toast("Starting job service");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}
    }

    @Override
    public boolean onStopCurrentWork() {
        // Nothing here.
        return super.onStopCurrentWork();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toast("Finished job service");
    }

    @SuppressWarnings("deprecation")
    final Handler mHandler = new Handler();

    // Helper for showing toasts
    void toast(@NonNull final String text) {
        mHandler.post(new Runnable() {
            @Override public void run() {
                Toast.makeText(MyJobService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
