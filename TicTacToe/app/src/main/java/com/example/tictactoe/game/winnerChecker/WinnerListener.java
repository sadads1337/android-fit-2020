package com.example.tictactoe.game.winnerChecker;

import com.example.tictactoe.game.CellStatus;

public interface WinnerListener {
    void notifyAboutWinner(CellStatus status);
}
