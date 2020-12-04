package ru.nsu.fit.lesson12.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "second_name")
    public String secondName;

    @ColumnInfo(name = "group")
    public int group;
}
