package ru.nsu.fit.lesson13;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.nsu.fit.lesson13.db.Concert;
import ru.nsu.fit.lesson13.db.ConcertDatabase;

public class MainActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ConcertAdapter adapter = new ConcertAdapter();
    private int idCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDb();

        final ConcertViewModel viewModel = new ViewModelProvider.NewInstanceFactory().create(ConcertViewModel.class);
        viewModel.concertList.observe(this, adapter::submitList);

        final RecyclerView recyclerView = findViewById(R.id.list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    private void initDb() {
        executorService.submit(() -> {
            final ConcertDatabase db = App.getInstance().getDatabase();
            for (int i = 0; i < 100; ++i) {
                db.concertDao().insertAll(createConcert(UUID.randomUUID().toString()));
            }
        });
    }

    private Concert createConcert(@NonNull String name) {
        final Concert student = new Concert();
        student.uid = idCounter;
        student.name = name;
        idCounter += 1;
        return student;
    }


}