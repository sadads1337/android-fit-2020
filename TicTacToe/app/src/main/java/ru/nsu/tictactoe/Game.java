package ru.nsu.tictactoe;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Game implements Serializable {

    private Runnable endgameCallback;
    private int xScore = 0;
    private int oScore = 0;
    private int nRounds = 1;
    private boolean someoneWon = true;
    private boolean playerXActive = true;
    private boolean isRoundOver = false;
    private int[] gameState = { -1, -1, -1, -1, -1, -1, -1, -1, -1 };

    private static final int nCells = 3;
    private static final int roundsInGame = 2;

    private void randomizeActivePlayer() {
        Random rand = new Random();
        playerXActive = rand.nextBoolean();
    }

    private void updatePlayersScore() {
        if (someoneWon) {
            if (playerXActive) {
                xScore++;
            } else {
                oScore++;
            }
        }
    }

    public void startNewRound() {
        if (nRounds == roundsInGame) {
            endgameCallback.run();
            startNewGame();
            return;
        }
        nRounds++;
        someoneWon = true;
        Arrays.fill(gameState, -1);
    }

    public void startNewGame() {
        nRounds = 0;
        xScore = 0;
        oScore = 0;
        isRoundOver = false;
        randomizeActivePlayer();
        startNewRound();
    }

    public boolean foundMatches(int startingPoint, int iIters, int iStep, int jIters, int jStep) {
        int nMatches = 0;
        for (int i = startingPoint; i < iIters; i += iStep) {
            for (int j = i; j < i + jIters - 1; j += jStep) {
                if (gameState[j] != -1 && gameState[j] == gameState[j + jStep]) {
                    nMatches++;
                }
            }
            if (nMatches + 1 == nCells) {
                return true;
            }
            nMatches = 0;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean roundOver() {
        // rows
        if (foundMatches(0, nCells * nCells, nCells, nCells, 1)) {
            updatePlayersScore();
            return true;
        }
        // columns
        if (foundMatches(0, nCells, 1, nCells * nCells - nCells, nCells)) {
            updatePlayersScore();
            return true;
        }
        // cross left
        if (foundMatches(0, nCells, nCells, nCells * nCells - 1, nCells + 1)) {
            updatePlayersScore();
            return true;
        }
        // cross right
        if (foundMatches(nCells - 1, nCells, 1, nCells * nCells - nCells - 1, nCells - 1)) {
            updatePlayersScore();
            return true;
        }
        // check if empty cells left
        if (!Arrays.asList(Arrays.stream(gameState).boxed().toArray(Integer[]::new)).contains(-1)) {
            someoneWon = false;
            updatePlayersScore();
            return true;
        }
        return false;
    }

    public void setEndGameCallback(Runnable callback) {
        this.endgameCallback = callback;
    }

    public int getxScore() {
        return xScore;
    }

    public int getoScore() {
        return oScore;
    }

    public int getnRounds() {
        return nRounds;
    }

    public boolean isSomeoneWon() {
        return someoneWon;
    }

    public boolean isPlayerXActive() {
        return playerXActive;
    }

    public int[] getGameState() {
        return gameState;
    }

    public void setGameState(int[] gameState) {
        this.gameState = gameState;
    }

    public boolean isRoundOver() {
        return isRoundOver;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void makeAMove(int index, int value) {
        gameState[index] = value;
        isRoundOver = roundOver();
        playerXActive = !playerXActive;
    }
}
