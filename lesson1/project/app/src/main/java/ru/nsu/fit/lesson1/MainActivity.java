package ru.nsu.fit.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "SLEEP_TIME";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public final void onClick(View view) {
        Log.i(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 10000);
        startActivity(intent);
    }
}