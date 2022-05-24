package com.example.roamtestapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamLocation;
import com.roam.sdk.service.RoamReceiver;

import java.util.List;

public class LocationReceiver extends RoamReceiver {

    @Override
    public void onLocationUpdated(Context context, List<RoamLocation> list) {
        super.onLocationUpdated(context, list);
        Toast.makeText(context.getApplicationContext(), new Gson().toJson(list), Toast.LENGTH_SHORT).show();
        Log.e("TEST", new Gson().toJson(list));
    }

    @Override
    public void onError(Context context, RoamError roamError) {
        super.onError(context, roamError);
        Log.e("TEST", new Gson().toJson(roamError));
    }
}
