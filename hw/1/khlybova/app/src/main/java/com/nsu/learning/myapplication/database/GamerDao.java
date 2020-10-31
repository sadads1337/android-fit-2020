package com.nsu.learning.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GamerDao {
    @Insert
    void insert(Gamer gamer);

    @Update
    void update(Gamer gamer);

    @Delete
    void delete(Gamer gamer);

    @Query("select * from gamer")
    LiveData<List<Gamer>> getAll();

    @Query("SELECT * FROM gamer WHERE id = :id")
    LiveData<Gamer> getById(int id);
}
