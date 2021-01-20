package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.tictactoe.database.DatabaseManager;
import com.example.tictactoe.onClickListeners.LoginButtonOnClickListener;
import com.example.tictactoe.onClickListeners.PlayButtonOnClickListener;
import com.example.tictactoe.onClickListeners.PlayersButtonOnClickListener;
import com.example.tictactoe.resourceManager.FilesManager;
import com.example.tictactoe.resourceManager.ResourceManager;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ResourceManager.getInstance().initResources(this);
        FilesManager.getInstance().init(this);
        DatabaseManager.getInstance().initDatabase(this);

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new PlayButtonOnClickListener(this));

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new LoginButtonOnClickListener(this));

        Button playersButton = findViewById(R.id.playersButton);
        playersButton.setOnClickListener(new PlayersButtonOnClickListener(this));

    }
}