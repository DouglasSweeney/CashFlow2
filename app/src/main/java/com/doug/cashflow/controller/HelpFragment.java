package com.doug.cashflow.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.Input;

public class HelpFragment extends Fragment {

    private static final String TAG = "HelpFragment";

    private static final String ARG_TITLE = "title";
    private static final String ARG_USER_ID = "user_id";

    private static String  mTitle;
    private static Integer mUserId;
    private static Input   mInput;
    private static Context mContext;

    private static Integer callNumber = 0;

    public static HelpFragment newInstance(String title, Context context) {
        Log.d(TAG, "newInstance(String title)");
//        callNumber++;
//        Log.d(TAG, "    callNumber: " + callNumber);
        Log.d(TAG, "    title: " + title);

        mContext = context;
        mTitle = title;

        Bundle args = new Bundle();
        args.putSerializable(ARG_TITLE, title);
//      args.putSerializable(ARG_USER_ID, userId);

        HelpFragment fragment = new HelpFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle savedInstanceState)");

        setRetainInstance(true);
        setHasOptionsMenu(true);
        mTitle = (String) getArguments().getSerializable(ARG_TITLE);
//        mUserId = (Integer) getArguments().getSerializable(ARG_USER_ID);
        mInput = HelpLab.getInstance(this.getContext()).getInput(mTitle);

//        InputLab.setUserId(mUserId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.help_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
        Log.d(TAG, "    item: " + item.toString());
        Log.d(TAG, "    item.getItemId(): " + item.getItemId());
        Log.d(TAG, "    R.id.help_account_401k_menu_item: " + R.id.help_account_401k_menu_item);
        Log.d(TAG, "    R.id.help_account_403b_menu_item: " + R.id.help_account_403b_menu_item);
//        Log.d(TAG, "    R.id.output_account_403b_menu_item: " + R.id.output_account_403b_menu_item);

        switch (item.getItemId()) {
            case R.id.inputs:
                Utils utils = new Utils();

                utils.goToInputsActivity();
                return true;

            case R.id.outputs:
                Utils utils2 = new Utils();

                utils2.goToOutputsActivity();
                return true;

            case R.id.login:
                Utils utils3 = new Utils();

                utils3.goToLoginActivity();
                return true;


            case R.id.help_account_401k_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getAccount401k().getName());
                return true;

            case R.id.help_account_403b_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getAccount403b().getName());
                return true;

            case R.id.help_account_cash_balance_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getCashBalance().getName());
                return true;

            case R.id.help_account_traditional_ira_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getTraditionalIra().getName());
                return true;

            case R.id.help_account_roth_ira_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getRothIra().getName());
                return true;

            case R.id.help_pension_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getPension().getName());
                return true;

            case R.id.help_social_security_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getSocialSecurity().getName());
                return true;

            case R.id.help_deductions_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getDeductions().getName());
                return true;

            case R.id.help_taxes_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getTaxes().getName());
                return true;

            case R.id.help_brokerage_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getBrokerage().getName());
                return true;

            case R.id.help_expenses_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getExpenses().getName());
                return true;

            case R.id.help_salary_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getSalary().getName());
                return true;

            case R.id.help_personal_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getPersonal().getName());
                return true;

            case R.id.help_savings_menu_item:
                HelpPagerActivity.setCurrentItem(HelpLab.getSavings().getName());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");
        Log.d(TAG, "   mTitle: " + mTitle);

        View v = null;

        if (mTitle.equals(HelpLab.getAccount401k().getName())) {
            v = HelpLab.getAccount401k().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getAccount403b().getName())) {
            v = HelpLab.getAccount403b().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getCashBalance().getName())) {
            v = HelpLab.getCashBalance().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getTraditionalIra().getName())) {
            v = HelpLab.getTraditionalIra().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getRothIra().getName())) {
            v = HelpLab.getRothIra().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getExpenses().getName())) {
            v = HelpLab.getExpenses().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getTaxes().getName())) {
            v = HelpLab.getTaxes().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getSocialSecurity().getName())) {
            v = HelpLab.getSocialSecurity().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getBrokerage().getName())) {
            v = HelpLab.getBrokerage().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getSalary().getName())) {
            v = HelpLab.getSalary().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getDeductions().getName())) {
            v = HelpLab.getDeductions().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getPension().getName())) {
            v = HelpLab.getPension().createView(getContext(), mTitle);
        }
        else
        if (mTitle.equals(HelpLab.getSavings().getName())) {
            v = HelpLab.getSavings().createView(getContext(), mTitle);
        }
        else
            v = HelpLab.getPersonal().createView(getContext(), mTitle);
        return v;
    }
}
