package com.nsu.learning.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.learning.myapplication.R;


public class MenuFragment extends AppFragment {
    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        setStartFragmentButton(view.findViewById(R.id.button_start_game), new ChooseGamersFragment());
        setStartFragmentButton(view.findViewById(R.id.button_score), new ScoreFragment());
        return view;
    }

}
