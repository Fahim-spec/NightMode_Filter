package com.android.tools.bluelightfiltereyecarenightmode.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.dao.SettingDAO;
import com.android.tools.bluelightfiltereyecarenightmode.model.SettingModel;

import java.util.List;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBack;
    TextView txtHienThiThongBao;
    Switch swNotification;
    SettingDAO settingDAO;
    CheckBox ck1, ck2, ck3, ck4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        imgBack = findViewById(R.id.imgBack);
        txtHienThiThongBao = findViewById(R.id.txtHienThiThongBao);
        swNotification = findViewById(R.id.swNotification);
        ck1 = findViewById(R.id.ck1);
        ck2 = findViewById(R.id.ck2);
        ck3 = findViewById(R.id.ck3);
        ck4 = findViewById(R.id.ck4);

        settingDAO = new SettingDAO(this);
        List<SettingModel> settingModelList = settingDAO.getAllSetting();
        SettingModel settingModel = settingModelList.get(0);

        if (settingModel.getShowNotification() == 0) {
            swNotification.setChecked(false);
        } else if (settingModel.getShowNotification() == 1) {
            swNotification.setChecked(true);
        }
        switch (settingModel.getContentNotification()){
            case 0:
                ck1.setChecked(true);
                break;
            case 1:
                ck2.setChecked(true);
                break;
            case 2:
                ck3.setChecked(true);
                break;
            case 3:
                ck4.setChecked(true);
                break;
        }

        imgBack.setOnClickListener(this);
        txtHienThiThongBao.setOnClickListener(this);
        ck1.setOnClickListener(this);
        ck2.setOnClickListener(this);
        ck3.setOnClickListener(this);
        ck4.setOnClickListener(this);

        swNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settingDAO.updateShowNotification(1);
                } else {
                    settingDAO.updateShowNotification(0);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.ck1:
                setCheckbox(ck1, ck2, ck3, ck4);
                settingDAO.updateContentNotification(0);
                break;
            case R.id.ck2:
                setCheckbox(ck2, ck1, ck3, ck4);
                settingDAO.updateContentNotification(1);
                break;
            case R.id.ck3:
                setCheckbox(ck3, ck2, ck1, ck4);
                settingDAO.updateContentNotification(2);
                break;
            case R.id.ck4:
                setCheckbox(ck4, ck1, ck2, ck3);
                settingDAO.updateContentNotification(3);
                break;
        }
    }
    private void setCheckbox(CheckBox ck1, CheckBox ck2, CheckBox ck3, CheckBox ck4) {
        ck1.setChecked(true);
        ck2.setChecked(false);
        ck3.setChecked(false);
        ck4.setChecked(false);
    }
}
