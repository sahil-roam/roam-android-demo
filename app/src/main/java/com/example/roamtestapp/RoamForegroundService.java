package com.example.roamtestapp;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class RoamForegroundService extends Service {

    private LocationReceiver mLocationReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        register();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegister();
    }

    private void register() {
        mLocationReceiver = new LocationReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.roam.android.RECEIVED");
        registerReceiver(mLocationReceiver, intentFilter);
    }

    private void unRegister() {
        if (mLocationReceiver != null) {
            unregisterReceiver(mLocationReceiver);
        }
    }

}
