package com.example.tictactoe.onClickListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.tictactoe.GameActivity;
import com.example.tictactoe.game.GameLoop;
import com.example.tictactoe.playersManager.PlayersManager;

public class PlayButtonOnClickListener implements View.OnClickListener {
    Activity currentActivity;
    public PlayButtonOnClickListener(Activity currentActivity){
        super();
        this.currentActivity = currentActivity;
    }
    @Override
    public void onClick(View v) {
        PlayersManager.getInstance().dropFirstSecondPlayers();
        GameLoop.getInstance().setNeedsRestart(true);
        Intent intent = new Intent(currentActivity, GameActivity.class);
        currentActivity.startActivity(intent);
    }
}
