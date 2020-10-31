package com.nsu.learning.myapplication;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.learning.myapplication.database.Gamer;

import static com.nsu.learning.myapplication.CellState.CROSS;
import static com.nsu.learning.myapplication.CellState.ZERO;
import static com.nsu.learning.myapplication.GameFieldState.CROSS_WINS;
import static com.nsu.learning.myapplication.GameFieldState.GAME_CONTINUES;
import static com.nsu.learning.myapplication.GameFieldState.NO_ONE_WINS;
import static com.nsu.learning.myapplication.GameFieldState.ZERO_WINS;
import static com.nsu.learning.myapplication.GamerInfo.firstPlayerCross;
import static com.nsu.learning.myapplication.GamerInfo.secondPlayerCross;
import static com.nsu.learning.myapplication.GamerOrder.FIRST;

public class Game {
    private static final int FIRST_PLAYER = 0;
    private static final int SECOND_PLAYER = 1;
    private Gamer firstGamer;
    private Gamer secondGamer;
    private MutableLiveData<GameField> gameField;
    private int round = 0;
    private GamerInfo activePlayer;
    private int[] curScore = new int[2];

    public String getCurScore() {
        return curScore[FIRST_PLAYER] + " : " + curScore[SECOND_PLAYER];
    }

    GameFieldState setCellValue(int position) {
        GameFieldState fieldState = gameField.getValue().setCellValue(position, activePlayer.getCellType());
        if (GAME_CONTINUES.equals(fieldState)) {
            activePlayer.changeGamer();
            return GAME_CONTINUES;
        }
        gameField.setValue(new GameField());
        if (CROSS_WINS.equals(fieldState) && ZERO.equals(activePlayer.getCellType())) {
            throw new IllegalStateException("Неправильная игровая логика");
        }
        if (ZERO_WINS.equals(fieldState) && CROSS.equals(activePlayer.getCellType())) {
            throw new IllegalStateException("Неправильная игровая логика");
        }
        if (NO_ONE_WINS.equals(fieldState)) {
            round++;
            startGame();
            return fieldState;
        }
        if (activePlayer.getGamerOrder() == FIRST) {
            curScore[FIRST_PLAYER]++;
            firstGamer.increaseScore();
        } else {
            curScore[SECOND_PLAYER]++;
            secondGamer.increaseScore();
        }
        round++;
        startGame();
        return fieldState;
    }

    boolean canCellBeSet(int position) {
        return getGameField().getValue().canCellBeSet(position);
    }

    @NonNull
    public LiveData<GameField> getGameField() {
        if (gameField == null || gameField.getValue() == null) {
            gameField = new MutableLiveData<>();
            gameField.setValue(new GameField());
        }
        return gameField;
    }

    public Gamer getFirstGamer() {
        return firstGamer;
    }

    public void setFirstGamer(Gamer firstGamer) {
        this.firstGamer = firstGamer;
    }

    public Gamer getSecondGamer() {
        return secondGamer;
    }

    public void setSecondGamer(Gamer secondGamer) {
        this.secondGamer = secondGamer;
    }

    public int getRound() {
        return round;
    }

    public GamerInfo getActivePlayer() {
        return activePlayer;
    }

    public void startGame() {
        if (round == 0) {
            round = 1;
        }
        activePlayer = round % 2 != 0 ? firstPlayerCross() : secondPlayerCross();
    }
}
