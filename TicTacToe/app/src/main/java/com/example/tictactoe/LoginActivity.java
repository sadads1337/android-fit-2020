package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tictactoe.adapters.LoginPlayersAdapter;
import com.example.tictactoe.playersManager.PlayersManager;

import static com.example.tictactoe.utils.Constants.PLAYER_NUMBER;

public class LoginActivity extends AppCompatActivity {
    private int playerNumber = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView labelTextView = findViewById(R.id.loginLabel);
        Intent intent = getIntent();
        playerNumber = intent.getIntExtra(PLAYER_NUMBER, 1);
        String playerNumberString = playerNumber == 1 ? getString(R.string.first) : getString(R.string.second);
        labelTextView.setText(String.format("%s%s%s", getString(R.string.LoginWithSpace), playerNumberString, getString(R.string.SpaceWithPlayer)));
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView loginPlayersView = findViewById(R.id.loginPlayersView);
        loginPlayersView.setLayoutManager(new LinearLayoutManager(this));
        LoginPlayersAdapter loginPlayersAdapter = new LoginPlayersAdapter(this, playerNumber == 1);
        loginPlayersView.setAdapter(loginPlayersAdapter);

        loginPlayersAdapter.setItems(PlayersManager.getInstance().getPlayers());
    }
}