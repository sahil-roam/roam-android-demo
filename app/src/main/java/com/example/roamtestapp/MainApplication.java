package com.example.roamtestapp;

import android.app.Application;

import com.roam.sdk.Roam;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Roam.initialize(this, "1ee24d8de18e679da596bc85852c0fc07966e62c5f9510ee5e2f708996c8d435");
    }
}
