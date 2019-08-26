package com.example.overayview.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = RestartBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "RestarterBroadcastReceiver.onReceive");
        context.startService(new Intent(context, BackgroundService.class));
    }
}
