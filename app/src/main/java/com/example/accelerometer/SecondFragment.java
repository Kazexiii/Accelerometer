package com.example.accelerometer;

import static android.hardware.SensorManager.DATA_X;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.media.metrics.Event;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.accelerometer.databinding.FragmentSecondBinding;

import java.util.EventListener;

public class SecondFragment extends Fragment implements SensorEventListener {

    private FragmentSecondBinding binding;
    private TextView textViewStepDetector;
    private SensorManager sensorManager;
    private Sensor mStepDetector;
    //private Sensor accelerometer;
    //private Sensor stepCounter;
    private boolean isDetectorSensorPresent;
    //Activity Context = getActivity();
    int stepDetect = 0;
    private float x;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) !=null){
            mStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            //Log.d("creation", sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR).toString());
            isDetectorSensorPresent = true;
        }else{
            textViewStepDetector.setText("Detector Sensor is not present");
            isDetectorSensorPresent = false;
        }

        //accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Chronometer simpleChronometer = (Chronometer) getView().findViewById(R.id.simpleChronometer);

        textViewStepDetector = (TextView) getView().findViewById(R.id.StepDetector);

       binding.buttonStart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               simpleChronometer.start(); // start a chronometer
           }
       });

        binding.buttonSecondPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume(){
        super.onResume();
        //sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_FASTEST);
        //sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null)
              //Log.d("creation", "onResume");
            sensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onPause(){
        super.onPause();
        //sensorManager.unregisterListener(this, accelerometer);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null)
            Log.d("creation", "onPause");
            sensorManager.unregisterListener(this, mStepDetector);

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //x = sensorEvent.values[DATA_X];
        //Log.d("creation", "hellooo");
        //if (sensorEvent.sensor == accelerometer){
        //Log.d("creation", Float.toString(x));}

//        if (sensorEvent.sensor == stepCounter) {
//            Log.d("creation", "stepcounr");
//            Log.d("creation", String.valueOf(stepCounter));
//        }

        if (sensorEvent.sensor == mStepDetector){
            stepDetect = (int) (stepDetect+sensorEvent.values[0]);
            textViewStepDetector.setText(String.valueOf(stepDetect));
            //Log.d("creation", String.valueOf(stepDetect));
            //Log.d("creation", "hello");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}