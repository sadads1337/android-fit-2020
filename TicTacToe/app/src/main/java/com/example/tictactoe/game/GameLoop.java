package com.example.tictactoe.game;

import android.widget.ImageButton;

import com.example.tictactoe.game.winnerChecker.WinnerChecker;
import com.example.tictactoe.game.winnerChecker.WinnerListener;
import com.example.tictactoe.onClickListeners.GameButtonOnClickListener;

public class GameLoop {
    private static volatile GameLoop instance;
    private GameField field;
    private WinnerChecker winnerChecker;
    private CellStatus whoGoes = CellStatus.CROSS;
    private WinnerListener winnerListener;
    private WhoGoesListener whoGoesListener;

    private GameLoop(){ winnerChecker = new WinnerChecker(); }

    private boolean needsRestart = true;

    public static GameLoop getInstance(){
        if(instance == null){
            synchronized (GameLoop.class){
                if(instance == null){
                    instance = new GameLoop();
                }
            }
        }
        return instance;
    }
    public void restart(int height, int width){
        this.whoGoes = CellStatus.CROSS;
        if(this.field == null)
            this.field = new GameField(height, width);
        else
            this.field.clearAll();
        whoGoesListener.notifyWhoGoesChanged(this.whoGoes);

    }
    public void restore(int height, int width){
        whoGoesListener.notifyWhoGoesChanged(this.whoGoes);
        for(int y = 0; y < this.field.getHeight(); ++y){
            for(int x = 0; x < this.field.getWidth(); ++x){
                field.field[y][x].notifyListener();
            }
        }
    }
    public int getHeight(){
        return field.getHeight();
    }
    public int getWidth(){
        return field.getWidth();
    }
    public void connectField(ImageButton[][] buttons){
        for(int y = 0; y < this.field.getHeight(); ++y){
            for(int x = 0; x < this.field.getWidth(); ++x){
                GameButtonOnClickListener listener = new GameButtonOnClickListener(x, y, buttons[y][x]);
                buttons[y][x].setOnClickListener(listener);
                field.field[y][x].setListener(listener);
            }
        }
    }
    public void move(int x, int y){
        if(field.setField(x, y, whoGoes)) {
            if(winnerChecker.checkWinner(field, x, y, whoGoes) && winnerListener != null)
                winnerListener.notifyAboutWinner(whoGoes);
            else if(winnerChecker.checkDraw(field) && winnerListener != null)
                winnerListener.notifyAboutWinner(CellStatus.CLEAR);
            if (whoGoes == CellStatus.CROSS)
                whoGoes = CellStatus.CIRCLE;
            else
                whoGoes = CellStatus.CROSS;
            whoGoesListener.notifyWhoGoesChanged(this.whoGoes);
        }
    }
    public CellStatus getWhoGoes(){
        return whoGoes;
    }
    public void setWinnerListener(WinnerListener winnerListener){
        this.winnerListener = winnerListener;
    }
    public void setWhoGoesListener(WhoGoesListener whoGoesListener){
        this.whoGoesListener = whoGoesListener;
    }

    public boolean isNeedsRestart() {
        return needsRestart;
    }

    public void setNeedsRestart(boolean needsRestart) {
        this.needsRestart = needsRestart;
    }
}
