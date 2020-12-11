package ru.nsu.fit.lesson13.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Concert {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    public String name;
}
