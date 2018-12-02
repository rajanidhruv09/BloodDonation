package com.mitrajani.blooddonation;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class tryCheckboxShift extends AppCompatActivity {

    RadioButton camp;
    RadioButton individual;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_checkbox_shift);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Blood Request");

        camp = (RadioButton) findViewById(R.id.camp);
        individual = (RadioButton) findViewById(R.id.indi);

        viewPager = (ViewPager) findViewById(R.id.viewpagerCheck);
        viewPager.setAdapter(new MyCheckBoxViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        individual.setChecked(true);


        camp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewPager.setCurrentItem(0, true);
            }
        });

        individual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewPager.setCurrentItem(1, true);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
