package com.hdaf.hw1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {
    private static Game game;
    String inf;

    public ScoreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance() {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        game = Game.getInstance();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(game!=null) {
            if(game.getName1() != null){
                inf = game.getName1();
            }
            else{
                inf = getActivity().getResources().getString(R.string.player1);
            }
            inf += " победил" + game.getWin1() + " раз\n";
            if(game.getName2() != null){
                inf += game.getName2();
            }
            else{
                inf += getActivity().getResources().getString(R.string.player2);
            }
            inf += " победил" + game.getWin2() + " раз\n";
            TextView t = getActivity().findViewById(R.id.score);
            t.setText(inf);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("inf", inf);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            inf = savedInstanceState.getString("inf");
            TextView t = getActivity().findViewById(R.id.score);
            t.setText(inf);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);
    }


}