package com.example.overayview;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String alert_abee_ad_st = "";


    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);


        SharedPreferences sf = getSharedPreferences("abee_ad",MODE_PRIVATE);
        alert_abee_ad_st = sf.getString("abee_ad","");

        alert_abee_ad(alert_abee_ad_st);

    }



    public void  alert_abee_ad(String alert_abee_ad_st){


        if(alert_abee_ad_st == "yes") {
            startService(new Intent(MainActivity.this, MyService.class));
        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle("아비 광고설정");
            alertDialogBuilder
                    .setMessage("아비 광고를 보시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("광고설정",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Toast.makeText(MainActivity.this , "아비광고 설정합니다." , Toast.LENGTH_LONG).show();
                                    checkPermission();
              //                      startService(new Intent(getApplicationContext(), MyService.class));
             /*                       SharedPreferences sharedPreferences = getSharedPreferences("abee_ad",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("abee_ad" , "yes");
                                    editor.commit();*/
                                }
                            })
                    .setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    Toast.makeText(MainActivity.this , "아비광고 설정을 하지 않습니다." , Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

    }
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(this)) {              // 체크
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            } else {
                startService(new Intent(MainActivity.this, MyService.class));
            }
        } else {
            startService(new Intent(MainActivity.this, MyService.class));
        }
    }
    public void turnoff_abee_ad(View v){


        stopService(new Intent(MainActivity.this, MyService.class));
    }


}