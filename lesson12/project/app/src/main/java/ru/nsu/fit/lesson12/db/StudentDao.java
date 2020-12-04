package ru.nsu.fit.lesson12.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student")
    Cursor selectAllProvider();

    @Query("SELECT * FROM student")
    List<Student> selectAll();

    @Query("SELECT * FROM student WHERE uid IN (:ids)")
    Cursor selectByIds(int[] ids);

    @Insert
    void insertAll(Student... students);

    @Query("DELETE FROM student")
    void deleteAll();
}
