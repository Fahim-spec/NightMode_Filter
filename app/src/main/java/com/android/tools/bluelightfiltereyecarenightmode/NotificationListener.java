package com.android.tools.bluelightfiltereyecarenightmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.android.tools.bluelightfiltereyecarenightmode.Fragment.FilterFragment;
import com.android.tools.bluelightfiltereyecarenightmode.dao.StatusDAO;
import com.android.tools.bluelightfiltereyecarenightmode.model.StatusModel;

import java.util.List;

public class NotificationListener extends BroadcastReceiver {


    View mFloatingView;
    WindowManager mWindowManager;
    FrameLayout frameLayout;

    String sColor = "";
    StatusDAO statusDAO;
    List<StatusModel> statusModelList;

    @Override
    public void onReceive(Context context, Intent intent) {
        String color = intent.getStringExtra("COLOR");
        int Percent = intent.getIntExtra("Percent", 0);
        String Intensity = FilterFragment.arrCuongDo[Percent];

        Log.d("KiemTra", Intensity);

        mFloatingView = LayoutInflater.from(context).inflate(R.layout.layout_background_color, null, false);
        frameLayout = mFloatingView.findViewById(R.id.frameBackground);

        //Add the view to the window.
        final WindowManager.LayoutParams params;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            params = new WindowManager.LayoutParams(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    , View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    , WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT
                    , WindowManager.LayoutParams.FILL_PARENT
                    , WindowManager.LayoutParams.TYPE_PHONE
                    , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    PixelFormat.TRANSLUCENT);
        }
        //Add the view to the window
        mWindowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);
        frameLayout.setBackgroundColor(Color.parseColor(Intensity + color));


    }
}
