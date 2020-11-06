package ru.nsu.fit.lesson8.workers;

import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.atomic.AtomicInteger;

import ru.nsu.fit.lesson8.R;

// Don't use in production bad way...
class NotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);

    public static int getID() {
        return c.incrementAndGet();
    }
}

public class MockedWorker extends Worker {
    public static final String CHANNEL_ID = "channel";

    public MockedWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            final int sleepTimeSec = 5;

            final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            final Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(MockedWorker.class.getSimpleName())
                    .setContentText(String.format("Task sleep for %s", sleepTimeSec))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build();
            notificationManager.notify(NotificationID.getID(), notification);

            Thread.sleep(sleepTimeSec * 1000);
            return Result.success();
        } catch (InterruptedException ignore) {
        }
        return Result.failure();
    }
}
