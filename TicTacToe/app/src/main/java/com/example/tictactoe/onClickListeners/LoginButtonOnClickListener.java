package com.example.tictactoe.onClickListeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.tictactoe.GameActivity;
import com.example.tictactoe.LoginActivity;
import com.example.tictactoe.MainMenuActivity;

public class LoginButtonOnClickListener implements View.OnClickListener {
    Activity currentActivity;
    public LoginButtonOnClickListener(Activity currentActivity) {
        super();
        this.currentActivity = currentActivity;
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(currentActivity, LoginActivity.class);
        currentActivity.startActivity(intent);
    }
}
