package com.mitrajani.blooddonation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Mit Rajani on 8/19/2018.
 */

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new MyFirstPagerFragment();
        }
        else
        {
            return new MySecondPagerFragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }


}
