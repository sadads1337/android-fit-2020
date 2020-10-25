package com.example.homework_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {
    public static final String FRAGMENT_NAME = "Fragment_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Fragment fragment = null;
        String fragmentName = (String) getIntent().getExtras().get(FRAGMENT_NAME);
        if (fragmentName.equals(getResources().getString(R.string.start))) {
            if(savedInstanceState == null) {
                TicTacToe.getInstance().startNewGame();
            }
            fragment = GameFragment.newInstance();
        } else if (fragmentName.equals(getResources().getString(R.string.score))){
          //  if(savedInstanceState == null) {
                fragment = ScoreFragment.newInstance();
            //}
        } else
            throw new IllegalArgumentException();

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    // .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putByte("key", (byte) 1);
        super.onSaveInstanceState(outState);
    }
}