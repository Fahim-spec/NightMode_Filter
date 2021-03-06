package com.android.tools.bluelightfiltereyecarenightmode.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.tools.bluelightfiltereyecarenightmode.databases.CreateDatabase;
import com.android.tools.bluelightfiltereyecarenightmode.model.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    private SQLiteDatabase database;

    public static final int DEFAULT_PROGESS = 55;

    public ScheduleDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean addSchedule(ScheduleModel scheduleModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_SCHEDULE_TIMESTART, scheduleModel.getTimeStart());
        contentValues.put(CreateDatabase.TB_SCHEDULE_TIMEEND, scheduleModel.getTimeEnd());
        contentValues.put(CreateDatabase.TB_SCHEDULE_PERSENT, scheduleModel.getPercent());
        contentValues.put(CreateDatabase.TB_SCHEDULE_COLOR, scheduleModel.getColor());
        // contentValues.put();

        long kt = database.insert(CreateDatabase.TB_SCHEDULE, null, contentValues);
        if (kt != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteSchedule(int id) {
        long kiemTra = database.delete(CreateDatabase.TB_SCHEDULE, CreateDatabase.TB_SCHEDULE_ID + " = " + id, null);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTimeFilter(int id, String start, String end) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_SCHEDULE_TIMESTART, start);
        contentValues.put(CreateDatabase.TB_SCHEDULE_TIMEEND, end);

        long kiemTra = database.update(CreateDatabase.TB_SCHEDULE, contentValues, CreateDatabase.TB_SCHEDULE_ID + " = " + id, null);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updatePercent(int id, int percent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_SCHEDULE_PERSENT, percent);

        long kiemTra = database.update(CreateDatabase.TB_SCHEDULE, contentValues, CreateDatabase.TB_SCHEDULE_ID + " = " + id, null);

        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateColor(String color, int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_SCHEDULE_COLOR, color);
        long kiemTra = database.update(CreateDatabase.TB_SCHEDULE, contentValues, CreateDatabase.TB_SCHEDULE_ID + " = " + id, null);

        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateColor(int id, String color) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_SCHEDULE_COLOR, color);
        long kiemTra = database.update(CreateDatabase.TB_SCHEDULE, contentValues, CreateDatabase.TB_SCHEDULE_ID + " = " + id, null);

        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<ScheduleModel> getAll() {
        List<ScheduleModel> list = new ArrayList<>();
        String query = "select * from " + CreateDatabase.TB_SCHEDULE;
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ScheduleModel scheduleModel = new ScheduleModel();

            scheduleModel.setId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_SCHEDULE_ID)));
            scheduleModel.setTimeStart(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_SCHEDULE_TIMESTART)));
            scheduleModel.setTimeEnd(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_SCHEDULE_TIMEEND)));
            scheduleModel.setPercent(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_SCHEDULE_PERSENT)));
            scheduleModel.setColor(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_SCHEDULE_COLOR)));

            list.add(scheduleModel);
            cursor.moveToNext();
        }
        return list;
    }

}
