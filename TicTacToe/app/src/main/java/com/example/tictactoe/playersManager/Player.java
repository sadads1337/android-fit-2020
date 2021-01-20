package com.example.tictactoe.playersManager;

public class Player {
    public int id;
    public String name;
    public int score;
    public String avatarPath;
    public Player(int id, String name, int score, String avatarPath){
        this.id = id;
        this.name = name;
        this.score = score;
        this.avatarPath = avatarPath;
    }
}
