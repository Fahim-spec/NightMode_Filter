package com.android.tools.bluelightfiltereyecarenightmode.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.dao.ScheduleDAO;
import com.android.tools.bluelightfiltereyecarenightmode.model.ScheduleModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateTimeFillterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTxtStart, mTxtEnd, mTxtThoiGianBatDau, mTxtThoiGianKetThuc, mTxtOK;
    TextView mTxtHuy, mTxtDongY;
    ImageView mImageBack;
    Dialog dialogSetTime;
    TimePicker timePicker;
    String thoiGianBatDau, thoiGianKetThuc;
    ScheduleDAO scheduleDAO;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_time);
        mTxtStart = findViewById(R.id.txtStart);
        mTxtEnd = findViewById(R.id.txtEnd);
        mTxtThoiGianBatDau = findViewById(R.id.txtThoiGianBatDau);
        mTxtThoiGianKetThuc = findViewById(R.id.txtThoiGianKetThuc);
        mTxtOK = findViewById(R.id.txtOK);
        mImageBack = findViewById(R.id.imgBack);

        mTxtStart.setOnClickListener(this);
        mTxtEnd.setOnClickListener(this);
        mTxtOK.setOnClickListener(this);
        mImageBack.setOnClickListener(this);

        thoiGianBatDau = getIntent().getStringExtra("thoiGianBatDau");
        thoiGianKetThuc = getIntent().getStringExtra("thoiGianKetThuc");
        id = getIntent().getIntExtra("ID", -1);
        scheduleDAO = new ScheduleDAO(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtOK:
                if(id == -1){
                    addTime();
                }else {
                    updateTime();
                }

                break;
            case R.id.txtStart:
                setTimeDialog(mTxtStart, mTxtThoiGianBatDau);
                break;
            case R.id.txtEnd:
                setTimeDialog(mTxtEnd, mTxtThoiGianKetThuc);
                break;
        }
    }

    private void updateTime() {
        String sBatDau = mTxtThoiGianBatDau.getText().toString();
        String sKetThuc = mTxtThoiGianKetThuc.getText().toString();

        if (!sBatDau.isEmpty() || !sKetThuc.isEmpty()) {

            boolean check = scheduleDAO.updateTimeFilter(id, sBatDau, sKetThuc);
            if (check) {
                Toast.makeText(this, getResources().getString(R.string.thanh_cong), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, getResources().getString(R.string.that_bai), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.toas_chua_chon_time), Toast.LENGTH_LONG).show();
        }
    }

    private void addTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date dateBatDau = dateFormat.parse(thoiGianBatDau);
            Date dateKetThuc = dateFormat.parse(thoiGianKetThuc);
            String sBatDau = mTxtThoiGianBatDau.getText().toString();
            String sKetThuc = mTxtThoiGianKetThuc.getText().toString();
            if (!sBatDau.isEmpty() || !sKetThuc.isEmpty()) {

                Date dateStart = dateFormat.parse(sBatDau);
                Date dateEnd = dateFormat.parse(sKetThuc);

                if (dateStart.after(dateBatDau) && dateEnd.before(dateKetThuc)) {

                    ScheduleModel scheduleModel = new ScheduleModel();
                    scheduleModel.setTimeStart(sBatDau);
                    scheduleModel.setTimeEnd(sKetThuc);
                    scheduleModel.setPercent(50);
                    scheduleModel.setColor("b67b44");

                    boolean check = scheduleDAO.addSchedule(scheduleModel);
                    if (check) {
                        Toast.makeText(this, getResources().getString(R.string.thanh_cong), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.that_bai), Toast.LENGTH_LONG).show();
                    }
                } else {
                    String s = getResources().getString(R.string.chon_khoang_tg) + " " + thoiGianBatDau + getResources().getString(R.string.den) + " " + thoiGianKetThuc;
                    Toast.makeText(this, s, Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, getResources().getString(R.string.toas_chua_chon_time), Toast.LENGTH_LONG).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void setTimeDialog(final TextView txt, final TextView txt2) {
        dialogSetTime = new Dialog(this);
        dialogSetTime.setContentView(R.layout.dialog_settime);
        mTxtHuy = dialogSetTime.findViewById(R.id.txtHuy);
        mTxtDongY = dialogSetTime.findViewById(R.id.txtDongY);
        timePicker = dialogSetTime.findViewById(R.id.dateTime);
        mTxtDongY.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                String sGio, sPhut;
                if (gio < 10) {
                    sGio = "0" + gio;
                } else {
                    sGio = String.valueOf(gio);
                }
                if (phut < 10) {
                    sPhut = "0" + phut;
                } else {
                    sPhut = String.valueOf(phut);
                }
                txt.setText(sGio + ":" + sPhut);
                txt2.setText(sGio + ":" + sPhut);

                dialogSetTime.cancel();
            }
        });
        dialogSetTime.show();
    }
}
