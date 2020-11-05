package ru.nsu.fit.lesson7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private UUID workId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.work_button);
        if (button != null) {
            button.setBackgroundColor(Color.GREEN);
        }
        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void onWorkClicked(View view) {
        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();

        if (ru.nsu.fit.lesson7.BuildConfig.DEBUG && workId != null) {
            throw new AssertionError("Assertion failed");
        }
        workId = request.getId();

        WorkManager.getInstance(this).enqueue(request);

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workId).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null) {
                    if (workInfo.getState() == WorkInfo.State.RUNNING)
                    {
                        final ProgressBar progressBar = findViewById(R.id.progress_bar);
                        if (progressBar != null) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                    final WorkInfo.State state = workInfo.getState();
                    if (state == WorkInfo.State.SUCCEEDED || state == WorkInfo.State.FAILED)
                    {
                        onWorkFinished(state);
                    }
                    //! \todo: Handle other cases
                }
            }

        });

        final Button button = findViewById(R.id.work_button);
        if (button != null) {
            button.setClickable(false);
            button.setBackgroundColor(Color.RED);
        }
    }

    public void onWorkFinished(WorkInfo.State state) {
        if (ru.nsu.fit.lesson7.BuildConfig.DEBUG && workId == null) {
            throw new AssertionError("Assertion failed");
        }

        final Toast toast = new Toast(this);
        toast.setText("Work finished " + workId.toString());
        toast.setText("Work result " + state.toString());
        toast.show();
        workId = null;

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
}