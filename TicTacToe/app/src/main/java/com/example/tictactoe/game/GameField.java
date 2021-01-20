package com.example.tictactoe.game;

public class GameField {
    private int width = 3;
    private int height = 3;
    public Cell[][] field;
    public GameField(int height, int width){
        field = new Cell[height][width];
        for(int i = 0; i < height; ++i){
            for(int j = 0; j < width; ++j){
                field[i][j] = new Cell();
            }
        }
        this.width = width;
        this.height = height;
        clearAll();
    }

    public void clearAll(){
        for(int y = 0; y < height; ++y){
            for(int x = 0; x < width; ++x){
                field[y][x].setStatus(CellStatus.CLEAR);
            }
        }
    }
    public int getHeight(){
        return height;
    }
    public int getWidth() {
        return width;
    }
    public boolean setField(int x, int y, CellStatus status){
        if(y < field.length && x < field[y].length && field[y][x].getStatus().equals(CellStatus.CLEAR)) {
            field[y][x].setStatus(status);
            return true;
        }
        return false;
    }
    public CellStatus getStatus(int x, int y){
        return field[y][x].getStatus();
    }
}
