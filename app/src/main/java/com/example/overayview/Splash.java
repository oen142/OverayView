package com.example.overayview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {


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

//                loading_Page();

                Intent it = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(it);
                finish();
            }
        }, 1000);
    }

   /* public void loading_Page(){
        if (mProgress == null) {
            mProgress = new WebviewProgressDialog(getApplicationContext(),R.style.myDialog);
            mProgress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // 배경 투명처리
            //mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //mProgress.setTitle("Loading...");
            //mProgress.setMessage("불러오는중...");
            mProgress.setCancelable(false);
	            *//*mProgress.setButton("Cancel", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int whichButton) {
	                    mProgress.dismiss();
	                }
	            });*//*
            mProgress.show();
        }
    }*/
}