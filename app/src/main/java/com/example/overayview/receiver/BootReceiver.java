package com.example.overayview.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.example.overayview.service.BackgroundService;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d(TAG, "action = " + action);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent serviceLauncher = new Intent(context, BackgroundService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(serviceLauncher);
                    } else {
                        context.startService(serviceLauncher);
                    }
                }
            }, 3000);
        }
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i(TAG, "ServiceRunning? = " + true);
                return true;
            }
        }
        Log.i(TAG, "ServiceRunning? = " + false);
        return false;
    }
}
