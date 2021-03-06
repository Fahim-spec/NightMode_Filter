package com.android.tools.bluelightfiltereyecarenightmode.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.ServiceBackground;
import com.android.tools.bluelightfiltereyecarenightmode.ads.Idelegate;
import com.android.tools.bluelightfiltereyecarenightmode.ads.MyAdsController;
import com.android.tools.bluelightfiltereyecarenightmode.ads.MyBaseActivityWithAds;
import com.android.tools.bluelightfiltereyecarenightmode.dao.StatusDAO;
import com.android.tools.bluelightfiltereyecarenightmode.model.StatusModel;

import java.util.List;

public class SettingActivity extends MyBaseActivityWithAds implements View.OnClickListener {
    ImageView imgBack, mImagePause;
    LinearLayout lnNotification;
    TextView txtCheDo, mTxtPause, mTxtStart, mTxtPhanTram;
    Dialog dialogCheDo;
    FrameLayout tamDung60s, runService;
    SeekBar skDoMo;
    int k = 59;
    StatusDAO statusDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        imgBack = findViewById(R.id.imgBack);
        lnNotification = findViewById(R.id.lnNotification);
        txtCheDo = findViewById(R.id.txtCheDo);
        tamDung60s = findViewById(R.id.tamDung60s);
        mImagePause = findViewById(R.id.imgPause);
        mTxtPause = findViewById(R.id.txtPause);
        mTxtStart = findViewById(R.id.txtStart);
        runService = findViewById(R.id.runService);
        skDoMo = findViewById(R.id.skDoMo);
        mTxtPhanTram = findViewById(R.id.txtPhanTram);
        skDoMo.setMax(75);

        statusDAO = new StatusDAO(this);


        lnNotification.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtCheDo.setOnClickListener(this);
        tamDung60s.setOnClickListener(this);
        List<StatusModel> statusModels = statusDAO.getStatus();
        skDoMo.setProgress(statusModels.get(0).getPercentOpacity());
        mTxtPhanTram.setText(statusModels.get(0).getPercentOpacity() + "%");

        skDoMo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTxtPhanTram.setText(progress + "%");
                float m = (float) ((progress * 1.0) / (100 * 1.0));
                statusDAO.updateOpacity(String.valueOf(m));
                boolean kt = statusDAO.updatePresentOpacity(progress);
                Intent intent = new Intent(SettingActivity.this, ServiceBackground.class);
                intent.putExtra("ON", "ON");
              //  startService(intent);
                ContextCompat.startForegroundService(SettingActivity.this,intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.lnNotification:

                MyAdsController.showAdsFullBeforeDoAction(new Idelegate() {
                    @Override
                    public void callBack(Object value, int where) {
                        startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                    }
                });

                break;
            case R.id.txtCheDo:
                dialogCheDo = new Dialog(this);
                dialogCheDo.setContentView(R.layout.dialog_chedo);
                dialogCheDo.show();
                break;
            case R.id.tamDung60s:
                if (MainActivity.checkService) {
                    k = 60;
                    tamDung60s.setVisibility(View.GONE);
                    runService.setVisibility(View.VISIBLE);
                    thread.start();
                    Intent intent = new Intent(this, ServiceBackground.class);
                    stopService(intent);
                }
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int value = msg.arg1;

            if (value == 0) {

                MyAdsController.showAdsFullBeforeDoAction(new Idelegate() {
                    @Override
                    public void callBack(Object value, int where) {
                        handler.removeCallbacksAndMessages(null);
                        Intent intent = new Intent(SettingActivity.this, SettingActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        tamDung60s.setVisibility(View.VISIBLE);
                        runService.setVisibility(View.GONE);

                        Intent iService = new Intent(SettingActivity.this, ServiceBackground.class);

                        iService.putExtra("ON", "ON");
                        startService(iService);
                    }
                });


            } else {

                mTxtStart.setText("0:" + value + "");
            }
        }
    };

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (k != 0) {
                k--;
                Message message = handler.obtainMessage();
                message.arg1 = k;
                handler.sendMessage(message);
                SystemClock.sleep(1000);
            }
        }
    });
}