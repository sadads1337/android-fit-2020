package ru.nsu.fit.lesson8;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.InputMerger;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

import ru.nsu.fit.lesson8.workers.MockedWorker;

public class MainActivity extends AppCompatActivity implements Configuration.Provider {
    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setExecutor(Executors.newFixedThreadPool(3))
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.work_button);
        if (button != null) {
            button.setBackgroundColor(Color.GREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            final NotificationChannel channel = new NotificationChannel(MockedWorker.CHANNEL_ID,
                    "unimportant channel name", importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void onWorkClicked(View view) {
        final Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
        final OneTimeWorkRequest lastInChain = new OneTimeWorkRequest.Builder(MockedWorker.class)
                .setInputMerger(MyInputMerger.class)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this)
                .beginWith(createWorkRequest(5))
                .then(Arrays.asList(
                        createWorkRequest(1),
                        createWorkRequest(2),
                        createWorkRequest(3)))
                .then(lastInChain)
                .enqueue();

        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        final UUID lastInChainId = lastInChain.getId();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(lastInChainId).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null) {
                    final WorkInfo.State state = workInfo.getState();
                    if (state == WorkInfo.State.SUCCEEDED || state == WorkInfo.State.FAILED) {
                        onWorkFinished(workInfo.getState());
                    }
                }
            }
        });

        final Button button = findViewById(R.id.work_button);
        if (button != null) {
            button.setClickable(false);
            button.setBackgroundColor(Color.RED);
        }
    }

    public void onWorkFinished(@NonNull WorkInfo.State state) {
        Toast.makeText(this, String.format("Work finished %s", state.toString()), Toast.LENGTH_LONG)
                .show();

        final Button button = findViewById(R.id.work_button);
        if (button != null) {
            button.setClickable(true);
            button.setBackgroundColor(Color.GREEN);
        }
        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @NonNull
    private OneTimeWorkRequest createWorkRequest(int value) {
        final Data inputData = new Data.Builder()
                .putInt(MockedWorker.INPUT_TAG, value)
                .build();
        return new OneTimeWorkRequest.Builder(MockedWorker.class)
                .setInputData(inputData)
                .build();
    }

    public static class MyInputMerger extends InputMerger {
        @NonNull
        @Override
        public Data merge(@NonNull List<Data> inputs) {
            final Data.Builder outputBuilder = new Data.Builder();
            final Map<String, Object> mergedValues = new HashMap<>();
            for (Data input : inputs) {
                for (Map.Entry<String, Object> entry : input.getKeyValueMap().entrySet()) {
                    if (mergedValues.containsKey(entry.getKey())) {
                        try {
                            // Don't use in production bad way...
                            mergedValues.put(entry.getKey(), (int) mergedValues.get(entry.getKey()) + (int) entry.getValue());
                        } catch (Throwable ignore) {
                        }
                    } else {
                        mergedValues.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            return outputBuilder
                    .putAll(mergedValues)
                    .build();
        }
    }
}