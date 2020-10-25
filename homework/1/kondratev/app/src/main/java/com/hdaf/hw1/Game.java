package com.hdaf.hw1;

import android.graphics.Bitmap;

public class Game {
    private static volatile Game game;
    private Game(){}
    public static Game getInstance() {
        Game localInstance = game;
        if (localInstance == null) {
            synchronized (Game.class) {
                localInstance = game;
                if (localInstance == null) {
                    game = localInstance = new Game();
                }
            }
        }
        return localInstance;
    }

    //для создания "чистой" новой игры
    public void clearState(){
        field = null;
        bm1 = null;
        bm2 = null;
        roundCount = 0;
        player1Turn = true;
        gameEnded = false;
        win1 = 0;
        win2 = 0;
        name1 = null;
        name2 = null;
    }

    private String[][] field;
    public void setField(String[][] f){
        field = f;
    }

    public String[][] getField(){
        return field;
    }
    // Картинки
    public Bitmap bm1;
    public Bitmap bm2;

    //Число ходов (нужно для определения состояния "ничья")
    private int roundCount = 0;
    //Чей ход?
    private boolean player1Turn = true;
    //Игра завершилась - нажатия не меняют поле
    private boolean gameEnded;

    //Счетчик побед
    private int win1 = 0;
    private int win2 = 0;

    //Имена игроков, которые ввел пользователь
    private String name1 ;
    private String name2 ;


    public String makeTurn(){
        roundCount++;
        if(player1Turn){
            return "X";
        }
        else{
            return "O";
        }
    }

    public Bitmap getBm1() {
        return bm1;
    }

    public void setBm1(Bitmap bm1) {
        this.bm1 = bm1;
    }

    public Bitmap getBm2() {
        return bm2;
    }

    public void setBm2(Bitmap bm2) {
        this.bm2 = bm2;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public int getWin1() {
        return win1;
    }

    public void setWin1(int win1) {
        this.win1 = win1;
    }

    public int getWin2() {
        return win2;
    }

    public void setWin2(int win2) {
        this.win2 = win2;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public boolean checkForWin(String [][] field) {

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
}
