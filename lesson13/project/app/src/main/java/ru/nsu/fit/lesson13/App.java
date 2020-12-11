package ru.nsu.fit.lesson13;

import android.app.Application;

import androidx.room.Room;

import ru.nsu.fit.lesson13.db.ConcertDatabase;

public class App extends Application {

    public static App instance;

    private ConcertDatabase database;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, ConcertDatabase.class, "concert-db")
                .build();
    }

    public ConcertDatabase getDatabase() {
        return database;
    }
}