package com.example.tic_tac_toe_game.Game;

public class Vertical implements CheckerInterface {
    private StateGame stateGame;

    public Vertical(StateGame stateGame) {
        this.stateGame = stateGame;
    }

    public Player checkWinner() {
        Field[][] field = stateGame.getField();
        Player currPlayer;
        Player lastPlayer = null;
        for (int i = 0, len = field.length; i < len; i++) {
            lastPlayer = null;
            int successCounter = 1;
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                currPlayer = field[j][i].getPlayer();
                if (currPlayer == lastPlayer && (currPlayer != null && lastPlayer != null)) {
                    successCounter++;
                    if (successCounter == len2) {
                        return currPlayer;
                    }
                }
                lastPlayer = currPlayer;
            }
        }
        return null;
    }
}
