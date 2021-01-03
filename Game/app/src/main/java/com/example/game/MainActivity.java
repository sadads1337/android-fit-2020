package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private Button buttonReset, buttonNewGame;
    private boolean player1Turn = true;
    private int roundCount, player1Points, player2Points;
    private String player1, player2;
    private TextView textViewPlayer1, textViewPlayer2;
    private ImageView player1_img, player2_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        player1_img = findViewById(R.id.imageView_palyer1_game);
        player2_img = findViewById(R.id.imageView_player2_game);

        Bundle extras = getIntent().getExtras();

        //player1_img.setImageBitmap((Bitmap) extras.getParcelableArray("image_1"));
        assert extras != null;
        player1_img.setImageBitmap((Bitmap) extras.getParcelable("image_1"));
        player2_img.setImageBitmap((Bitmap) extras.getParcelable("image_2"));
        player2_img.setImageResource(R.drawable.ic_baseline_insert_emoticon_24);

        Intent intent = getIntent();
        player1 = intent.getStringExtra(PlayersActivity.PLAYER1_TEXT);
        player2 = intent.getStringExtra(PlayersActivity.PLAYER2_TEXT);

        if (!player1.isEmpty())
            updatePlayersText();


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
        buttonNewGame = findViewById(R.id.button_newgame);
        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewGame();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCount++;
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Wins() {
        player1Points++;
        if (getResources().getConfiguration().locale.getLanguage().equals("en"))
            Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        if (getResources().getConfiguration().locale.getLanguage().equals("ru"))
            Toast.makeText(this, "Первый игрок победил!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        if (getResources().getConfiguration().locale.getLanguage().equals("en"))
            Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        if (getResources().getConfiguration().locale.getLanguage().equals("ru"))
            Toast.makeText(this, "Второй игрок победил!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        if (getResources().getConfiguration().locale.getLanguage().equals("en"))
            Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
        if (getResources().getConfiguration().locale.getLanguage().equals("ru"))
            Toast.makeText(this, "Ничья", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        if (player1.isEmpty()) {
            if (getResources().getConfiguration().locale.getLanguage().equals("en")){
                textViewPlayer1.setText("Player 1:" + player1Points);
                textViewPlayer2.setText("Player 2: " + player2Points);
            }
            if (getResources().getConfiguration().locale.getLanguage().equals("ru")) {
                textViewPlayer1.setText("Игрок 1" + player1Points);
                textViewPlayer2.setText("Игрок 2: " + player2Points);
            }

        }
        else{
            textViewPlayer1.setText(player1 + ": " + player1Points);
            textViewPlayer2.setText(player2 + ": " + player2Points);
        }
    }

    private void updatePlayersText(){
        textViewPlayer1.setText(player1 + ": 0");
        textViewPlayer2.setText(player2 + ": 0");
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    private void openNewGame(){
        Intent intent = new Intent(this, PlayersActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}