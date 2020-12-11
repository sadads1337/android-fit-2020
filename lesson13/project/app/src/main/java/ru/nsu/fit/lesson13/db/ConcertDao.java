package ru.nsu.fit.lesson13.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ConcertDao {
    @Query("SELECT * FROM concert")
    DataSource.Factory<Integer, Concert> concerts();

    @Insert
    void insertAll(Concert... concerts);
}
