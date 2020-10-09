package ru.nsu.fit.lesson5;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CountFragment.CountInterface {

    private static final String COUNTER = "counter";
    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addFragment();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER, mCount);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        // Called only when R.attr.persistableMode is set to as persistAcrossReboots.
        outState.putInt(COUNTER, mCount);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCount = savedInstanceState.getInt(COUNTER, 0);

        final TextView showCount = findViewById(R.id.show_count);
        if (showCount != null)
            showCount.setText(String.valueOf(mCount));
    }

    public void countUp(View view) {
        mCount++;
        addFragment();
    }

    public void showToast(View view) {
        final Toast toast = new Toast(this);
        toast.setText(String.format(Locale.getDefault(), "Counter: %d", mCount));
        toast.show();
    }

    public void popToInitValue(View view) {
        final String initValue = getResources().getString(R.string.count_initial_value);
        getSupportFragmentManager().popBackStack(initValue, 0);
        mCount = Integer.parseInt(initValue);
    }

    private void addFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.count_fragment, CountFragment.newInstance(mCount))
                .addToBackStack(String.valueOf(mCount))
                .commit();

        updateBackStackValue();
    }

    private void updateBackStackValue() {
        final TextView showCount = findViewById(R.id.last_stack_tag);
        final int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            final int index = backStackEntryCount - 1;
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
            if (showCount != null)
                showCount.setText(backEntry.getName());
        }
        else
        {
            if (showCount != null)
                showCount.setText(getResources().getString(R.string.count_initial_value));
        }
    }

    @Override
    public void onCreated() {
        updateBackStackValue();
    }
}