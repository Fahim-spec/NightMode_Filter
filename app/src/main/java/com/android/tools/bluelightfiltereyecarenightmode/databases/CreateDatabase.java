package com.android.tools.bluelightfiltereyecarenightmode.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {

    public static final String TB_COLOR = "COLOR";
    public static final String TB_COLOR_ID = "ID";
    public static final String TB_COLOR_CODE = "CODE";

    public static final String TB_STATUS = "STATUS";
    public static final String TB_STATUS_ID = "ID";
    public static final String TB_STATUS_COLOR = "COLOR";
    public static final String TB_STATUS_INTENSITY = "INTENSITY";
    public static final String TB_STATUS_PERSENT = "PERSENT";
    public static final String TB_STATUS_opacity = "PERSENT_opacity";
    public static final String TB_STATUS_PERSENT_opacity = "opacity";

    public static final String TB_SCHEDULE = "SCHEDULE";
    public static final String TB_SCHEDULE_ID = "ID";
    public static final String TB_SCHEDULE_TIMESTART = "TIMESTART";
    public static final String TB_SCHEDULE_TIMEEND = "TIMEEND";
    public static final String TB_SCHEDULE_PERSENT = "PERSENT";
    public static final String TB_SCHEDULE_COLOR = "COLOR";
    public static final String TB_SCHEDULE_FLAG = "FLAG";

    public static final String TB_SETTING = "SETTING";
    public static final String TB_SETTING_ID = "ID";
    public static final String TB_SETTING_SHOW_NOTIFICATION = "SHOW_NOTIFICATION";
    public static final String TB_SETTING_CONTENT_NOTIFICATION = "CONTENT_NOTIFICATION";

//intensity

    public CreateDatabase(@Nullable Context context) {
        super(context, "COLOR", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbColor = "create table " + TB_COLOR + " ( " + TB_COLOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                TB_COLOR_CODE + " text )";
        String tbStatus = "create table " + TB_STATUS + " ( " + TB_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                TB_STATUS_INTENSITY + " text , " + TB_STATUS_COLOR + " text , "
                + TB_STATUS_opacity + " TEXT ,"
                + TB_STATUS_PERSENT_opacity + " int ,"
                + TB_STATUS_PERSENT + " INTEGER )";

        String tbSchedule = "create table " + TB_SCHEDULE + " ( " + TB_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + TB_SCHEDULE_TIMESTART + " text , "
                + TB_SCHEDULE_TIMEEND + " text , "
                + TB_SCHEDULE_PERSENT + " integer , "
                + TB_SCHEDULE_FLAG + " integer , "
                + TB_SCHEDULE_COLOR + " text ) ";

        String tbSetting = "create table " + TB_SETTING + " ( " + TB_SETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + TB_SETTING_SHOW_NOTIFICATION + " INTEGER ,"
                + TB_SETTING_CONTENT_NOTIFICATION + " INTEGER )";


        sqLiteDatabase.execSQL(tbStatus);
        sqLiteDatabase.execSQL(tbColor);
        sqLiteDatabase.execSQL(tbSchedule);
        sqLiteDatabase.execSQL(tbSetting);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
}
