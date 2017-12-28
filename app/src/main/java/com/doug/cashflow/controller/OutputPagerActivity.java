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

public class OutputPagerActivity  extends AppCompatActivity {

    private static final String TAG = "OutputPagerActivity";

    private static final String EXTRA_INPUT = "input";

    private static ViewPager mViewPager;
    private static List<Input> mOutputs;

    public static Intent newIntent(Context packageContext, String input) {
        Intent intent = new Intent(packageContext, InputPagerActivity.class);
        intent.putExtra(EXTRA_INPUT, input);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() called");

//        LayoutInflater inflater = getLayoutInflater();
//        ProgressBar spinner = (ProgressBar)inflater.inflate(R.layout.progress_bar, null);

//        spinner.setVisibility(View.VISIBLE);

//        ProgressDialog spinner = new ProgressDialog(this);
//        spinner.setCancelable(false);
//        spinner.setTitle("Progress");
//        spinner.show();

        setContentView(R.layout.activity_output_pager);

        String input = (String) getIntent().getSerializableExtra(EXTRA_INPUT);

        mViewPager = (ViewPager) findViewById(R.id.activity_output_pager_view_pager);

        ModelLab.getInstance(this.getApplicationContext());
        LoginLab.setCurrentActivity(this);

        Calculate calculate = new Calculate();
        calculate.run();

        OutputLab.getInstance(this.getApplicationContext());
        mOutputs = OutputLab.getOutputs();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
//                Log.d(TAG, "getItem(int position)");
//                Log.d(TAG, "    position: " + position);
//                Log.d(TAG, "    mOutputs.size(): " + mOutputs.size());

                Input output=mOutputs.get(position);
                return OutputFragment.newInstance(output.getHeader());
            }

            @Override
            public int getCount() {
                    return mOutputs.size();
                }
        });

//        spinner.dismiss();

        setCurrentItem(this.getResources().getString(R.string.output_header_income_graph));
    }

    public static void setCurrentItem(String output) {
//        Log.d(TAG, "output: " + output);
//        Log.d(TAG, "mOutputs: " + mOutputs.size());
//        Log.d(TAG, "mViewPager: " + mViewPager);

        for (int i = 0; i < mOutputs.size(); i++) {
            if (mOutputs.get(i).getHeader().equals(output)) {
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
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop() called");
        Log.d(TAG, "    mViewPager.toString()" + mViewPager.toString());

        mViewPager.removeAllViews();
    }
}
