package com.example.task1_tictactoe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayersFragment extends Fragment {
    private String mName1, mName2;
    private Bitmap mAvatar1, mAvatar2;

    public PlayersFragment() {
    }

    public static Fragment newInstance() {
        return new PlayersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameActivity activity = (GameActivity) getActivity();

        Bundle bundle = activity.getDataForFragment();
        mName1 = bundle.getString("name1");
        mName2 = bundle.getString("name2");
        mAvatar1 = bundle.getParcelable("avatar1");
        mAvatar2 = bundle.getParcelable("avatar2");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView1 = view.findViewById(R.id.textView3);
        textView1.setText(mName1);
        TextView textView2 = view.findViewById(R.id.textView4);
        textView2.setText(mName2);
        ImageView imageView1 = view.findViewById(R.id.imageView10);
        ImageView imageView2 = view.findViewById(R.id.imageView12);
        if (mAvatar1 == null) {
            imageView1.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            imageView1.setImageBitmap(mAvatar1);
        }
        if (mAvatar2 == null) {
            imageView2.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            imageView2.setImageBitmap(mAvatar2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_players, container, false);
    }
}