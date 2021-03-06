package com.android.tools.bluelightfiltereyecarenightmode;

import android.annotation.SuppressLint;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;

import android.os.IBinder;


import android.support.annotation.Nullable;

import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RemoteViews;

import com.android.tools.bluelightfiltereyecarenightmode.activity.MainActivity;
import com.android.tools.bluelightfiltereyecarenightmode.dao.SettingDAO;
import com.android.tools.bluelightfiltereyecarenightmode.dao.StatusDAO;
import com.android.tools.bluelightfiltereyecarenightmode.interfaces.IChangeBackground;
import com.android.tools.bluelightfiltereyecarenightmode.model.ModelCustom;
import com.android.tools.bluelightfiltereyecarenightmode.model.SettingModel;
import com.android.tools.bluelightfiltereyecarenightmode.model.StatusModel;

import java.util.List;

import static com.android.tools.bluelightfiltereyecarenightmode.App.CHANNEL_ID;

public class ServiceBackground extends Service implements IChangeBackground {

    View mFloatingView;
    WindowManager mWindowManager;
    FrameLayout frameLayout;

    String sColor = "";
    StatusDAO statusDAO;
    List<StatusModel> statusModelList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("InflateParams")
    @Override
    public void onCreate() {
        super.onCreate();
        // startNotificationListener();
        //Inflate the floating view layout we created
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_background_color, null, false);
        frameLayout = mFloatingView.findViewById(R.id.frameBackground);
        ModelCustom.getInstance().setSpeed(this);

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
            params = new WindowManager.LayoutParams(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    , View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    , WindowManager.LayoutParams.TYPE_PHONE
                    , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    PixelFormat.TRANSLUCENT);
        }
        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SettingDAO settingDAO = new SettingDAO(this);
        List<SettingModel> settingModelList = settingDAO.getAllSetting();
        SettingModel settingModel = settingModelList.get(0);
        RemoteViews contentView = null;
        switch (settingModel.getContentNotification()) {
            case 0:
                contentView = new RemoteViews(getPackageName(), R.layout.layout_notification_0);
                break;
            case 1:
                contentView = new RemoteViews(getPackageName(), R.layout.layout_notification_mot);
                break;
            case 2:
                contentView = new RemoteViews(getPackageName(), R.layout.layout_notification_hai);
                break;
            case 3:
                contentView = new RemoteViews(getPackageName(), R.layout.layout_activity_ba);
                break;
        }
        Intent intenMain = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, intenMain, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_luncherlight)
                .setContent(contentView).setContentIntent(pendingIntent).build();


        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = contentView;
        }

        String check = intent.getStringExtra("ON");
        if (check != null) {
            if (check.equals("ON")) {
                statusDAO = new StatusDAO(this);
                statusModelList = statusDAO.getStatus();
                String s = statusModelList.get(0).getIntensity() + statusModelList.get(0).getColor();
                frameLayout.setBackgroundColor(Color.parseColor(s));
                float opacity = Float.parseFloat(statusModelList.get(0).getOpacity());
                frameLayout.setAlpha(opacity);
                contentView.setTextViewText(R.id.txtNotifi,statusModelList.get(0).getPercent()+"%");
                // frameLayout.setAlpha(0.25f);
                startForeground(101, notification);
            } else if (check.equals("Off")) {
                String bacground = intent.getStringExtra("background");
                String persent = String.valueOf(intent.getIntExtra("persent",0));
                frameLayout.setBackgroundColor(Color.parseColor(bacground));
                contentView.setTextViewText(R.id.txtNotifi,persent+"%");
                startForeground(101, notification);
            }
        }
        return START_STICKY;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("KiemTra","123456789");
        super.onTaskRemoved(rootIntent);
        Intent i = new Intent(getApplicationContext(), ServiceBackground.class);
        i.setPackage(getPackageName());
        startService(i);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Intent i = new Intent(getApplicationContext(), ServiceBackground.class);
        i.setPackage(getPackageName());
        startService(i);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(false);
        stopSelf();
        Log.d("KiemTra","123456");
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }

    @Override
    public void intensity(String s, String s1) {
        sColor = s;
        String color = sColor + s1;
        //frameLayout.setBackgroundColor(Color.parseColor(color));
    }


}
