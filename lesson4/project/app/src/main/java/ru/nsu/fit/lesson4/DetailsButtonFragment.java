package ru.nsu.fit.lesson4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DetailsButtonFragment extends Fragment {

    private static final String ARG_PARAM = "param";

    private String mParam;

    private OnContentButtonClickedListener listener;

    public interface OnContentButtonClickedListener {
        void onContentClick(String text);
    }

    public DetailsButtonFragment() {
        // Empty constructor required.
    }

    public static DetailsButtonFragment newInstance(@NonNull String param) {
        final DetailsButtonFragment fragment = new DetailsButtonFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnContentButtonClickedListener) {
            listener = (OnContentButtonClickedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement " + OnContentButtonClickedListener.class.getSimpleName());
        }
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
        // We could create view here, but the only true way is to create it in onViewCreated.
        return inflater.inflate(R.layout.fragment_details_button, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button button = view.findViewById(R.id.detail_fragment_button);

        if (mParam != null) {
            button.setText(mParam);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert listener != null;
                listener.onContentClick(button.getText().toString());
            }
        });
    }
}