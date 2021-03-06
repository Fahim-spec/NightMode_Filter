package com.android.tools.bluelightfiltereyecarenightmode.ads;

import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;


public abstract class MyBaseMainActivity extends MyBaseActivityWithAds {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, MyAdsController.getApplicationAdsId(getApplicationContext()));

        MyAdsController.setTypeAds(getApplicationContext());

        MyAdsController.handleInterstitialAds(this);

        MyAdsController.listenNetworkChangeToRequestAdsFull(this);
    }

    @Override
    protected void onDestroy() {

        MyAdsController.releaseAds_Callbacks();

        super.onDestroy();
    }
}
