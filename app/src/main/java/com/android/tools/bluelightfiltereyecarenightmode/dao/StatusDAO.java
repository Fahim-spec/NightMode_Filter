package com.android.tools.bluelightfiltereyecarenightmode.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.tools.bluelightfiltereyecarenightmode.databases.CreateDatabase;
import com.android.tools.bluelightfiltereyecarenightmode.model.StatusModel;

import java.util.ArrayList;
import java.util.List;

public class StatusDAO {
    private SQLiteDatabase database;

    public StatusDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean addStatus(StatusModel statusModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CreateDatabase.TB_STATUS_COLOR, statusModel.getColor());
        contentValues.put(CreateDatabase.TB_STATUS_INTENSITY, statusModel.getIntensity());
        contentValues.put(CreateDatabase.TB_STATUS_PERSENT, statusModel.getPercent());
        contentValues.put(CreateDatabase.TB_STATUS_opacity, statusModel.getOpacity());
        long kt = database.insert(CreateDatabase.TB_STATUS, null, contentValues);
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<StatusModel> getStatus() {
        List<StatusModel> list = new ArrayList<>();
        String query = "select * from " + CreateDatabase.TB_STATUS;
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            StatusModel statusModel = new StatusModel();

            statusModel.setId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_STATUS_ID)));
            statusModel.setIntensity(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_STATUS_INTENSITY)));
            statusModel.setColor(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_STATUS_COLOR)));
            statusModel.setPercent(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_STATUS_PERSENT)));
            statusModel.setOpacity(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_STATUS_opacity)));
            statusModel.setPercentOpacity(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_STATUS_PERSENT_opacity)));

            list.add(statusModel);
            cursor.moveToNext();
        }
        return list;
    }
    public boolean updatePresentOpacity(int present){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_STATUS_PERSENT_opacity,present);
        long kt = database.update(CreateDatabase.TB_STATUS, contentValues, null, null);
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean updateOpacity(String opacity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_STATUS_opacity, opacity);
        long kt = database.update(CreateDatabase.TB_STATUS, contentValues, null, null);
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateStatus(StatusModel statusModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_STATUS_INTENSITY, statusModel.getIntensity());
        contentValues.put(CreateDatabase.TB_STATUS_COLOR, statusModel.getColor());
        contentValues.put(CreateDatabase.TB_STATUS_PERSENT, statusModel.getPercent());
        long kt = database.update(CreateDatabase.TB_STATUS, contentValues, statusModel.getId() + " = " + CreateDatabase.TB_STATUS_ID, null);
        if (kt != 0) {
            return true;
        } else {
            return false;
        }

    }
}
