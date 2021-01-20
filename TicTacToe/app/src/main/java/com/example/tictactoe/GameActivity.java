package com.example.tictactoe;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoe.game.CellStatus;
import com.example.tictactoe.game.GameLoop;
import com.example.tictactoe.game.WhoGoesListener;
import com.example.tictactoe.game.winnerChecker.WinnerListener;
import com.example.tictactoe.playersManager.Player;
import com.example.tictactoe.playersManager.PlayersManager;
import com.example.tictactoe.resourceManager.ResourceManager;

public class GameActivity extends AppCompatActivity implements WinnerListener, WhoGoesListener {
    int h = 3, w = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initField();
    }

    private void initField() {
        TableLayout tableLayout = findViewById(R.id.gameField);
        GameLoop.getInstance().setWhoGoesListener(this);
        ImageButton[][] buttons = new ImageButton[h][w];
        GameLoop.getInstance().setWinnerListener(this);

        if(GameLoop.getInstance().isNeedsRestart()) {
            GameLoop.getInstance().restart(h, w);
            GameLoop.getInstance().setNeedsRestart(false);
        }

        for(int y = 0; y < GameLoop.getInstance().getHeight(); ++y){
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER);
            for(int x = 0; x < GameLoop.getInstance().getWidth(); ++x){
                ImageButton btn = new ImageButton(this);
                btn.setMinimumHeight(ResourceManager.getInstance().getBiggestDimension());
                btn.setMinimumWidth(ResourceManager.getInstance().getBiggestDimension());
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                btn.setLayoutParams(layoutParams);
                btn.setPadding(0,0,0,0);
                buttons[y][x] = btn;
                row.addView(btn);
            }
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            row.setLayoutParams(layoutParams);
            tableLayout.addView(row);
        }

        GameLoop.getInstance().connectField(buttons);
        GameLoop.getInstance().restore(h, w);

        findViewById(R.id.swapButton).setOnClickListener(v -> {
            PlayersManager.getInstance().swapPlayers();
            GameLoop.getInstance().restart(h, w);
        });
    }

    @Override
    public void notifyAboutWinner(CellStatus status) {

        String message = getString(R.string.winMessageEnd);
        if(status.equals(CellStatus.CLEAR))
            message = getString(R.string.drawMessage);
        else if(PlayersManager.getInstance().hasPlayers()){
            Player winner = status.equals(CellStatus.CROSS) ?
                    PlayersManager.getInstance().getFirstPlayer()
                    : PlayersManager.getInstance().getSecondPlayer();
            winner.score += 1;
            PlayersManager.getInstance().setPlayer(winner);
            message = winner.name + message;
        }
        else {
            message = (status.equals(CellStatus.CROSS) ? getString(R.string.crosses) : getString(R.string.circles)) + message;
        }
        AlertDialog dialog = new AlertDialog.Builder(GameActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog1, which) -> GameLoop.getInstance().restart(h, w))
                .setCancelable(false)
                .create();
        dialog.show();
    }

    @Override
    public void notifyWhoGoesChanged(CellStatus status) {
        ImageView playerTurnImageView = findViewById(R.id.playerTurnImageView);
        TextView playerTurnTextView = findViewById(R.id.playerTurnTextView);
        if(status.equals(CellStatus.CROSS)) {
            playerTurnImageView.setImageBitmap(ResourceManager.getInstance().getCross());
            if(PlayersManager.getInstance().hasPlayers())
                playerTurnTextView.setText(PlayersManager.getInstance().getFirstPlayer().name);
            else
                playerTurnTextView.setVisibility(View.GONE);
        }
        else{
            playerTurnImageView.setImageBitmap(ResourceManager.getInstance().getCircle());
            if(PlayersManager.getInstance().hasPlayers())
                playerTurnTextView.setText(PlayersManager.getInstance().getSecondPlayer().name);
            else
                playerTurnTextView.setVisibility(View.GONE);
        }
    }
}