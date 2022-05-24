package com.example.roamtestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.roam.sdk.Roam;
import com.roam.sdk.RoamPublish;
import com.roam.sdk.RoamTrackingMode;
import com.roam.sdk.callback.RoamCallback;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button requestPermission, requestBackgroundPermission, createUser, startTracking, stopTracking, toggleListener;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission = findViewById(R.id.request_location);
        requestBackgroundPermission = findViewById(R.id.request_bg_location);
        createUser = findViewById(R.id.create_user);
        startTracking = findViewById(R.id.start_tracking);
        stopTracking = findViewById(R.id.stop_tracking);
        toggleListener = findViewById(R.id.toggle_listener);

        Roam.disableBatteryOptimization();
        Roam.offlineLocationTracking(true);

        requestPermission.setOnClickListener(this);
        requestBackgroundPermission.setOnClickListener(this);
        createUser.setOnClickListener(this);
        startTracking.setOnClickListener(this);
        stopTracking.setOnClickListener(this);
        toggleListener.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, RoamForegroundService.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.request_location:
                Roam.requestLocationPermission(this);
                break;

            case R.id.request_bg_location:
                Roam.requestBackgroundLocationPermission(this);
                break;

            case R.id.create_user:
                Roam.createUser("Test User", null, new RoamCallback() {
                    @Override
                    public void onSuccess(RoamUser roamUser) {
                        Log.e("TEST", new Gson().toJson(roamUser));
                        userId = roamUser.getUserId();
                    }

                    @Override
                    public void onFailure(RoamError roamError) {
                        Log.e("TEST", new Gson().toJson(roamError));
                    }
                });
                break;

            case R.id.start_tracking:
                RoamTrackingMode roamTrackingMode = new RoamTrackingMode.Builder(5)
                        .setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH)
                        .build();
                Roam.startTracking(roamTrackingMode);
                RoamPublish publish = new RoamPublish.Builder().build();
                Roam.publishAndSave(publish);
                Roam.setForegroundNotification(true,
                        "ROAM TEST APP",
                        "Roam is fetching locations",
                        R.mipmap.ic_launcher,
                        "com.example.roamtestapp.MainActivity",
                        "com.example.roamtestapp.RoamForegroundService");
                break;

            case R.id.stop_tracking:
                Roam.stopTracking();
                Roam.setForegroundNotification(false,
                        "ROAM TEST APP",
                        "Roam is fetching locations",
                        R.mipmap.ic_launcher,
                        "com.example.roamtestapp.MainActivity",
                        "com.example.roamtestapp.RoamForegroundService");
                break;

            case R.id.toggle_listener:
                Roam.toggleListener(true, true, new RoamCallback() {
                    @Override
                    public void onSuccess(RoamUser roamUser) {
                        Log.e("TEST", new Gson().toJson(roamUser));
                        Roam.subscribe(Roam.Subscribe.LOCATION, userId);
                    }

                    @Override
                    public void onFailure(RoamError roamError) {
                        Log.e("TEST", new Gson().toJson(roamError));
                    }
                });
                break;

        }
    }

}