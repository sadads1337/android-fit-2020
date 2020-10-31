package com.nsu.learning.myapplication.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nsu.learning.myapplication.CellState;
import com.nsu.learning.myapplication.Game;
import com.nsu.learning.myapplication.GameViewModel;
import com.nsu.learning.myapplication.GameViewModelFactory;
import com.nsu.learning.myapplication.GamerOrder;
import com.nsu.learning.myapplication.R;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.nsu.learning.myapplication.CellState.CROSS;
import static com.nsu.learning.myapplication.GamerOrder.FIRST;


public class GameFragment extends Fragment {

    private final List<Integer> buttonIds = Arrays.asList(R.id.button_1_1, R.id.button_1_2, R.id.button_1_3,
            R.id.button_2_1, R.id.button_2_2, R.id.button_2_3,
            R.id.button_3_1, R.id.button_3_2, R.id.button_3_3);
    private View view;
    private GameViewModel gameViewModel;

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);
        gameViewModel = new ViewModelProvider(getActivity(), new GameViewModelFactory())
                .get(GameViewModel.class);
        setGamersInfo();
        gameViewModel.getGame().observe(this, this::updateField);
        setButtons();
        return view;
    }

    private void updateField(Game game) {
        TextView score = view.findViewById(R.id.textView_score);
        score.setText(game.getCurScore());

        CellState[] cellStates = Objects.requireNonNull(game.getGameField().getValue()).getCells();
        for (int i = 0; i < cellStates.length; i++) {
            Button button = view.findViewById(buttonIds.get(i));
            button.setBackgroundResource(getPictureRes(cellStates[i]));
        }

        setCurrentGamer(game.getActivePlayer().getGamerOrder());
    }

    private void setCurrentGamer(GamerOrder gamerOrder) {
        TextView firstGamerName = view.findViewById(R.id.textView_player_1_name);
        TextView secondGamerName = view.findViewById(R.id.textView_player_2_name);
        Drawable activePlayerBackground = getResources().getDrawable(R.drawable.rounded, null);
        if (FIRST.equals(gamerOrder)) {
            firstGamerName.setBackground(activePlayerBackground);
            secondGamerName.setBackground(null);
        } else {
            secondGamerName.setBackground(activePlayerBackground);
            firstGamerName.setBackground(null);
        }
    }

    private int getPictureRes(CellState cellState) {
        switch (cellState) {
            case CROSS:
                return R.drawable.cross_field;
            case ZERO:
                return R.drawable.zero_field;
            default:
                return R.drawable.clear_field;
        }
    }

    private void setGamersInfo() {
        TextView gamerName = view.findViewById(R.id.textView_player_1_name);
        Game game = gameViewModel.getGame().getValue();
        gamerName.setText(game.getFirstGamer().getName());
        gamerName = view.findViewById(R.id.textView_player_2_name);
        gamerName.setText(game.getSecondGamer().getName());

        TextView score = view.findViewById(R.id.textView_score);
        score.setText(game.getCurScore());
    }

    private void setButtons() {
        for (int buttonId : buttonIds) {
            Button button = view.findViewById(buttonId);
            button.setOnClickListener(v -> {
                int buttonPosition = buttonIds.indexOf(v.getId());
                if (!gameViewModel.canCellBeSet(buttonPosition)) {
                    return;
                }
                CellState cellState = gameViewModel.getGame().getValue().getActivePlayer().getCellType();
                button.setBackgroundResource(CROSS.equals(cellState) ? R.drawable.cross_field : R.drawable.zero_field);
                gameViewModel.setCellValue(buttonPosition);
            });
        }
    }
}
