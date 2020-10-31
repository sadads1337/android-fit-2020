package com.nsu.learning.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.nsu.learning.myapplication.Game;
import com.nsu.learning.myapplication.GameViewModel;
import com.nsu.learning.myapplication.GameViewModelFactory;
import com.nsu.learning.myapplication.R;
import com.nsu.learning.myapplication.database.Gamer;

import java.util.List;
import java.util.stream.Collectors;

import static android.widget.AdapterView.INVALID_POSITION;


public class ChooseGamersFragment extends AppFragment {
    private GameViewModel gameViewModel;
    private List<Gamer> gamers;
    private View view;

    public ChooseGamersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_choose_gamers, container, false);
        gameViewModel = new ViewModelProvider(getActivity(), new GameViewModelFactory())
                .get(GameViewModel.class);
        gameViewModel.getGamers().observe(this, this::updateSpinners);
        setButtons();
        return view;
    }

    private void setButtons() {
        setCreatePlayerButton(R.id.button_create_player_1);
        setCreatePlayerButton(R.id.button_create_player_2);
        setStartGameButton();
    }

    private void setStartGameButton() {
        Button startGameButton = view.findViewById(R.id.button_start_game_players);
        startGameButton.setOnClickListener(v -> {
            Gamer firstGamer = getSelectedGamer(R.id.spinner_player_1);
            Gamer secondGamer = getSelectedGamer(R.id.spinner_player_2);
            if (firstGamer == null || firstGamer == secondGamer) {
                showDialog();
                return;
            }
            saveSpinnersState();
            gameViewModel.getGame().getValue().startGame();
            startFragment(new GameFragment());
        });
    }

    private void showDialog() {
        Toast.makeText(getContext(), R.string.should_choose_players, Toast.LENGTH_LONG).show();
    }

    private void setCreatePlayerButton(int buttonId) {
        Button createPlayerButton = view.findViewById(buttonId);
        createPlayerButton.setOnClickListener(v -> {
            saveSpinnersState();
            startFragment(new CreateGamerFragment());
        });
    }

    private void saveSpinnersState() {
        Game game = gameViewModel.getGame().getValue();
        if (game == null || gamers == null) {
            return;
        }
        Gamer firstGamer = getSelectedGamer(R.id.spinner_player_1);
        if (firstGamer != null) {
            game.setFirstGamer(firstGamer);
        }
        Gamer secondGamer = getSelectedGamer(R.id.spinner_player_2);
        if (secondGamer != null) {
            game.setSecondGamer(secondGamer);
        }
    }

    private Gamer getSelectedGamer(int spinnerId) {
        Spinner playerSpinner = view.findViewById(spinnerId);
        int index = playerSpinner.getSelectedItemPosition();
        if (index == INVALID_POSITION || gamers == null || index >= gamers.size()) {
            return null;
        }
        return gamers.get(index);
    }

    private void updateSpinners(List<Gamer> gamers) {
        this.gamers = gamers;
        if (gamers == null) {
            return;
        }
        List<String> names = gamers.stream()
                .map(Gamer::getName).collect(Collectors.toList());
        updateSpinner(view, R.id.spinner_player_1, names);
        updateSpinner(view, R.id.spinner_player_2, names);
        checkForSelectedGamers();
    }

    private void checkForSelectedGamers() {
        Game game = gameViewModel.getGame().getValue();
        if (game == null) {
            return;
        }
        if (game.getFirstGamer() != null) {
            Spinner spinner = view.findViewById(R.id.spinner_player_1);
            spinner.setSelection(gamers.indexOf(game.getFirstGamer()));
        }
        if (game.getSecondGamer() != null) {
            Spinner spinner = view.findViewById(R.id.spinner_player_2);
            spinner.setSelection(gamers.indexOf(game.getSecondGamer()));
        }
    }

    private void updateSpinner(View view, int spinnerId, List<String> resources) {
        Spinner spinner = view.findViewById(spinnerId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                resources);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
