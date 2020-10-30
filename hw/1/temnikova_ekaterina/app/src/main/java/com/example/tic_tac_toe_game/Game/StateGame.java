package com.example.tic_tac_toe_game.Game;

public class StateGame {
    private Player[] players;
    private Field[][] field;
    private boolean started;
    private Player playerNow;
    private int filled; // колличество заполненных ячеек
    private int countAll; // Всего ячеек
    private CheckerInterface[] winnerCheckers; // объекты, проверяющие был ли выйгрыш

    public StateGame() {
        winnerCheckers = new CheckerInterface[4];
        winnerCheckers[0] = new Horizontal(this); //проверка выйгрыша по горизонтали
        winnerCheckers[1] = new Vertical(this); //проверка выйгрыша по вертикали
        winnerCheckers[2] = new DiagonalLeft(this); //проверка выйгрыша левая диагональ
        winnerCheckers[3] = new DiagonalRight(this); //проверка выйгрыша правая диагональ

        field = new Field[3][3];
        countAll = 0;
        // заполнение поля
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Field();
                countAll++;
            }
        }
        players = new Player[2];
        started = false;
        playerNow = null;
        filled = 0;
    }

    public Player checkWinner() {
        for (CheckerInterface winChecker : winnerCheckers) {
            Player winner = winChecker.checkWinner();
            if (winner != null) {
                return winner;
            }
        }
        return null;
    }

    public void start() {
        resetPlayers();
        started = true;
    }

    private void resetPlayers() {
        players[0] = new Player("X");
        players[1] = new Player("O");
        setCurrentActivePlayer(players[0]);
    }

    private void setCurrentActivePlayer(Player player) {
        playerNow = player;
    }

    public Field[][] getField() {
        return field;
    }

    public boolean makeTurn(int x, int y) {
        if (field[x][y].isFilled()) {
            return false;
        }
        field[x][y].fill(getCurrentActivePlayer());
        filled++;
        switchPlayers();
        return true;
    }

    private void switchPlayers() {
        playerNow = (playerNow == players[0]) ? players[1] : players[0];
    }

    public Player getCurrentActivePlayer() {
        return playerNow;
    }

    public boolean isFieldFilled() {
        return countAll == filled;
    }

    public void reset() {
        resetField();
        resetPlayers();
    }

    private void resetField() {
        for (int i = 0, l = field.length; i < l; i++) {
            for (int j = 0, l2 = field[i].length; j < l2; j++) {
                field[i][j].fill(null);
            }
        }
        filled = 0;
    }
}
