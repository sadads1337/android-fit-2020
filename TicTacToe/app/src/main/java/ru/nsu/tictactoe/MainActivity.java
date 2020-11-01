package ru.nsu.tictactoe;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CopyOnWriteArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements ScoreFragment.ScoreInterface {

    private GameFragment gameFragment;
    private ScoreFragment scoreFragment;
    // thread safe array list (to avoid ConcurrentModificationException)
    private CopyOnWriteArrayList<GameScore> gameScores = new CopyOnWriteArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            int gameScoresSize = savedInstanceState.getInt("gameScoresSize");
            for (int i = 0; i < gameScoresSize; i++) {
                gameScores.add((GameScore)savedInstanceState.getSerializable("score" + i));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, GameFragment.newInstance())
                .addToBackStack("game")
                .commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int gameScoresSize = gameScores.size();
        outState.putInt("gameScoresSize", gameScoresSize);
        for (int i = 0; i < gameScoresSize; i++) {
            outState.putSerializable("score" + i, gameScores.get(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint({"CommitTransaction", "NonConstantResourceId", "ResourceType"})
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.score:
                scoreFragment = ScoreFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, scoreFragment, "score")
                        .addToBackStack("score")
                        .commit();
                return true;
            case R.id.newGame:
                gameFragment = GameFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, gameFragment, "game")
                        .addToBackStack("game")
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackClick() {
        getSupportFragmentManager()
                .popBackStackImmediate("game", 0);
    }

    public void updateScoreTable() {
        for (GameScore score: gameScores) {
            scoreFragment.addRow(score, this);
            //gameScores.remove(score);
        }
    }

    public void writeGameScore(GameScore gameScore) {
        gameScores.add(gameScore);
    }
}
