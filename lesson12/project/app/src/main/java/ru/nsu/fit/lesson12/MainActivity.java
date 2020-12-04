package ru.nsu.fit.lesson12;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.nsu.fit.lesson12.db.Student;
import ru.nsu.fit.lesson12.db.StudentDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private int idCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final StudentDatabase db = App.getInstance().getDatabase();
                db.studentDao().insertAll(
                        createStudent("Ilya", "Makarov", 20152),
                        createStudent("Ivan", "Ivanov", 20153),
                        createStudent("Petr", "Petrov", 20154));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    private Student createStudent(@NonNull String firstName, @NonNull String secondName, int group) {
        final Student student = new Student();
        student.uid = idCounter;
        student.firstName = firstName;
        student.secondName = secondName;
        student.group = group;

        idCounter += 1;

        return student;
    }

    public void onPrintStudentsFromRoomClicked(View view) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final StudentDatabase db = App.getInstance().getDatabase();
                for (Student student : db.studentDao().selectAll()) {
                    Log.i(LOG_TAG, String.format("%s %s %d", student.firstName, student.secondName, student.group));
                }
            }
        });
    }

    public void onDeleteStudentsFromRoomClicked(View view) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final StudentDatabase db = App.getInstance().getDatabase();
                db.studentDao().deleteAll();
            }
        });
    }

    public void onPrintStudentsFromContentProvider(View view) {
        executorService.submit(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try (Cursor cursor = getContentResolver()
                        .query(Uri.parse("content://ru.nsu.fit.lesson12.provider/student"), null,
                                null, null, null)) {
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            Log.i(LOG_TAG, String.format("%s %s %d", cursor.getString(1),
                                    cursor.getString(2), cursor.getInt(3)));
                        }
                    }
                }
            }
        });
    }

    public void onPrintSingleStudentFromContentProvider(View view) {
        executorService.submit(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try (Cursor cursor = getContentResolver()
                        .query(Uri.parse("content://ru.nsu.fit.lesson12.provider/student/1"), null,
                                null, null, null)) {
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            Log.i(LOG_TAG, String.format("%s %s %d", cursor.getString(1),
                                    cursor.getString(2), cursor.getInt(3)));
                        }
                    }
                }
            }
        });
    }
}