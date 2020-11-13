package ru.nsu.fit.lesson9;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyForegroundService extends Service {
    public static final String CHANNEL_ID = MyForegroundService.class.getSimpleName();
    public static final String TEXT = MyForegroundService.class.getCanonicalName();

    @Override
    public void onCreate() {
        // Nothing here.
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Intent notificationIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        final Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(MyForegroundService.class.getSimpleName())
                // Care here its throws.
                .setContentText(intent.getStringExtra(TEXT))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        // We have to call it within 5 secs if MainActivity calls startForegroundService(...).
        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null.
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // Nothing here.
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        // Nothing here.
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        // Nothing here.
        super.onDestroy();
    }
}
