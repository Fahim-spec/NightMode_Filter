package com.android.tools.bluelightfiltereyecarenightmode.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.tools.bluelightfiltereyecarenightmode.databases.CreateDatabase;
import com.android.tools.bluelightfiltereyecarenightmode.model.ColorModel;

import java.util.ArrayList;
import java.util.List;

public class ColorDAO {
    private SQLiteDatabase database;

    public ColorDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean addColor(String color) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_COLOR_CODE, color);
        long test = database.insert(CreateDatabase.TB_COLOR, null, contentValues);
        if (test != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<ColorModel> getColor() {
        List<ColorModel> list = new ArrayList<>();
        String query = "select * from " + CreateDatabase.TB_COLOR;
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ColorModel colorModel = new ColorModel();

            colorModel.setId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_COLOR_ID)));
            colorModel.setColorCode(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_COLOR_CODE)));
            list.add(colorModel);
            cursor.moveToNext();
        }
        return list;
    }
    public boolean updateCodeColor(int id, String color) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_COLOR_CODE, color);

        int kt = database.update(CreateDatabase.TB_COLOR, contentValues, id + " = " + CreateDatabase.TB_COLOR_ID, null);
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }
}
