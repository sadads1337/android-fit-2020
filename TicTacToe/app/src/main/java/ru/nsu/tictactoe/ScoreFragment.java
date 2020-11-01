package ru.nsu.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class ScoreFragment extends Fragment {

    private MainActivity listener;
    private Button back;
    private TableLayout table;
    private Bundle savedState = new Bundle();

    public interface ScoreInterface {
        void onBackClick();
    }

    public static ScoreFragment newInstance() {
        ScoreFragment fragment = new ScoreFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public ScoreFragment() {
        // required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            this.listener = (MainActivity)context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.score, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        back = view.findViewById(R.id.back);
        back.setOnClickListener(b -> {
            assert listener != null;
            listener.onBackClick();
        });
        table = view.findViewById(R.id.scoreTable);
        listener.updateScoreTable();
    }

//    @Override
//    public void onDestroyView(){
//        super.onDestroyView();
//        for(int i = 0; i < table.getChildCount(); i++) {
//            View view = table.getChildAt(i);
//            if (view instanceof TableRow) {
//
//            }
//        }
//    }

    private void addColumn(TableRow row, MainActivity context, float initWeight, String text) {
        TextView column = new TextView(context);
        column.setText(text);
        column.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(column, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, initWeight));
    }

    @SuppressLint("SetTextI18n")
    public void addRow(GameScore record, MainActivity context) {
        TableRow newRow = new TableRow(context);
        newRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        addColumn(newRow, context, 1f, record.getPlayerX());
        addColumn(newRow, context, 0.2f, Integer.toString(record.playerXScore));
        addColumn(newRow, context, 0.2f, Integer.toString(record.playerOScore));
        addColumn(newRow, context, 1f, record.getPlayerO());
        table.addView(newRow);
    }
}
