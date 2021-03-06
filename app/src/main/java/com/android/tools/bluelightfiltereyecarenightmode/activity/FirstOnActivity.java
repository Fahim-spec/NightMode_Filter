package com.android.tools.bluelightfiltereyecarenightmode.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.tools.bluelightfiltereyecarenightmode.BuildConfig;
import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.dao.ScheduleDAO;
import com.android.tools.bluelightfiltereyecarenightmode.dao.StatusDAO;
import com.android.tools.bluelightfiltereyecarenightmode.model.ScheduleModel;
import com.android.tools.bluelightfiltereyecarenightmode.model.StatusModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class FirstOnActivity extends AppCompatActivity implements View.OnClickListener {
    FrameLayout page1, page2, page3;
    TextView txtDieuKhoanDichVu, txtBoLoc, txtBatBuoc;
    TextView txtHuy, txtDongY, txtOk, txtThoat, txtMo;
    StatusModel statusModel;
    StatusDAO statusDAO;

    ScheduleModel scheduleModel;
    ScheduleDAO scheduleDAO;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firston);
        txtDieuKhoanDichVu = findViewById(R.id.txtDieuKhoanDichVu);
        txtBoLoc = findViewById(R.id.txtBoLoc);
        txtHuy = findViewById(R.id.txtHuy);
        txtDongY = findViewById(R.id.txtDongY);
        txtOk = findViewById(R.id.txtOK);
        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);
        page3 = findViewById(R.id.page3);
        txtThoat = findViewById(R.id.txtThoat);
        txtMo = findViewById(R.id.txtMo);
        txtBatBuoc = findViewById(R.id.txtBatBuoc);

        txtOk.setOnClickListener(this);
        txtDongY.setOnClickListener(this);
        txtHuy.setOnClickListener(this);
        txtThoat.setOnClickListener(this);
        txtMo.setOnClickListener(this);
        statusDAO = new StatusDAO(this);
        statusModel = new StatusModel();
        scheduleModel = new ScheduleModel();
        scheduleDAO = new ScheduleDAO(this);

        String language = Locale.getDefault().getDisplayLanguage();

        if (language.equals("English")) {
            txtDieuKhoanDichVu.setText(onRead("dieukhoandichvu_en.txt"));
            txtBoLoc.setText(onRead("bolocgiammoimat_en.txt"));
            txtBatBuoc.setText(onRead("chophepbatbuoc_en.txt"));
        } else {
            txtDieuKhoanDichVu.setText(onRead("dieukhoandichvu.txt"));
            txtBoLoc.setText(onRead("bolocgiammoimat.txt"));
            txtBatBuoc.setText(onRead("chophepbatbuoc.txt"));
        }

        checkFirstRun();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String onRead(String s)  // Khuyên dùng
    {
        String mystring = null;
        try {
            InputStream in = getAssets().open(s);
            int size = in.available();
            byte[] buffer = new byte[size];  // cấp cho bộ nhớ đúng bằng kích thước của file,
            in.read(buffer);               //đây là cách nguy hiểm khi đọc file có kích thước 1gb trở lên
            mystring = new String(buffer);

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mystring;
    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            statusModel.setColor("b67b44");
            statusModel.setIntensity("#8C");
            statusModel.setPercent(ScheduleDAO.DEFAULT_PROGESS);
            statusModel.setOpacity("1");
            boolean kt = statusDAO.addStatus(statusModel);
            addScheduleDefault();
            if (kt) {

            }
        } else if (currentVersionCode > savedVersionCode) {
        }
        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtDongY:
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.VISIBLE);
                break;
            case R.id.txtHuy:
                finish();
                break;
            case R.id.txtOK:
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.VISIBLE);
                break;
            case R.id.txtThoat:
                finish();
                break;
            case R.id.txtMo:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
    private void addScheduleDefault() {
        ScheduleModel scheduleModel1 = new ScheduleModel();
        scheduleModel1.setTimeStart("6:00");
        scheduleModel1.setTimeEnd("9:00");
        scheduleModel1.setPercent(50);
        scheduleModel1.setColor("b67b44");
       // AlarmUtils.addAlarm(this, 6, 0, 1, "b67b44", 50);

        ScheduleModel scheduleModel2 = new ScheduleModel();
        scheduleModel2.setTimeStart("9:01");
        scheduleModel2.setTimeEnd("17:00");
        scheduleModel2.setPercent(30);
        scheduleModel2.setColor("b67b44");
        //AlarmUtils.addAlarm(this, 9, 1, 2, "b67b44", 30);

        ScheduleModel scheduleModel3 = new ScheduleModel();
        scheduleModel3.setTimeStart("17:01");
        scheduleModel3.setTimeEnd("21:00");
        scheduleModel3.setPercent(50);
        scheduleModel3.setColor("b67b44");
     //   AlarmUtils.addAlarm(this, 17, 1, 3, "b67b44", 50);

        ScheduleModel scheduleModel4 = new ScheduleModel();
        scheduleModel4.setTimeStart("21:01");
        scheduleModel4.setTimeEnd("5:59");
        scheduleModel4.setPercent(70);
        scheduleModel4.setColor("b67b44");

        scheduleDAO.addSchedule(scheduleModel1);
        scheduleDAO.addSchedule(scheduleModel2);
        scheduleDAO.addSchedule(scheduleModel3);
        scheduleDAO.addSchedule(scheduleModel4);
    }
}
