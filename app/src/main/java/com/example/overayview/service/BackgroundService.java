package com.example.overayview.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class BackgroundService extends Service {

    private static final String TAG = BackgroundService.class.getSimpleName();

    private Context context = null;
    public int counter = 0;
    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public BackgroundService() {

    }

    public BackgroundService(Context applicationContext) {
        super();
        context = applicationContext;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "BackgroundSeervice.onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "BackgroundService.onStartCommand");
        startTimer();
        return START_STICKY;
    }

    private void initializeTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "Background Timer Task = " + (counter++));
            }
        };
    }

    private void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 1000, 1000);
    }

    private void stopTimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Start service automatically when user ends service
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "BackgroundService.onTaskRemoved");

        // create onTaskIntent when start service again.
        Intent onTaskIntent = new Intent(getApplicationContext(), BackgroundService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, onTaskIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "BackgroundService.onDestroy");
        Intent broadcastIntent = new Intent("com.example.overayview.RestartService");
        sendBroadcast(broadcastIntent);
        stopTimertask();
    }
}
