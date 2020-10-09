package ru.nsu.fit.lesson3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView);
        final boolean tabletSize = getResources().getBoolean(R.bool.is_tablet);

        if (tabletSize) {
            textView.setText("tablet");
        } else {
            textView.setText("phone");
        }
    }
}