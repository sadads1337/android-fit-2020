package ru.nsu.fit.lesson4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsTextFragment extends Fragment {
    private static final String ARG_PARAM = "param";

    private String mParam;

    public DetailsTextFragment() {
        // Empty constructor required.
    }

    public static DetailsTextFragment newInstance(@NonNull String param) {
        final DetailsTextFragment fragment = new DetailsTextFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mParam != null) {
            final TextView text = view.findViewById(R.id.detail_fragment_text);
            text.setText(mParam);
        }
    }
}