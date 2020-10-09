package ru.nsu.fit.lesson5;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CountFragment extends Fragment {
    private static final String COUNTER = "counter";

    private int mCount;
    private CountInterface listener;

    public interface CountInterface {
        void onCreated();
    }

    public CountFragment() {
    }

    public static CountFragment newInstance(int counter) {
        CountFragment fragment = new CountFragment();
        Bundle args = new Bundle();
        args.putInt(COUNTER, counter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CountInterface) {
            listener = (CountInterface) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCount = getArguments().getInt(COUNTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_count, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView showCount = view.findViewById(R.id.show_count);
        if (showCount != null)
            showCount.setText(String.valueOf(mCount));
        listener.onCreated();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener.onCreated();
    }
}