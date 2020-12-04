package ru.nsu.fit.lesson12;

import android.app.Application;

import androidx.room.Room;

import ru.nsu.fit.lesson12.db.StudentDatabase;

public class App extends Application {

    public static App instance;

    private StudentDatabase database;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, StudentDatabase.class, "student-db")
                //! Don't use in production
                .allowMainThreadQueries()
                .build();
    }

    public StudentDatabase getDatabase() {
        return database;
    }
}
