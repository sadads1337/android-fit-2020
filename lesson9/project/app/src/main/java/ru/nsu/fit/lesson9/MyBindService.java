package ru.nsu.fit.lesson9;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyBindService extends Service {
    private final IBinder binder = new LocalBinder();
    private final Random generator = new Random();

    public class LocalBinder extends Binder {
        MyBindService getService() {
            return MyBindService.this;
        }
    }

    public int getRandomNumber() {
        return generator.nextInt(100);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "Service binded", Toast.LENGTH_SHORT).show();
        return binder;
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
        Toast.makeText(this, "Service done", Toast.LENGTH_SHORT).show();
        // Nothing here.
        super.onDestroy();
    }
}
