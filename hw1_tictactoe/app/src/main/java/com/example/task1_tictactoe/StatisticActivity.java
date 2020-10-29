package com.example.task1_tictactoe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task1_tictactoe.databinding.ActivityStatisticBinding;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {
    private static String mName1;
    private static String mName2;
    private static Bitmap mAvatar1, mAvatar2;
    private static List<String> mStatistic = new ArrayList<>();


    private ActivityStatisticBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    public static void setPlayers(String name1, Bitmap avatar1, String name2, Bitmap avatar2) {
        mName1 = name1;
        mName2 = name2;
        mAvatar1 = avatar1;
        mAvatar2 = avatar2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatisticBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        ImageView img1 = findViewById(R.id.imgPlayer1);
        if (mAvatar1 != null)
            img1.setImageBitmap(mAvatar1);
        else
            img1.setImageResource(R.mipmap.ic_launcher_round);
        ImageView img2 = findViewById(R.id.imgPlayer2);
        if (mAvatar2 != null)
            img2.setImageBitmap(mAvatar2);
        else
            img2.setImageResource(R.mipmap.ic_launcher_round);
        TextView txt1 = findViewById(R.id.txtPlayer1);
        txt1.setText(mName1);
        TextView txt2 = findViewById(R.id.txtPlayer2);
        txt2.setText(mName2);

        binding.appList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.appList.setLayoutManager(layoutManager);
        adapter = new AppListAdapter(
                mStatistic,
                this);
        binding.appList.setAdapter(adapter);
    }

    public static void addRound(String round) {
        mStatistic.add(round);
    }

    public static void clearStatistic() {
        mStatistic = new ArrayList<>();
    }

    public static boolean isSetStatistic() {
        return !mStatistic.isEmpty();
    }

}