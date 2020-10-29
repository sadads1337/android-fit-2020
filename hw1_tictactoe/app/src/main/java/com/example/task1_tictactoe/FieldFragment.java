package com.example.task1_tictactoe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FieldFragment extends Fragment {
    TouchInterface mMainActivity;
    GameActivity mGameActivity;

    public interface TouchInterface {
        void onMakeMove(View view);
    }

    public FieldFragment(GameActivity gameActivity) {
        mGameActivity = gameActivity;
    }

    public FieldFragment() {
    }

    public static Fragment newInstance(GameActivity gameActivity) {
        return new FieldFragment(gameActivity);
    }

    public void setOnTouchInterface(TouchInterface activity) {
        mMainActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_field, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGameActivity != null)
            mGameActivity.redrawUI(view);
    }
}