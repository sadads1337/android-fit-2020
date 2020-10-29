package com.example.task1_tictactoe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements FieldFragment.TouchInterface {
    private static final String GAME_STATE = "gameState";
    private static final String ACTIVE_PLAYER = "player";
    private static final String IS_GAME_ACTIVE = "gameActive";
    private static final String IS_DRAW = "isDraw";

    private String mName1, mName2;
    private Bitmap mAvatar1, mAvatar2;

    boolean gameActive = true;
    boolean isDraw = false;
    int activePlayer = 0;    // Player representation: 0 - first player, 1 - second player
    int[] gameState = {
            2, 2, 2,
            2, 2, 2,
            2, 2, 2
    };  // 0 - X, 1 - O, 2 - None
    private int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView status = findViewById(R.id.status);

        if (savedInstanceState != null) {
            gameState = savedInstanceState.getIntArray(GAME_STATE);
            activePlayer = savedInstanceState.getInt(ACTIVE_PLAYER);
            gameActive = savedInstanceState.getBoolean(IS_GAME_ACTIVE);
            isDraw = savedInstanceState.getBoolean(IS_DRAW);
        }

        Intent intent = getIntent();
        mName1 = intent.getStringExtra("name1");
        mAvatar1 = intent.getParcelableExtra("avatar1");
        mName2 = intent.getStringExtra("name2");
        mAvatar2 = intent.getParcelableExtra("avatar2");

        if (gameActive)
            if (activePlayer == 0)
                status.setText(getString(R.string.move, mName1));
            else
                status.setText(getString(R.string.move, mName2));
        else if (isDraw)
            status.setText(getString(R.string.draw));
        else if (activePlayer == 0)
            status.setText(getString(R.string.winner, mName2));
        else
            status.setText(getString(R.string.winner, mName1));

        StatisticActivity.setPlayers(mName1, mAvatar1, mName2, mAvatar2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addFieldFragment();
        addPlayersFragment();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(GAME_STATE, gameState);
        outState.putInt(ACTIVE_PLAYER, activePlayer);
        outState.putBoolean(IS_GAME_ACTIVE, gameActive);
        outState.putBoolean(IS_DRAW, isDraw);
    }

    public void playerTap(View view) {
        setTapToUI(view);
        String roundCount = checkWinner();
        if (!gameActive) {
            StatisticActivity.addRound(roundCount);
        }
    }

    void setTapToUI(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        redrawUI(view);
        if (!gameActive) {
            gameReset(view);
            replaceFieldFragment();
            TextView status = findViewById(R.id.status);
            status.setText(getString(R.string.move, mName1));
        } else if (gameState[tappedImage] == 2) {
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);
            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);
                status.setText(getString(R.string.move, mName2));
            } else {
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);
                status.setText(getString(R.string.move, mName1));
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
    }

    String checkWinner() {
        String roundCount = "";
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                gameActive = false;
                TextView status = findViewById(R.id.status);
                if (gameState[winPosition[0]] == 0) {
                    roundCount = "1 : 0";
                    status.setText(getString(R.string.winner, mName1));
                } else {
                    roundCount = "0 : 1";
                    status.setText(getString(R.string.winner, mName2));
                }
            }
        }
        ArrayList<Integer> list = new ArrayList<>(gameState.length);
        if (gameActive) {
            for (int el : gameState)
                list.add(el);
            if (!list.contains(2)) {
                gameActive = false;
                isDraw = true;
                TextView status = findViewById(R.id.status);
                roundCount = "0 : 0";
                status.setText(getString(R.string.draw));
            }
        }
        return roundCount;
    }

    public Bundle getDataForFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("name1", mName1);
        bundle.putString("name2", mName2);
        bundle.putParcelable("avatar1", mAvatar1);
        bundle.putParcelable("avatar2", mAvatar2);
        return bundle;
    }

    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        Arrays.fill(gameState, 2);

        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);
    }

    public void redrawUI(View view) {
        if (view == null) return;
        int[] im = {R.drawable.x, R.drawable.o, 0};
        ((ImageView) findViewById(R.id.imageView0)).setImageResource(im[gameState[0]]);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(im[gameState[1]]);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(im[gameState[2]]);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(im[gameState[3]]);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(im[gameState[4]]);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(im[gameState[5]]);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(im[gameState[6]]);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(im[gameState[7]]);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(im[gameState[8]]);
    }

    private void addFieldFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.field_fragment, FieldFragment.newInstance(this))
                .commit();
    }

    void replaceFieldFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.field_fragment, FieldFragment.newInstance(this))
                .commit();
    }

    private void addPlayersFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.status_fragment, PlayersFragment.newInstance())
                .commit();
    }

    @Override
    public void onMakeMove(View view) {
        playerTap(view);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof FieldFragment) {
            FieldFragment fieldFragment = (FieldFragment) fragment;
            fieldFragment.setOnTouchInterface(this);
        }
    }
}

