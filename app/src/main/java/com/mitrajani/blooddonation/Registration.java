package com.mitrajani.blooddonation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;
import java.util.List;

public class Registration extends AppCompatActivity {

    String[] descriptionData = {"Basic Info", "Log-in credentials", "Step Three", "Step Four"};
    Button nextButton;
    Button previousButton;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("Registration");

        final StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

        nextButton = (Button) findViewById(R.id.next_button);
        previousButton = (Button) findViewById(R.id.previous_button) ;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        //adapterViewPager = new ViewPagerAdapter(getSupportFragmentManager());
        //viewPager.setAdapter(adapterViewPager);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (stateProgressBar.getCurrentStateNumber()){
                    case 1:

                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        stateProgressBar.checkStateCompleted(true);
                        previousButton.setVisibility(View.VISIBLE);
                        nextButton.setVisibility(View.INVISIBLE);
                        viewPager.setCurrentItem(getItem(+1), true);
                        break;
                    case 2:
                        //stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        break;

                    /*case 3:
                        //stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                        stateProgressBar.enableAnimationToCurrentState(false);

                        //stateProgressBar.setAllStatesCompleted(true);
                        break;
                    */

                    /*


                    case 4:
                        stateProgressBar.setAllStatesCompleted(true);
                        break;

                    */
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (stateProgressBar.getCurrentStateNumber()){
                    /*
                    case 3:
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        break;
                    */
                    case 2:
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                        previousButton.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(getItem(-1), true);
                        break;
                    case 1:
                        break;

                }
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

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

}
