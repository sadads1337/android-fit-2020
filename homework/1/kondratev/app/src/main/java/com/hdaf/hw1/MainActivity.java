package com.hdaf.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MenuFragment.MenuInterface {
    boolean isTablet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.game_container) != null)
            isTablet = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, MenuFragment.newInstance())
                .commit();

    }

    @Override
    public void onMenuClick(String str) {
        if (isTablet) {
            if (str.equals(getResources().getString(R.string.start_new_game))) {
                Fragment old = getSupportFragmentManager().findFragmentByTag("game");
                if (old != null){
                    getSupportFragmentManager().beginTransaction().remove(old).commit();
                }
                Game.getInstance().clearState();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.game_container, GameFragment.newInstance(), "game")
                        .addToBackStack("game")
                        .commit();
            }
            else if (str.equals(getResources().getString(R.string.score))){
                Fragment old = getSupportFragmentManager().findFragmentByTag("score");
                if (old != null){
                    getSupportFragmentManager().beginTransaction().remove(old).commit();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.game_container, ScoreFragment.newInstance(),"score")
                        .addToBackStack("score")
                        .commit();
            }
        } else {
            if (str.equals(getResources().getString(R.string.start_new_game))) {
                Game.getInstance().clearState();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, GameFragment.newInstance())
                        .addToBackStack("game")
                        .commit();
            }
            else if (str.equals(getResources().getString(R.string.score))){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, ScoreFragment.newInstance())
                        .addToBackStack("score")
                        .commit();
            }
        }
    }



}