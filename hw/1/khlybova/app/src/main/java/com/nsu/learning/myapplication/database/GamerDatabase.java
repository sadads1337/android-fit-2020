package com.nsu.learning.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Gamer.class}, version = 1)
public abstract class GamerDatabase extends RoomDatabase {
    public abstract GamerDao gamerDao();
}
