package com.example.tictactoe.onClickListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.tictactoe.GameActivity;
import com.example.tictactoe.MainMenuActivity;
import com.example.tictactoe.PlayersActivity;

public class PlayersButtonOnClickListener implements View.OnClickListener {
    Activity currentActivity;
    public PlayersButtonOnClickListener(Activity currentActivity) {
        super();
        this.currentActivity = currentActivity;
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(currentActivity, PlayersActivity.class);
        currentActivity.startActivity(intent);
    }
}
