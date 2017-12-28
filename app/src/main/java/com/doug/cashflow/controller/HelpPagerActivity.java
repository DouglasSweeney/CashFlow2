package com.doug.cashflow.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.Input;

import java.util.List;

public class HelpPagerActivity extends AppCompatActivity {

    private static final String TAG = "HelpPagerActivity";

//    private static final String EXTRA_INPUT = "input";
    private static final String EXTRA_USER_ID = "user_id";

    private static ViewPager mViewPager;
    private static List<Input> mInputs;
//    private static Integer mUserId;
    private static Context mContext;

    public static Intent newIntent(Context packageContext) {
        mContext = packageContext;

        Intent intent = new Intent(packageContext, HelpPagerActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() called");

        LoginLab.setCurrentActivity(this);

        HelpLab.getInstance(this);

        mInputs = HelpLab.getInputs();

        setContentView(R.layout.activity_help_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_help_view_pager);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

             @Override
             public Fragment getItem(int position) {
//                 Log.d(TAG, "getItem(int position)");
//                 Log.d(TAG, "    position: " + position);
//                 Log.d(TAG, "    mInputs.size(): " + mInputs.size());

                 Input input=mInputs.get(position);
                 return HelpFragment.newInstance(input.getHeader(), getBaseContext());
             }

             @Override
             public int getCount() {
                    return mInputs.size();
                }
        });
//
//        setCurrentItem(input);
    }

    public static void setCurrentItem(String input) {
//        Log.d(TAG, "input: " + input);
//        Log.d(TAG, "mInputs: " + mInputs.size());
//        Log.d(TAG, "mViewPager: " + mViewPager);

        for (int i = 0; i < mInputs.size(); i++) {
            if (mInputs.get(i).getHeader().equals(input)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop() called");
        Log.d(TAG, "    mViewPager.toString()" + mViewPager.toString());

        mViewPager.removeAllViews();
    }
}
