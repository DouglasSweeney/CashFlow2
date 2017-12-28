package com.doug.cashflow.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.Input;

/**
 * Created by Doug on 5/7/2017.
 */

public class OutputFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String TAG = "OutputFragment";

    private String       mTitle;
    private Input mInput;

    private static Integer callNumber = 0;

    public static OutputFragment newInstance(String title) {
//        Log.d(TAG, "newInstance(String title)");
//        callNumber++;
//        Log.d(TAG, "    callNumber: " + callNumber);
//        Log.d(TAG, "    title: " + title);

        Bundle args = new Bundle();
        args.putSerializable(ARG_TITLE, title);

        OutputFragment fragment = new OutputFragment();
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
        mInput = InputLab.getInstance(this.getContext(), getFragmentManager(), null).getInput(mTitle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.output_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
//        Log.d(TAG, "    item: " + item.toString());
//        Log.d(TAG, "    item.getItemId(): " + item.getItemId());
//        Log.d(TAG, "    item: " + item.toString());

        switch (item.getItemId()) {
            case R.id.inputs:
                Utils utils = new Utils();

                utils.goToInputsActivity();
                return true;

            case R.id.login:
                Utils utils2 = new Utils();

                utils2.goToLoginActivity();
                return true;


            case R.id.output_account_401k_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getAccount401k().getName());
                return true;

            case R.id.output_account_403b_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getAccount403b().getName());
                return true;

            case R.id.output_account_cash_balance_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getCashBalance().getName());
                return true;

            case R.id.output_income_graph_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getIncomeGraph().getName());
                return true;

            case R.id.output_account_traditional_ira_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getIraTraditional().getName());
                return true;

            case R.id.output_account_roth_ira_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getIraRoth().getName());
                return true;

            case R.id.output_pension_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getPension().getName());
                return true;

            case R.id.output_social_security_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getSocialSecurity().getName());
                return true;

            case R.id.output_deductions_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getDeductions().getName());
                return true;

            case R.id.output_taxes_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getTaxes().getName());
                return true;

            case R.id.output_brokerage_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getBrokerage().getName());
                return true;

            case R.id.output_expenses_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getExpenses().getName());
                return true;

            case R.id.output_salary_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getSalary().getName());
                return true;

            case R.id.output_savings_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getSavings().getName());
                return true;

            case R.id.output_savings_graph_menu_item:
                OutputPagerActivity.setCurrentItem(OutputLab.getSavingsGraph().getName());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        Log.d(TAG, "onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

        View v = null;

        if (mTitle.equals(OutputLab.getAccount401k().getName())) {
            v = OutputLab.getAccount401k().getView();
        }
        else
        if (mTitle.equals(OutputLab.getAccount403b().getName())) {
            v = OutputLab.getAccount403b().getView();
        }
        else
        if (mTitle.equals(OutputLab.getBrokerage().getName())) {
            v = OutputLab.getBrokerage().getView();
        }
        else
        if (mTitle.equals(OutputLab.getCashBalance().getName())) {
            v = OutputLab.getCashBalance().getView();
        }
        else
        if (mTitle.equals(OutputLab.getDeductions().getName())) {
            v = OutputLab.getDeductions().getView();
        }
        else
        if (mTitle.equals(OutputLab.getExpenses().getName())) {
            v = OutputLab.getExpenses().getView();
        }
        else
        if (mTitle.equals(OutputLab.getIncomeGraph().getName())) {
            v = OutputLab.getIncomeGraph().getView();
        }
        else
        if (mTitle.equals(OutputLab.getIraRoth().getName())) {
            v = OutputLab.getIraRoth().getView();
        }
        else
        if (mTitle.equals(OutputLab.getIraTraditional().getName())) {
           v = OutputLab.getIraTraditional().getView();
        }
        else
        if (mTitle.equals(OutputLab.getPension().getName())) {
           v = OutputLab.getPension().getView();
        }
        else
        if (mTitle.equals(OutputLab.getSalary().getName())) {
            v = OutputLab.getSalary().getView();
        }
        else
        if (mTitle.equals(OutputLab.getSavings().getName())) {
            v = OutputLab.getSavings().getView();
        }
        else
        if (mTitle.equals(OutputLab.getSavingsGraph().getName())) {
            v = OutputLab.getSavingsGraph().getView();
        }
        else
        if (mTitle.equals(OutputLab.getSocialSecurity().getName())) {
            v = OutputLab.getSocialSecurity().getView();
        }
        else
        if (mTitle.equals(OutputLab.getTaxes().getName())) {
             v = OutputLab.getTaxes().getView();
            }

        return v;
    }
}
