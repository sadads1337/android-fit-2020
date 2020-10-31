package com.nsu.learning.myapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Gamer {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int score;

    private String photoResource;

    public Gamer(String name) {
        this.name = name;
    }

    public void increaseScore() {
        score++;
    }
}
