package com.example.overayview.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.overayview.R;
import com.example.overayview.Webview;


public class MyService extends Service {

    WindowManager wm;
    View mView;

    private float mTouchX, mTouchY;
    private int mViewX, mViewY;
    WindowManager.LayoutParams params;
    int x ,y ;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN  | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);


        mView = inflate.inflate(R.layout.view_in_service, null);
        final LinearLayout viewservice_ll = mView.findViewById(R.id.viewservice_ll);

        viewservice_ll.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final ImageView iv = mView.findViewById(R.id.bt);
                iv.setImageResource(R.drawable.abeetouch);
                Display display = ((WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        mTouchX = motionEvent.getRawX();
                        mTouchY = motionEvent.getRawY();
                        mViewX = params.x;
                        mViewY = params.y;

                        break;

                    case MotionEvent.ACTION_UP:

                        iv.setImageResource(R.drawable.abeeweget);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        x = (int) (motionEvent.getRawX() - mTouchX);
                        y = (int) (motionEvent.getRawY() - mTouchY);

                        params.x = mViewX + x;
                        params.y = mViewY + y;
                        Log.e("X" , String.valueOf(x) + "X좌표");
                        Log.e("X" , String.valueOf(y) + "Y좌표");

                        Log.e("X" , String.valueOf(params.x) + "mTouchX좌표");
                        Log.e("X" , String.valueOf(params.y) + "mTouchY좌표");
                        Log.e("X" , String.valueOf(width) + "displayX좌표");
                        Log.e("X" , String.valueOf(height) + "displayY좌표");
                        Log.e("X" , String.valueOf((-(height- height/2)+(height/7)) ) + "뭘까용");

                        wm.updateViewLayout(mView, params);

                        break;
                }



                if( (params.y < (-(height- height/2)+200) )){
                    Toast.makeText(getApplicationContext() , "광고페이지로 이동합니다." , Toast.LENGTH_LONG).show();
                    Intent it = new Intent(getApplicationContext() , Webview.class);
                    startActivity(it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    wm.removeView(mView);
                }else if((params.y > ((height- height/2)-200) )){
                    Toast.makeText(getApplicationContext() , "광고를 보지않고 종료합니다." , Toast.LENGTH_LONG).show();
                    wm.removeView(mView);

                }
                return true;
            }

        });
        wm.addView(mView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wm != null) {
            if (mView != null) {
                wm.removeView(mView);
                mView = null;
            }
            wm = null;
        }
    }
}