package com.doug.cashflow.controller;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.Input;
import com.doug.cashflow.view.inputs.help.Account401k;
import com.doug.cashflow.view.inputs.help.Account403b;
import com.doug.cashflow.view.inputs.help.Brokerage;
import com.doug.cashflow.view.inputs.help.CashBalance;
import com.doug.cashflow.view.inputs.help.Deductions;
import com.doug.cashflow.view.inputs.help.Expenses;
import com.doug.cashflow.view.inputs.help.IraRoth;
import com.doug.cashflow.view.inputs.help.IraTraditional;
import com.doug.cashflow.view.inputs.help.Pension;
import com.doug.cashflow.view.inputs.help.Personal;
import com.doug.cashflow.view.inputs.help.Salary;
import com.doug.cashflow.view.inputs.help.Savings;
import com.doug.cashflow.view.inputs.help.SocialSecurity;
import com.doug.cashflow.view.inputs.help.Taxes;

import java.util.ArrayList;
import java.util.List;

public class HelpLab {
    private static final String TAG = "HelpLab";

    private static HelpLab          sHelpLab;
    private static List<Input>      mInputs = new ArrayList<>();
    private static Integer          mUserId;

    private static Account401k account401k;
    private static Account403b account403b;
    private static CashBalance cashBalance;
    private static IraTraditional traditionalIra;
    private static IraRoth rothIra;
    private static Expenses expenses;
    private static Taxes taxes;
    private static SocialSecurity socialSecurity;
    private static Brokerage brokerage;
    private static Salary salary;
    private static Deductions deductions;
    private static Pension pension;
    private static Personal personal;
    private static Savings savings;

    public static HelpLab getInstance(Context context) {
        if (sHelpLab == null) {
            sHelpLab = new HelpLab(context);
        }
        return sHelpLab;
    }

    private HelpLab(Context context) {
        mInputs.clear();

        Input input = new Input();
        input.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_401k)); // 401K
        mInputs.add(input);

        Input input2 = new Input();
        input2.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_403b)); // 403B
        mInputs.add(input2);

        Input input5 = new Input();
        input5.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_brokerage)); // Brokerage
        mInputs.add(input5);

        Input input10 = new Input();
        input10.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_cash_balance)); // Cash Balance
        mInputs.add(input10);

        Input input11 = new Input();
        input11.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_deductions)); // Deductions
        mInputs.add(input11);

        Input input12 = new Input();
        input12.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_expenses)); // Expenses
        mInputs.add(input12);

        Input input15 = new Input();
        input15.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_ira_roth)); // IRA (Roth)
        mInputs.add(input15);

        Input input20 = new Input();
        input20.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_ira_traditional)); // IRA (Traditional)
        mInputs.add(input20);

        Input input25 = new Input();
        input25.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_pension)); // Pension
        mInputs.add(input25);

        Input input27 = new Input();
        input27.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_personal)); // Personal
        mInputs.add(input27);

        Input input30 = new Input();
        input30.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_salary)); // Salary
        mInputs.add(input30);

        Input input32 = new Input();
        input32.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_savings)); // Saving
        mInputs.add(input32);

        Input input35 = new Input();
        input35.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_social_security)); // Social Security
        mInputs.add(input35);

        Input input40 = new Input();
        input40.setHeader(LoginLab.getCurrentActivity().getResources().getString(R.string.help_input_header_taxes)); // Taxes
        mInputs.add(input40);

        account401k = new Account401k(context, R.string.help_input_header_401k, R.string.help_input_header_401k_text);
        account403b = new Account403b(context, R.string.help_input_header_403b, R.string.help_input_header_403b_text);
        cashBalance = new CashBalance(context, R.string.help_input_header_cash_balance, R.string.help_input_header_cash_balance_text);
        traditionalIra = new IraTraditional(context, R.string.help_input_header_ira_traditional, R.string.help_input_header_ira_traditional_text);
        rothIra = new IraRoth(context, R.string.help_input_header_ira_roth, R.string.help_input_header_ira_roth_text);
        pension = new Pension(context, R.string.help_input_header_pension, R.string.help_input_header_pension_text);
        socialSecurity = new SocialSecurity(context, R.string.help_input_header_social_security, R.string.help_input_header_social_security_text);
        deductions = new Deductions(context, R.string.help_input_header_deductions, R.string.help_input_header_deductions_text);
        brokerage = new Brokerage(context, R.string.help_input_header_brokerage, R.string.help_input_header_brokerage_text);
        taxes = new Taxes(context, R.string.help_input_header_taxes, R.string.help_input_header_taxes_text);
        salary = new Salary(context, R.string.help_input_header_salary, R.string.help_input_header_salary_text);
        expenses = new Expenses(context, R.string.help_input_header_expenses, R.string.help_input_header_expenses_text);
        savings = new Savings(context, R.string.help_input_header_savings, R.string.help_input_header_savings_text);
        personal = new Personal(context, R.string.help_input_header_personal, R.string.help_input_header_personal_text);
    }

    public static List<Input> getInputs() {
        return mInputs;
    }

    public Input getInput(String title) {
        for (Input input : mInputs) {
            if (input.getHeader().equals(title)) {
                return input;
            }
        }
        return null;
    }

    public static Account401k    getAccount401k()    { return account401k; }
    public static Account403b    getAccount403b()    { return account403b; }
    public static CashBalance    getCashBalance()    { return cashBalance; }
    public static IraTraditional getTraditionalIra() { return traditionalIra; }
    public static IraRoth        getRothIra()        { return rothIra; }
    public static Expenses       getExpenses()       { return expenses; }
    public static Taxes          getTaxes()          { return taxes; }
    public static SocialSecurity getSocialSecurity() { return socialSecurity; }
    public static Brokerage      getBrokerage()      { return brokerage; }
    public static Salary         getSalary()         { return salary; }
    public static Deductions     getDeductions()     { return deductions; }
    public static Pension        getPension()        { return pension; }
    public static Personal       getPersonal()       { return personal; }
    public static Savings        getSavings()        { return savings; }
}
