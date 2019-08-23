package com.example.overayview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LockActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton coverImage;
    private ImageView pointImage, missionImage, homeImage, linkToUrlImage, lockerImage, abeeLogo;
    private TextView dateTxt, timeTxt, pointTxt, pointPlusTxt;
    private LinearLayout dragLayout;
    private SeekBar seekBar;

    private int index, res;
    private int[] randomResources = {
            R.drawable.abee_ad_low,
            R.drawable.abee_ad_2,
            R.drawable.abee_ad_3,
            R.drawable.abee_ad_4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_seek);

        initView();
        initData();
//        ifScreenOn();
    }

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
    }

    private void initData() {
        index = (int) Math.random() * 10;
        res = randomResources[0];
        Glide.with(this).load(res).thumbnail(0.1f).into(coverImage);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        dateTxt.setText(simpleDateFormat.format(date));
    }

    private void ifScreenOn() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);

        BroadcastReceiver screenOnOff = new BroadcastReceiver() {
            public static final String SCREEN_OFF = "android.intent.action.SCREEN_OFF";
            public static final String SCREEN_ON = "android.intent.action.SCREEN_ON";

            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(SCREEN_OFF)) {
                    Log.e("MainActivity", "Screen Off");
                } else if (intent.getAction().equals(SCREEN_ON)) {
                    Log.e("MainActivity", "Screen On");
                }
            }
        };
        registerReceiver(screenOnOff, intentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cover_image:
                break;
        }
    }

    private void recycleView(View view) {
        if (view != null) {
            Drawable bg = view.getBackground();
            if (bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable) bg).getBitmap().recycle();
                view.setBackgroundDrawable(null);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycleView(findViewById(R.id.cover_image));
    }
}
