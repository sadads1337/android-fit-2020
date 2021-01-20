package com.example.tictactoe.game;

public class Cell {
    private CellStatus status;
    private CellStatusChangeListener listener;

    public void setListener(CellStatusChangeListener listener) {
        this.listener = listener;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
        notifyListener();
    }

    public void notifyListener(){
        if(listener != null)
            listener.notifyChanged(this.status);
    }
}
