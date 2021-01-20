package com.example.tictactoe.onClickListeners;

import android.view.View;
import android.widget.ImageButton;

import com.example.tictactoe.game.CellStatus;
import com.example.tictactoe.game.CellStatusChangeListener;
import com.example.tictactoe.game.GameLoop;
import com.example.tictactoe.resourceManager.ResourceManager;

public class GameButtonOnClickListener implements View.OnClickListener, CellStatusChangeListener {
    private int x, y;
    private View v;
    public GameButtonOnClickListener(int x, int y, View v){
        super();
        this.x = x;
        this.y = y;
        this.v = v;
    }
    @Override
    public void onClick(View v) {
        GameLoop.getInstance().move(x, y);
    }

    @Override
    public void notifyChanged(CellStatus status) {
        ((ImageButton)v).setImageBitmap(ResourceManager.getInstance().getGameBitmap(status));
    }
}
