package com.example.tic_tac_toe_game.Game;

public class DiagonalLeft implements CheckerInterface {
    private StateGame stateGame;

    public DiagonalLeft(StateGame stateGame) {
        this.stateGame = stateGame;
    }

    public Player checkWinner() {
        Field[][] field = stateGame.getField();
        Player currPlayer;
        Player lastPlayer = null;
        int successCounter = 1;
        for (int i = 0, len = field.length; i < len; i++) {
            currPlayer = field[i][i].getPlayer();
            if (currPlayer != null) {
                if (lastPlayer == currPlayer) {
                    successCounter++;
                    if (successCounter == len) {
                        return currPlayer;
                    }
                }
            }
            lastPlayer = currPlayer;
        }
        return null;
    }
}
