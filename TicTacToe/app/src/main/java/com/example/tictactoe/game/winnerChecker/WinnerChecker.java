package com.example.tictactoe.game.winnerChecker;

import com.example.tictactoe.game.CellStatus;
import com.example.tictactoe.game.GameField;

public class WinnerChecker {
    int checkSum = 0;
    public boolean checkToRight(GameField field, int x, int y, CellStatus status){
        if(x != y)
            return false;
        for(int i = 0; i < checkSum; ++i){
            if(!field.getStatus(i, i).equals(status)){
                return false;
            }
        }
        return true;
    }
    public boolean checkToLeft(GameField field, int x, int y, CellStatus status){
        if(x != checkSum - y - 1)
            return false;
        for(int i = 0; i < checkSum; ++i){
            if(!field.getStatus(i, checkSum - i - 1).equals(status)){
                return false;
            }
        }
        return true;
    }
    public boolean checkHorizontal(GameField field, int x, int y, CellStatus status){
        for(int i = 0; i < checkSum; ++i){
            if(!field.getStatus(i, y).equals(status)){
                return false;
            }
        }
        return true;
    }
    public boolean checkVertical(GameField field, int x, int y, CellStatus status){
        for(int i = 0; i < checkSum; ++i){
            if(!field.getStatus(x, i).equals(status)){
                return false;
            }
        }
        return true;
    }
    public boolean checkWinner(GameField field, int x, int y, CellStatus status){
        checkSum = Math.min(field.getWidth(), field.getHeight());
        return checkHorizontal(field, x, y, status) ||
                checkVertical(field, x, y, status) ||
                checkToLeft(field, x, y, status) ||
                checkToRight(field, x, y, status);
    }

    public boolean checkDraw(GameField field) {
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                if(field.getStatus(x, y).equals(CellStatus.CLEAR))
                    return false;
            }
        }
        return true;
    }
}
