package ru.nsu.fit.extras.memory_leak;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static View view1;
    private static View view2;
    private static Object object1;
    private Object object2;
    private static Thread thread;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    static class MyTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //! Leak.
        view1 = new View(this);

        //! No leak cause of onDestroy.
        view2 = new View(this);

        class MyClass {
            // Holding ref to activity context.
        }

        //! Leak. Static field.
        object1 = new MyClass();
        //! No leak cause field is dynamic.
        object1 = new MyClass();

        //! Leak. Anonymous class.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    // Holding ref to activity context.
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        // No leak.
        new MyTask().execute();

        // Leak.
        new Thread() {
            @Override
            public void run() {
                if (!isInterrupted()) {
                    // Referencia al contexto de la actividad
                }
            }
        }.start();

        // No leak. Interrupted.
        thread = new Thread() {
            @Override
            public void run() {
                if (!isInterrupted()) {
                    // Referencia al contexto de la actividad
                }
            }
        };
        thread.start();
    }

    // Leaks. Must be unregistered.
    private void registrarSensor () {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view2 = null;
        thread.interrupt();
    }
}