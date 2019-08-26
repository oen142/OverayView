package com.example.overayview.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.overayview.R;
import com.example.overayview.receiver.BootReceiver;
import com.example.overayview.service.BackgroundService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class LockActivity extends AppCompatActivity implements View.OnClickListener {

    // TAG
    private final String TAG = "LockActivity";

    // Components
    private ImageButton coverImage;

    private ImageView pointImage;
    private ImageView missionImage;
    private ImageView linkToUrlImage;
    private ImageView lockerImage;
    private ImageView abeeLogo;
    private ImageView homeImage;

    private TextView dateTxt;
    private TextView timeTxt;
    private TextView pointTxt;
    private TextView pointPlusTxt;

    private LinearLayout dragLayout;
    private SeekBar seekBar;

    // random images
    private int index, res;
    private int[] randomResources = {
            R.drawable.abee_ad_low,
            R.drawable.abee_ad_2,
            R.drawable.abee_ad_3,
            R.drawable.abee_ad_4
    };

    // background running
    private Intent backgroundServiceIntent;
    private BackgroundService backgroundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_seek);

        initView();
        initData();
        ifScreenOn();
    }

    private void actionBackground() {
        backgroundService = new BackgroundService(getApplicationContext());
        backgroundServiceIntent = new Intent(getApplicationContext(), backgroundService.getClass());
        if (!BootReceiver.isServiceRunning(this, backgroundService.getClass())) {
            startService(backgroundServiceIntent);
        }
    }

    /**
     * @return @null
     * @desc initialize & allocate view
     */
    private void initView() {
        coverImage = findViewById(R.id.cover_image);
        pointImage = findViewById(R.id.point_image);
        missionImage = findViewById(R.id.mission_image);
        homeImage = findViewById(R.id.home_image);
        linkToUrlImage = findViewById(R.id.link_to_url_image);
        lockerImage = findViewById(R.id.locker_image);
        dateTxt = findViewById(R.id.date_txt);
        timeTxt = findViewById(R.id.time_txt);
        pointTxt = findViewById(R.id.point_txt);
        pointPlusTxt = findViewById(R.id.point_plus_txt);
        abeeLogo = findViewById(R.id.abee_logo);
        seekBar = findViewById(R.id.seekbar);

        coverImage.setOnClickListener(this);
        pointImage.setOnClickListener(this);
        missionImage.setOnClickListener(this);
        homeImage.setOnClickListener(this);
        linkToUrlImage.setOnClickListener(this);
        lockerImage.setOnClickListener(this);

        getTime();
    }

    /**
     * @return @null
     * @desc initialize & allocate data
     */
    @SuppressLint("SetTextI18n")
    private void initData() {
        index = (int) (Math.random() * 10);
        if (index > 3) {
            index = 0;
        }
        res = randomResources[index];
        Glide.with(this).load(res).thumbnail(0.1f).into(coverImage);
    }

    /**
     * @return date
     * @desc get date
     */
    @SuppressLint("SetTextI18n")
    private void getTime() {
        // date
        Date date = new Date(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

        // day
        Calendar calendar = Calendar.getInstance();
        String day = null;
        int dayInt = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayInt == 1) {
            day = "SUN";
        } else if (dayInt == 2) {
            day = "MON";
        } else if (dayInt == 3) {
            day = "TUE";
        } else if (dayInt == 4) {
            day = "WED";
        } else if (dayInt == 5) {
            day = "THU";
        } else if (dayInt == 6) {
            day = "FRI";
        } else if (dayInt == 7) {
            day = "SAT";
        }

        // time
        Date time = new Date(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm", Locale.KOREA);

        // setText
        dateTxt.setText(simpleDateFormat.format(date) + "." + day);
        timeTxt.setText(simpleDateFormatTime.format(time));
    }

    /**
     * @return Screen Of/Off Logcat
     * @desc initialize view
     */
    private void ifScreenOn() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);

        BroadcastReceiver screenOnOff = new BroadcastReceiver() {
            public static final String SCREEN_OFF = "android.intent.action.SCREEN_OFF";
            public static final String SCREEN_ON = "android.intent.action.SCREEN_ON";

            public void onReceive(Context context, Intent intent) {
                if (Objects.equals(intent.getAction(), SCREEN_OFF)) {
                    Log.e(TAG, "Screen Off");
                } else if (Objects.equals(intent.getAction(), SCREEN_ON)) {
                    Log.e(TAG, "Screen On");
                }
            }
        };
        registerReceiver(screenOnOff, intentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cover_image:
                String imageUrl = "http://www.naver.com";
                Intent imageIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                startActivity(imageIntent);
                break;
        }
    }

    /**
     * @param view
     * @desc recycle view when activity destroy
     */
    private void recycleView(View view) {
        if (view != null) {
            Drawable bg = view.getBackground();
            if (bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable) bg).getBitmap().recycle();
                view.setBackground(null);

                // setBackground, setBackgroundDrawable, setBackgroundResource
            }
        }
    }

    private final Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(LockActivity.this, "시간이 변경되었습니다.", Toast.LENGTH_SHORT).show();
            getTime();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.cover_image));
    }
}
