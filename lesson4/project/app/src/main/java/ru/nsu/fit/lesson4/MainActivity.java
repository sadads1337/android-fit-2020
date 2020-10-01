package ru.nsu.fit.lesson4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnMenuButtonClickedListener, DetailsButtonFragment.OnContentButtonClickedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(String text) {
        addFragment(text);
    }

    @Override
    public void onAttach(String text) {
        addFragment(text);
    }

    @Override
    public void onContentClick(String text) {
        final DetailsTextFragment textFragment = DetailsTextFragment.newInstance(text);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, textFragment)
                .addToBackStack(null)
                .commit();
    }

    private void addFragment(String text) {
        final DetailsButtonFragment buttonFragment = DetailsButtonFragment.newInstance(text);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, buttonFragment)
                .commit();
    }
}