package com.android.tools.bluelightfiltereyecarenightmode.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tools.bluelightfiltereyecarenightmode.Fragment.FilterFragment;
import com.android.tools.bluelightfiltereyecarenightmode.R;
import com.android.tools.bluelightfiltereyecarenightmode.ServiceBackground;
import com.android.tools.bluelightfiltereyecarenightmode.adapter.TabAdapter;
import com.android.tools.bluelightfiltereyecarenightmode.ads.Idelegate;
import com.android.tools.bluelightfiltereyecarenightmode.ads.MyAdsController;
import com.android.tools.bluelightfiltereyecarenightmode.ads.MyBaseActivityWithAds;
import com.android.tools.bluelightfiltereyecarenightmode.dao.SettingDAO;
import com.android.tools.bluelightfiltereyecarenightmode.databases.CreateDatabase;
import com.android.tools.bluelightfiltereyecarenightmode.model.SettingModel;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends MyBaseActivityWithAds implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    ImageView mImageOpenMenu;
    DrawerLayout mDrawerLayout;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    Toolbar mToolbar;
    AppBarLayout mAppBarLayout;
    NavigationView navigationView;
    TabAdapter adapter;
    TextView txtChinhSachBaoMat, txtEmail, txtOk, txtTacGia;
    @SuppressLint("StaticFieldLeak")
    public static SwitchCompat swService;
    Dialog dialogThongTin;
    public static boolean checkService = true;
    SettingDAO settingDAO;
    Intent intent;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, MyAdsController.getApplicationAdsId(getApplicationContext()));

        MyAdsController.setTypeAds(getApplicationContext());

        MyAdsController.handleInterstitialAds(this);

        MyAdsController.listenNetworkChangeToRequestAdsFull(this);

        setContentView(R.layout.activity_main);

        mImageOpenMenu = findViewById(R.id.open_menu);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);
        mToolbar = findViewById(R.id.toolbar);
        mAppBarLayout = findViewById(R.id.appBarLayout);
        navigationView = findViewById(R.id.NavigationViewTrangChu);
        swService = findViewById(R.id.swService);
        adapter = new TabAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mTabLayout.setupWithViewPager(mViewPager);

        mImageOpenMenu.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        CreateDatabase createDatabase = new CreateDatabase(this);
        createDatabase.open();

        intent = new Intent(this, ServiceBackground.class);

        swService.setChecked(checkService);
        swService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkService) {
                    FilterFragment.check_On_Off = true;
                    runService();
                } else {
                    stopService(intent);
                    FilterFragment.check_On_Off = false;
                }
                checkService = !checkService;
            }
        });
        settingDAO = new SettingDAO(this);
        if (settingDAO.count() == 0) {
            SettingModel settingModel = new SettingModel();
            settingModel.setContentNotification(0);
            settingModel.setShowNotification(0);
            settingDAO.addSetting(settingModel);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_menu:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.txtChinhSachBaoMat:
                String url = getResources().getString(R.string.link_chinh_sach_bao_mat);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.txtEmail:
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.EmailFeedback)});
                Email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_app));
                startActivity(Intent.createChooser(Email, getResources().getString(R.string.send_feesback)));
                break;
            case R.id.txtOK:
                dialogThongTin.cancel();
                break;
            case R.id.txtTacGia:
                String url1 = getResources().getString(R.string.link_home);
                Intent i1 = new Intent(Intent.ACTION_VIEW);
                i1.setData(Uri.parse(url1));
                startActivity(i1);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itCaiDat:

                MyAdsController.showAdsFullBeforeDoAction(new Idelegate() {
                    @Override
                    public void callBack(Object value, int where) {
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                    }
                });

                break;
            case R.id.itDanhGia:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;
            case R.id.itShare:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                shareIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.choose)));
                break;
            case R.id.itThongTin:

                dialogThongTin = new Dialog(this);
                dialogThongTin.setContentView(R.layout.dialog_infor);
                txtChinhSachBaoMat = dialogThongTin.findViewById(R.id.txtChinhSachBaoMat);
                txtEmail = dialogThongTin.findViewById(R.id.txtEmail);
                txtOk = dialogThongTin.findViewById(R.id.txtOK);
                txtTacGia = dialogThongTin.findViewById(R.id.txtTacGia);

                txtOk.setOnClickListener(this);
                txtTacGia.setOnClickListener(this);
                txtChinhSachBaoMat.setOnClickListener(this);
                txtEmail.setOnClickListener(this);
                dialogThongTin.show();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        MyAdsController.releaseAds_Callbacks();

        //super.onBackPressed();

        finishAffinity();
    }

    private void runService() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            intent.putExtra("ON", "ON");
           // startService(intent);
            ContextCompat.startForegroundService(MainActivity.this,intent);
        } else if (Settings.canDrawOverlays(this)) {
            intent.putExtra("ON", "ON");
            //startService(intent);
            ContextCompat.startForegroundService(MainActivity.this,intent);
        }
    }

    @Override
    protected void onDestroy() {

        //hCheckLoadQcBanner.removeCallbacksAndMessages(null);

        MyAdsController.releaseAds_Callbacks();

        super.onDestroy();
    }
}
