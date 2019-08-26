package com.example.overayview.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.overayview.R;
import com.example.overayview.dialog.WebviewProgressDialog;

public class SplashActivity extends AppCompatActivity {


    WebviewProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        startLoading();

    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent it = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(it);
                finish();
            }
        }, 1000);
    }


}