package com.example.overayview;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
public class WebviewProgressDialog extends Dialog {


    public WebviewProgressDialog(Context context, int theme) {
        super(context,theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);

    }

}
