package com.example.overayview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LockActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton coverImage;
    private ImageView pointImage, missionImage, homeImage, linkToUrlImage, lockerImage, abeeLogo;
    private TextView dateTxt, timeTxt, pointTxt, pointPlusTxt;
    private LinearLayout dragLayout;

    private int index, res;
    private static final int[] randomResources = {
            R.drawable.abee_ad_1,
            R.drawable.abee_ad_2,
            R.drawable.abee_ad_3,
            R.drawable.abee_ad_4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        initView();
        initData();
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
        dragLayout = findViewById(R.id.drag_layout);

        coverImage.setOnClickListener(this);
        pointImage.setOnClickListener(this);
        missionImage.setOnClickListener(this);
        homeImage.setOnClickListener(this);
        linkToUrlImage.setOnClickListener(this);
        lockerImage.setOnClickListener(this);

        coverImage.setBackground(new BitmapDrawable(
                getResources(), BitmapFactory.decodeResource(
                getResources(), R.drawable.abee_ad_1
        )));
    }

    private void initData() {
        index = (int) Math.random() * 10;
        res = randomResources[index];
        coverImage.setImageResource(res);
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

    private final class LongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
            String[] mimeTypes = {
                    ClipDescription.MIMETYPE_TEXT_PLAIN
            };
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }
}
