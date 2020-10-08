package ru.nsu.fit.lesson5;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String COUNTER = "counter";
    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER, mCount);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        // Called only when R.attr.persistableMode is set to as persistAcrossReboots.
        outState.putInt(COUNTER, mCount);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCount = savedInstanceState.getInt(COUNTER, 0);

        final TextView showCount = findViewById(R.id.show_count);
        if (showCount != null)
            showCount.setText(String.valueOf(mCount));
    }

    public void countUp(View view) {
        mCount++;
        final TextView showCount = findViewById(R.id.show_count);
        if (showCount != null)
            showCount.setText(String.valueOf(mCount));
    }

    public void showToast(View view) {
        final Toast toast = new Toast(this);
        toast.setText(String.format(Locale.getDefault(), "Counter: %d", mCount));
        toast.show();
    }
}