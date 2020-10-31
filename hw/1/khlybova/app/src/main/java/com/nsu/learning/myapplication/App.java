package com.nsu.learning.myapplication;

import android.app.Application;

import androidx.room.Room;

import com.nsu.learning.myapplication.database.GamerDatabase;

public class App extends Application {

    public static App instance;

    private GamerDatabase database;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, GamerDatabase.class, "database")
                .build();
    }

    public GamerDatabase getDatabase() {
        return database;
    }
}
