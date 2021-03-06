package com.android.tools.bluelightfiltereyecarenightmode.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.tools.bluelightfiltereyecarenightmode.Fragment.FilterFragment;
import com.android.tools.bluelightfiltereyecarenightmode.Fragment.ScheduleFragment;
import com.android.tools.bluelightfiltereyecarenightmode.R;

public class TabAdapter extends FragmentPagerAdapter {
   private Context context;

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FilterFragment();
            case 1:
                return new ScheduleFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return context.getResources().getString(R.string.bo_loc);
            case 1:
                return context.getResources().getString(R.string.lich_trinh);
        }
        return null;

    }
}
