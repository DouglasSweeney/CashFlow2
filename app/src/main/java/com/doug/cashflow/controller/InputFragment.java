package com.doug.cashflow.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.ValidateInputs;

public class InputFragment extends Fragment {

    private static final String TAG = "InputFragment";

    private static final String ARG_TITLE = "title";
    private static final String ARG_USER_ID = "user_id";

    private String  mTitle;
    private static Integer mUserId;
    private Context context;
    View v = null;

    public static InputFragment newInstance(String title, Integer userId, Context context) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TITLE, title);
//        mTitle = title;
        args.putSerializable(ARG_USER_ID, userId);
        InputFragment fragment = new InputFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle savedInstanceState)");

//        setRetainInstance(true);
        setHasOptionsMenu(true);
        mTitle = (String) getArguments().getSerializable(ARG_TITLE);
        Log.d(TAG, "onCreate() mTitle: " + mTitle);
        mUserId = (Integer) getArguments().getSerializable(ARG_USER_ID);
//        mInput = InputLab.getInstance(this.getContext()).getInput(mTitle);

        if (mUserId != null) {
            InputLab.setUserId(mUserId);
        }
        else {
            mUserId = InputLab.getUserId();
        }

//        LoginLab.setCurrentActivity(getActivity());

        context = getActivity().getApplicationContext();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.input_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
//        Log.d(TAG, "    item: " + item.toString());
//        Log.d(TAG, "    item.getItemId(): " + item.getItemId());
//        Log.d(TAG, "    R.id.account_403b_menu_item: " + R.id.account_403b_menu_item);
//        Log.d(TAG, "    R.id.output_account_403b_menu_item: " + R.id.output_account_403b_menu_item);

        switch (item.getItemId()) {
            case R.id.save_inputs_id:
                InputLab.getAccount401k().saveToDatabase(mUserId);
                InputLab.getAccount403b().saveToDatabase(mUserId);
                InputLab.getBrokerage().saveToDatabase(mUserId);
                InputLab.getCashBalance().saveToDatabase(mUserId);
                InputLab.getDeductions().saveToDatabase(mUserId);
                InputLab.getExpenses().saveToDatabase(mUserId);
                InputLab.getRothIra().saveToDatabase(mUserId);
                InputLab.getTraditionalIra().saveToDatabase(mUserId);
                InputLab.getPension().saveToDatabase(mUserId);
                InputLab.getPersonal().saveToDatabase(mUserId);
                InputLab.getSalary().saveToDatabase(mUserId);
                InputLab.getSavings().saveToDatabase(mUserId);
                InputLab.getSocialSecurity().saveToDatabase(mUserId);
                InputLab.getTaxes().saveToDatabase(mUserId);

                return true;

            case R.id.outputs:
                Utils utils = new Utils();

                utils.goToOutputsActivity();
                return true;

            case R.id.help:
                Utils utils2 = new Utils();

                utils2.goToHelpActivity();
                return true;

            case R.id.login:
                Utils utils3 = new Utils();

                utils3.goToLoginActivity();
                return true;

            case R.id.license_cash_flow:
                Utils utils4 = new Utils();

                utils4.goToAssetsLicensesActivity(LoginLab.getCurrentActivity().getApplicationContext(),
                    FileReadActivity.LICENSE_DIRECTORY, "cashflow.txt");
                return true;

            case R.id.license_android_plot:
                Utils utils5 = new Utils();

                utils5.goToAssetsLicensesActivity(LoginLab.getCurrentActivity().getApplicationContext(),
                        FileReadActivity.LICENSE_DIRECTORY, "androidplot.txt");
                return true;

            case R.id.license_apache_v2:
                Utils utils6 = new Utils();

                utils6.goToAssetsLicensesActivity(LoginLab.getCurrentActivity().getApplicationContext(),
                        FileReadActivity.LICENSE_DIRECTORY, "LICENSE-2_0.txt");
                return true;

            case R.id.account_401k_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getAccount401k().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.account_403b_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getAccount403b().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.brokerage_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getBrokerage().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.account_cash_balance_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getCashBalance().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.deductions_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getDeductions().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.expenses_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getExpenses().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.account_roth_ira_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getRothIra().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.account_traditional_ira_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getTraditionalIra().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.pension_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getPension().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.personal_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getPersonal().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.salary_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getSalary().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.savings_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getSavings().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.social_security_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getSocialSecurity().getName(context));
      //          mJumpSelected = true;
                return true;

            case R.id.taxes_menu_item:
                InputPagerActivity.setCurrentItem(InputLab.getTaxes().getName(context));
      //          mJumpSelected = true;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

        if (mTitle.equals(InputLab.getAccount401k().getName(context))) {
            v = InputLab.getAccount401k().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getAccount403b().getName(context))) {
            v = InputLab.getAccount403b().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getBrokerage().getName(context))) {
            v = InputLab.getBrokerage().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getCashBalance().getName(context))) {
            v = InputLab.getCashBalance().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getDeductions().getName(context))) {
            v = InputLab.getDeductions().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getExpenses().getName(context))) {
            v = InputLab.getExpenses().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getRothIra().getName(context))) {
            v = InputLab.getRothIra().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getTraditionalIra().getName(context))) {
            v = InputLab.getTraditionalIra().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getPension().getName(context))) {
            v = InputLab.getPension().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getPersonal().getName(context))) {
            v = InputLab.getPersonal().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getSalary().getName(context))) {
            v = InputLab.getSalary().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getSavings().getName(context))) {
            v = InputLab.getSavings().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getSocialSecurity().getName(context))) {
            v = InputLab.getSocialSecurity().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }
        else
        if (mTitle.equals(InputLab.getTaxes().getName(context))) {
            v = InputLab.getTaxes().createView(context, LoginLab.getCurrentActivity().getSupportFragmentManager(), container);
        }

        return v;
    }
/*
    @Override
    public void onDestroyView() {
        System.out.println(TAG + "onDestroyView()");

        super.onDestroyView();
        if (v != null) {
            ViewGroup parentViewGroup = (ViewGroup) v.getParent();
            if (parentViewGroup != null) {
                System.out.println(TAG + "   removeAllViews()");
                parentViewGroup.removeAllViews();
            }
        }
    }
*/
    /*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "****************************** HAPPENING NOW");
        ValidateInputs validateInputs = new ValidateInputs(context);
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            mJumpSelected = false;
            mJumpIndex = InputPagerActivity.mViewPager.getLeft();
            if (mJumpIndex > 0) {
                mJumpIndex -= 1;
            }
            Log.d(TAG, "Setting mJumpIndex: " + mJumpIndex);
            switch (mJumpIndex) {
                case 0: validateInputs.validate401k(null, null, null, null, null);
                    break;
                case 1: validateInputs.validate403b(null, null, null, null, null);
                    break;
            }

        }
        else{
            // fragment is no longer visible
        }
    }
*/
}
