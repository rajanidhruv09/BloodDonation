package com.mitrajani.blooddonation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Mit Rajani on 11/14/2018.
 */

public class MyCheckBoxViewPagerAdapter extends FragmentStatePagerAdapter {
    public MyCheckBoxViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new MyCheckBoxFirstPagerFragment();
        }
        else
        {
            return new MyCheckBoxSecondPagerFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
