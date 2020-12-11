package ru.nsu.fit.lesson13.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Concert.class}, version = 1, exportSchema = false)
public abstract class ConcertDatabase extends RoomDatabase {
    public abstract ConcertDao concertDao();
}