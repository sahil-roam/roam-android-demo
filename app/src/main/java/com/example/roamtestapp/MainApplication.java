package com.example.roamtestapp;

import android.app.Application;

import com.roam.sdk.Roam;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Roam.initialize(this, "f29a28e483f1b6a61ae8c6d2a32ae57b9fa438877e20c168916499900f6c6070");
    }
}
