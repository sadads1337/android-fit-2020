package ru.nsu.fit.lesson2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "ru.nsu.fit.lesson2.url";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button findButton = findViewById(R.id.find_button);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFind(view);
            }
        });

        final ImageView cameraButton = findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo Implement me and call startAnother().
            }
        });
    }

    public final void onFind(View view) {
        Log.i(LOG_TAG, "Find button clicked!");
        final EditText url = findViewById(R.id.url_address);
        startAnother(url.getText().toString());
    }

    private void startAnother(String url) {
        final Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, url);
        startActivity(intent);
    }
}