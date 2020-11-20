package ru.nsu.fit.lesson10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d("MyBroadcastReceiver", action != null ? action : "EMPTY_ACTION");

            final TextView textView = MainActivity.this.findViewById(R.id.count_text);
            final int counter = Integer.parseInt(textView.getText().toString());
            textView.setText(String.valueOf(counter + 1));

        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d("SimpleBroadcastReceiver", action != null ? action : "EMPTY_ACTION");
        }
    };
    private BroadcastReceiver counterBroadcastReceiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(broadcastReceiver, new IntentFilter("android.intent.action.AIRPLANE_MODE"));
        registerReceiver(counterBroadcastReceiver, new IntentFilter("ru.nsu.fit.lesson10.INCREASE_COUNTER"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(counterBroadcastReceiver);
    }

    public void onCount(View view) {
        MyJobService.enqueueWork(this, new Intent().setAction("ru.nsu.fit.lesson10.INCREASE_COUNTER"));
    }
}