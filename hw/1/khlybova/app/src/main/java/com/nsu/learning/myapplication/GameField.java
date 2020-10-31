package com.nsu.learning.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nsu.learning.myapplication.CellState.CLEAR;
import static com.nsu.learning.myapplication.CellState.CROSS;
import static com.nsu.learning.myapplication.GameFieldState.CROSS_WINS;
import static com.nsu.learning.myapplication.GameFieldState.GAME_CONTINUES;
import static com.nsu.learning.myapplication.GameFieldState.NO_ONE_WINS;
import static com.nsu.learning.myapplication.GameFieldState.ZERO_WINS;

public class GameField {
    private CellState[] cells = {CLEAR, CLEAR, CLEAR,
            CLEAR, CLEAR, CLEAR,
            CLEAR, CLEAR, CLEAR};

    GameFieldState setCellValue(int position, CellState cellState) {
        if (position >= cells.length || CLEAR.equals(cellState)) {
            throw new IllegalArgumentException("Некорректное обращение к игровому полю");
        }
        cells[position] = cellState;
        return getFieldState();
    }

    boolean canCellBeSet(int position) {
        if (position < 0 || position >= cells.length) {
            return false;
        }
        return CLEAR.equals(cells[position]);
    }

    public CellState[] getCells() {
        return cells;
    }

    private GameFieldState getFieldState() {
        List<GameFieldState> gameFieldStates = getFieldStates();
        if (gameFieldStates.contains(CROSS_WINS) && gameFieldStates.contains(ZERO_WINS)) {
            throw new IllegalStateException("Неправильная игровая логика в подсчете победы");
        }
        if (gameFieldStates.contains(CROSS_WINS)) {
            return CROSS_WINS;
        }
        if (gameFieldStates.contains(ZERO_WINS)) {
            return ZERO_WINS;
        }
        if (gameFieldStates.contains(NO_ONE_WINS)) {
            return NO_ONE_WINS;
        }
        return GAME_CONTINUES;
    }

    private List<GameFieldState> getFieldStates() {
        List<GameFieldState> states = new ArrayList<>(4);
        states.add(checkHorizontalState());
        states.add(checkVerticalState());
        states.add(checkDiagonalStates());
        if (isFull()) {
            states.add(NO_ONE_WINS);
        }
        return states;
    }

    private boolean isFull() {
        return Arrays.stream(cells)
                .noneMatch(CLEAR::equals);
    }

    private GameFieldState checkHorizontalState() {
        for (int i = 0; i < cells.length; i += 3) {
            if (CLEAR.equals(cells[i])) {
                continue;
            }
            if (cells[i].equals(cells[i + 1]) && cells[i].equals(cells[i + 2])) {
                return getWinnerByCell(cells[i]);
            }
        }
        return GAME_CONTINUES;
    }

    private GameFieldState checkVerticalState() {
        for (int i = 0; i < 3; i++) {
            if (CLEAR.equals(cells[i])) {
                continue;
            }
            if (cells[i].equals(cells[i + 3]) && cells[i].equals(cells[i + 6])) {
                return getWinnerByCell(cells[i]);
            }
        }
        return GAME_CONTINUES;
    }

    private GameFieldState checkDiagonalStates() {
        CellState centerCell = cells[4];
        if (CLEAR.equals(centerCell)) {
            return GAME_CONTINUES;
        }
        if (centerCell.equals(cells[0]) && centerCell.equals(cells[8])) {
            return getWinnerByCell(centerCell);
        }
        if (centerCell.equals(cells[2]) && centerCell.equals(cells[6])) {
            return getWinnerByCell(centerCell);
        }
        return GAME_CONTINUES;
    }

    private GameFieldState getWinnerByCell(CellState cell) {
        return CROSS.equals(cell) ? CROSS_WINS : ZERO_WINS;
    }
}
