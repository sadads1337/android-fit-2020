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

public class MenuFragment extends Fragment {

    private OnMenuButtonClickedListener listener;

    public interface OnMenuButtonClickedListener {
        void onClick(String text);
        void onAttach(String text);
    }

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuButtonClickedListener) {
            listener = (OnMenuButtonClickedListener) context;
            listener.onAttach(getResources().getString(R.string.button_1));
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement " + OnMenuButtonClickedListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button button1 = view.findViewById(R.id.menu_button_1);
        addListener(button1);

        final Button button2 = view.findViewById(R.id.menu_button_2);
        addListener(button2);

        final Button button3 = view.findViewById(R.id.menu_button_3);
        addListener(button3);
    }

    private void addListener(@NonNull final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert listener != null;
                listener.onClick(button.getText().toString());
            }
        });
    }
}