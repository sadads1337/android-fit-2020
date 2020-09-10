package ru.nsu.fit.lesson1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public final class SecondActivity extends AppCompatActivity {

    private final Timer timer = new Timer();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Intent intent = getIntent();
        final int sleepTime = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, 0);
        timer.schedule(new Task(), sleepTime);
    }

    private final class Task extends TimerTask {

        @Override
        public final void run() {
            SecondActivity.this.runOnUiThread(new Runnable() {
                @Override
                public final void run() {
                    SecondActivity.this.finish();
                }
            });
        }
    }
}