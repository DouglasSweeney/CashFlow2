package com.doug.cashflow.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.Input;
import com.doug.cashflow.model.system.ToastFlags;
import com.doug.cashflow.model.system.ValidateInputPage;

import java.util.List;

public class InputPagerActivity extends AppCompatActivity {

    private static final String TAG = "InputPagerActivity";

//    private static final String EXTRA_INPUT = "input";
    private static final String EXTRA_USER_ID = "user_id";

    @SuppressLint("StaticFieldLeak")
    public  static ViewPager mViewPager;
    private static Context mContext;
    private static List<Input> mInputs;
    private static Integer mUserId;
    private static boolean mPageEnd = true;
    private static int mJumpIndex;
    private static int counter = 0;
    private Fragment fragmentsArray[];
    private static ToastFlags toastFlags = new ToastFlags();

    public static Intent newIntent(Context packageContext, Integer user_id) {
        mContext = packageContext;

        Intent intent = new Intent(packageContext, InputPagerActivity.class);
        intent.putExtra(EXTRA_USER_ID, user_id);

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

        LoginLab.setCurrentActivity(this);

        InputLab.getInstance(this.getApplicationContext(), getSupportFragmentManager(), null);
        mInputs = InputLab.getInputs();

        toastFlags.init(mInputs.size());

        LoginLab.getInstance(this, false);

        setContentView(R.layout.activity_input_pager);

        mUserId = (Integer)getIntent().getSerializableExtra(EXTRA_USER_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_input_view_pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

             @Override
             public Fragment getItem(int position) {
                 Log.d(TAG, "getItem() position: " + position);

                 toastFlags.reset();

                 Input input=mInputs.get(position);
                 return InputFragment.newInstance(input.getHeader(), mUserId, getApplicationContext());
             }

             @Override
             public int getCount() {
                    return mInputs.size();
                }
        });

        setCurrentItem(InputLab.getAccount401k().getName(mContext));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int selectedIndex;

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                selectedIndex = arg0;
            }

            boolean callHappened;

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                if (mPageEnd && arg0 == selectedIndex && !callHappened) {
                    Log.d(getClass().getName(), "Okay");
                    mPageEnd = false;//To avoid multiple calls.
                    callHappened = true;
                } else {
                    mPageEnd = false;
                }
//                System.out.println("mViewPager::onPageScrolled");
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
/*                if (InputFragment.mJumpSelected) {
                    InputFragment.mJumpSelected = false;
                    return;
                }
                */
                if (selectedIndex == mViewPager.getCurrentItem() - 1) {
                    mPageEnd = true;
                    Log.d(TAG, "OnPageScrollStateChanged mPageEnd: " + mPageEnd);
                }

                // inside "if" is a page change
                if (selectedIndex != -1 && !mPageEnd) {
                    Log.d(TAG,"mViewPager::onPageScrollStateChange changing pages. selectedIndex==old index & getCurrentIndex() == new page");
                    Log.d(TAG,"mViewPager::onPageScrollStateChange selectedIndex2: " + selectedIndex);
                    Log.d(TAG,"mViewPager::onPageScrollStateChange currentPage2: " + mViewPager.getCurrentItem());
                    ValidateInputPage validateInputPage = new ValidateInputPage();

                    validateInputPage.validate(getApplicationContext(), selectedIndex);
                    selectedIndex = -1;
                }
            }
        });
//        spinner.dismiss();
    }

    public static void setCurrentItem(String input) {
//        InputFragment.mJumpSelected = false;
//        InputFragment.mJumpIndex = -1;

//        Log.d(TAG, "input: " + input);
//        Log.d(TAG, "mInputs: " + mInputs.size());
//        Log.d(TAG, "mViewPager: " + mViewPager);
        for (int i = 0; i < mInputs.size(); i++) {
            if (mInputs.get(i).getHeader().equals(input)) {
                mViewPager.setCurrentItem(i);
//                InputFragment.mJumpSelected = false;
//                InputFragment.mJumpIndex = i;
                break;
            }
        }
    }

    public static int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    public static int getPageIndex(String input) {
        int returnValue = -1;

        for (int i = 0; i < mInputs.size(); i++) {
            if (mInputs.get(i).getHeader().equals(input)) {
                returnValue = i;
                break;
             }
        }

        return returnValue;
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

//        mViewPager.removeAllViews(); // Still crashed with or without line

    }
}
