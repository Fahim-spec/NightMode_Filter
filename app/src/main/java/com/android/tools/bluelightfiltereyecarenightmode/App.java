package com.android.tools.bluelightfiltereyecarenightmode;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.ads.AudienceNetworkAds;

public class App extends MultiDexApplication {
    public static final String CHANNEL_ID = "appServiceChannel";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            builder.detectFileUriExposure();
            StrictMode.setVmPolicy(builder.build());
        }

        AudienceNetworkAds.initialize(getApplicationContext());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("KiemTra","7896");
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
