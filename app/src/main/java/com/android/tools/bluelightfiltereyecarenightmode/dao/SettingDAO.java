package com.android.tools.bluelightfiltereyecarenightmode.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.tools.bluelightfiltereyecarenightmode.databases.CreateDatabase;
import com.android.tools.bluelightfiltereyecarenightmode.model.SettingModel;

import java.util.ArrayList;
import java.util.List;

public class SettingDAO {
    private SQLiteDatabase database;

    public SettingDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean addSetting(SettingModel settingModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_SETTING_SHOW_NOTIFICATION, settingModel.getShowNotification());
        contentValues.put(CreateDatabase.TB_SETTING_CONTENT_NOTIFICATION, settingModel.getContentNotification());

        long kt = database.insert(CreateDatabase.TB_SETTING, null, contentValues);

        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean updateShowNotification (int show){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_SETTING_SHOW_NOTIFICATION,show);
        long kt= database.update(CreateDatabase.TB_SETTING,contentValues,null,null);
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean updateContentNotification(int content){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_SETTING_CONTENT_NOTIFICATION,content);
        long kt= database.update(CreateDatabase.TB_SETTING,contentValues,null,null);
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<SettingModel> getAllSetting() {
        List<SettingModel> settingModelList = new ArrayList<>();
        String query = "select * from " + CreateDatabase.TB_SETTING;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SettingModel settingModel = new SettingModel();
            settingModel.setShowNotification(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_SETTING_SHOW_NOTIFICATION)));
            settingModel.setContentNotification(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_SETTING_CONTENT_NOTIFICATION)));
            settingModelList.add(settingModel);

            cursor.moveToNext();
        }
        return settingModelList;
    }
    public int count() {
        String sql = "select * from " + CreateDatabase.TB_SETTING;

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, null);
        return cursor.getCount();
    }
}
