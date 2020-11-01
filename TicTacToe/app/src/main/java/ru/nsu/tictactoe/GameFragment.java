package ru.nsu.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

@SuppressLint("SetTextI18n")
@RequiresApi(api = Build.VERSION_CODES.N)
public class GameFragment extends Fragment implements View.OnClickListener {

    private MainActivity listener;
    private final Bundle savedState = new Bundle();
    private Game game;

    private static final int nCells = 3;
    private static final int GALLERY_REQUEST = 1;

    private TextView playerXScore, playerOScore, playerTurn;
    private EditText playerX, playerO;
    private Button[] cells = new Button[nCells * nCells];
    private Button reset;
    private ImageView userPicX, userPicO;
    private int userPicToChange;
    private boolean playerXActive = true;

    private final Runnable endGameCallback = () -> {
        listener.writeGameScore(new GameScore(playerX.getText().toString(),
                playerO.getText().toString(), game.getxScore(), game.getoScore()));
    };

    public GameFragment() {
        // required empty public constructor
    }

    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            listener = (MainActivity)context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerX = view.findViewById(R.id.playerX);
        playerO = view.findViewById(R.id.playerO);
        playerXScore = view.findViewById(R.id.playerXScore);
        playerOScore = view.findViewById(R.id.playerOScore);
        playerTurn = view.findViewById(R.id.playerTurn);
        reset = view.findViewById(R.id.reset);
        userPicX = view.findViewById(R.id.userPicX);
        userPicO = view.findViewById(R.id.userPicO);

        for (int i = 0; i < cells.length; i++) {
            cells[i] = view.findViewById(getResources().getIdentifier("cell" + i,
                        "id", "ru.nsu.tictactoe"));
            cells[i].setOnClickListener(this);
        }
        reset.setOnClickListener(r -> game.startNewGame());
        addEditTextListener(playerX);
        addEditTextListener(playerO);
        addImageViewListener(userPicX);
        addImageViewListener(userPicO);

        if (!savedState.containsKey("game")) {
            game = new Game();
            game.setEndGameCallback(endGameCallback);
        } else {
            game = (Game)savedState.getSerializable("game");
            game.setEndGameCallback(endGameCallback);
        }
        drawView();
    }

    @SuppressLint("NonConstantResourceId")
    private void addEditTextListener(EditText editText) {
        editText.setOnClickListener(view -> editText.setCursorVisible(true));
        editText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER) {
                InputMethodManager inputMethodManager = (InputMethodManager) listener
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                editText.setCursorVisible(false);
                editText.clearFocus();
                switch (v.getId()) {
                    case R.id.playerX:
                        playerX.setText(editText.getText().toString());
                        return true;
                    case R.id.playerO:
                        playerO.setText(editText.getText().toString());
                        return true;
                    default:
                        return false;
                }
            }
            return false;
        });
    }

    private void addImageViewListener (ImageView imageView) {
        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            userPicToChange = imageView.getId();
            startActivityForResult(intent, GALLERY_REQUEST);
        });
    }

    // TODO: crop image into a square
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri image = data.getData();
            switch (userPicToChange) {
                case (R.id.userPicX):
                    userPicX.setImageURI(image);
                    break;
                case (R.id.userPicO):
                    userPicO.setImageURI(image);
                    break;
                default:
                    break;
            }
        }
    }

    // saving fragment state while switching between fragments (activity's alive)
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        savedState.putSerializable("game", game);
    }

    // saving fragment state when activity gets destroyed
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", game);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            game = (Game)savedInstanceState.getSerializable("game");
            game.setEndGameCallback(endGameCallback);
            playerXActive = game.isPlayerXActive();
            drawView();
        }
    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().equals("")) {
            return;
        }
        String cellId = v.getResources().getResourceEntryName(v.getId());
        int cellNumber = Integer.parseInt(cellId.substring(cellId.length() - 1)
                .replaceAll("\\p{C}", ""));
        if (playerXActive) {
            game.makeAMove(cellNumber, 1);
        } else {
            game.makeAMove(cellNumber, 0);
        }
        if (game.isRoundOver()) {
            updatePlayersScore(game.isSomeoneWon(), game.getxScore(), game.getoScore(), game.getnRounds());
            game.startNewRound();
        }
        playerXActive = game.isPlayerXActive();
        drawView();
    }

    private void updatePlayersScore(boolean someoneWon, int xScore, int oScore, int nRounds) {
        if (someoneWon) {
            if (playerXActive) {
                playerXScore.setText(Integer.toString(xScore));
                Toast.makeText(getContext(), playerX.getText().toString() + " won round " + nRounds,
                        Toast.LENGTH_SHORT).show();
            } else {
                playerOScore.setText(Integer.toString(oScore));
                Toast.makeText(getContext(), playerO.getText().toString() + " won round " + nRounds,
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Draw!", Toast.LENGTH_SHORT).show();
        }
    }

    private void drawView() {
        playerXScore.setText(Integer.toString(game.getxScore()));
        playerOScore.setText(Integer.toString(game.getoScore()));
        if (playerXActive) {
            playerTurn.setText(playerX.getText().toString() + " turn");
        } else {
            playerTurn.setText(playerO.getText().toString() + " turn");
        }
        for (int i = 0; i < game.getGameState().length; i++) {
            switch (game.getGameState()[i]) {
                case -1:
                    cells[i].setText("");
                    break;
                case 0:
                    cells[i].setText("O");
                    break;
                case 1:
                    cells[i].setText("X");
                    break;
            }
        }
    }
}