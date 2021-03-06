package com.android.tools.bluelightfiltereyecarenightmode.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.android.tools.bluelightfiltereyecarenightmode.NotificationListener;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class AlarmUtils {
    public static void addAlarm(Context context, int hour, int minute, int flag, String color, int Percent) {
        PendingIntent pendingIntent;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationListener.class);
        intent.putExtra("COLOR", color);
        intent.putExtra("Percent", Percent);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        pendingIntent = PendingIntent.getBroadcast(context, flag, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public static void cancelAlarm(Context context, int flag) {
        PendingIntent pendingIntent;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent iAlarm;

        iAlarm = new Intent(context, NotificationListener.class);
        iAlarm.putExtra("extra", "on");
        pendingIntent = PendingIntent.getBroadcast(context, flag, iAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}

