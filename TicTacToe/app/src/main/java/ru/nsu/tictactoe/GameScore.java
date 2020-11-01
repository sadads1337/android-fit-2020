package ru.nsu.tictactoe;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class GameScore implements Serializable {
    String playerX;
    String playerO;
    int playerXScore;
    int playerOScore;

    public GameScore(String playerX, String playerO, int playerXScore, int playerOScore) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.playerXScore = playerXScore;
        this.playerOScore = playerOScore;
    }

    public String getPlayerX() {
        return playerX;
    }

    public String getPlayerO() {
        return playerO;
    }

    public int getPlayerXScore() {
        return playerXScore;
    }

    public int getPlayerOScore() {
        return playerOScore;
    }
}
