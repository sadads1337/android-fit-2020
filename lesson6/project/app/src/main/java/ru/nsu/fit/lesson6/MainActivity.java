package ru.nsu.fit.lesson6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import ru.nsu.fit.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        binding.appList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        binding.appList.setLayoutManager(layoutManager);

        adapter = new AppListAdapter(getPackageManager()
                .getInstalledPackages(PackageManager.GET_META_DATA), this);
        binding.appList.setAdapter(adapter);
    }
}