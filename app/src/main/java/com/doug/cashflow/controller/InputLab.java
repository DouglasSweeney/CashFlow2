package com.doug.cashflow.controller;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ViewGroup;

import com.doug.cashflow.R;
import com.doug.cashflow.model.db.CashFlowDb;
import com.doug.cashflow.model.system.Input;
import com.doug.cashflow.view.inputs.Account401K;
import com.doug.cashflow.view.inputs.Account403B;
import com.doug.cashflow.view.inputs.Brokerage;
import com.doug.cashflow.view.inputs.CashBalance;
import com.doug.cashflow.view.inputs.Deductions;
import com.doug.cashflow.view.inputs.Expenses;
import com.doug.cashflow.view.inputs.IraRoth;
import com.doug.cashflow.view.inputs.IraTraditional;
import com.doug.cashflow.view.inputs.Pension;
import com.doug.cashflow.view.inputs.Personal;
import com.doug.cashflow.view.inputs.Salary;
import com.doug.cashflow.view.inputs.Savings;
import com.doug.cashflow.view.inputs.SocialSecurity;
import com.doug.cashflow.view.inputs.Taxes;

import java.util.ArrayList;
import java.util.List;

public class InputLab {
    private static final String TAG = "InputLab";

    private static Context context;
    private static FragmentManager fragmentManager;

    private static InputLab sInputLab;
    private static List<Input>      mInputs = new ArrayList<>();
    private static Integer          mUserId;

    private static Account401K account401k;
    private static Account403B account403b;
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

    public static InputLab getInstance(Context context, FragmentManager fragmentManager, ViewGroup parent) {
         if (sInputLab == null) {
             sInputLab = new InputLab(context, fragmentManager, parent);
         }
         return sInputLab;
    }

    private InputLab(Context context, FragmentManager fragmentManager, ViewGroup parent) {

         this.context = context;
         this.fragmentManager = fragmentManager;

         mInputs.clear();

         Input input = new Input();
         input.setHeader(context.getString(R.string.input_header_401k)); // 401K
         mInputs.add(input);

         Input input2 = new Input();
         input2.setHeader(context.getString(R.string.input_header_403b)); // 403B
         mInputs.add(input2);

         Input input5 = new Input();
         input5.setHeader(context.getString(R.string.input_header_brokerage)); // Brokerage
         mInputs.add(input5);

         Input input10 = new Input();
         input10.setHeader(context.getString(R.string.input_header_cash_balance)); // Cash Balance
         mInputs.add(input10);

         Input input11 = new Input();
         input11.setHeader(context.getString(R.string.input_header_deductions)); // Deductions
         mInputs.add(input11);

         Input input12 = new Input();
         input12.setHeader(context.getString(R.string.input_header_expenses)); // Expenses
         mInputs.add(input12);

         Input input15 = new Input();
         input15.setHeader(context.getString(R.string.input_header_ira_roth)); // IRA (Roth)
         mInputs.add(input15);

         Input input20 = new Input();
         input20.setHeader(context.getString(R.string.input_header_ira_traditional)); // IRA (Traditional)
         mInputs.add(input20);

         Input input25 = new Input();
         input25.setHeader(context.getString(R.string.input_header_pension)); // Pension
         mInputs.add(input25);

         Input input27 = new Input();
         input27.setHeader(context.getString(R.string.input_header_personal)); // Personal
         mInputs.add(input27);

         Input input30 = new Input();
         input30.setHeader(context.getString(R.string.input_header_salary)); // Salary
         mInputs.add(input30);

         Input input32 = new Input();
         input32.setHeader(context.getString(R.string.input_header_savings)); // Saving
         mInputs.add(input32);

         Input input35 = new Input();
         input35.setHeader(context.getString(R.string.input_header_social_security)); // Social Security
         mInputs.add(input35);

         Input input40 = new Input();
         input40.setHeader(context.getString(R.string.input_header_taxes)); // Taxes
         mInputs.add(input40);

         account401k = new Account401K(getPageIndex(input.getHeader()), context, fragmentManager, parent);
         account403b = new Account403B(getPageIndex(input2.getHeader()), context, fragmentManager, parent);
         brokerage = new Brokerage(getPageIndex(input5.getHeader()), context, fragmentManager, parent);
         cashBalance = new CashBalance(getPageIndex(input10.getHeader()), context, fragmentManager, parent);
         deductions = new Deductions(getPageIndex(input11.getHeader()), context, fragmentManager, parent);
         expenses = new Expenses(getPageIndex(input12.getHeader()), context, fragmentManager, parent);
         rothIra = new IraRoth(getPageIndex(input15.getHeader()), context, fragmentManager, parent);
         traditionalIra = new IraTraditional(getPageIndex(input20.getHeader()), context, fragmentManager, parent);
         pension = new Pension(getPageIndex(input25.getHeader()), context, fragmentManager, parent);
         salary = new Salary(getPageIndex(input30.getHeader()), context, fragmentManager, parent);
         savings = new Savings(getPageIndex(input32.getHeader()), context, fragmentManager, parent);
         socialSecurity = new SocialSecurity(getPageIndex(input35.getHeader()), context, fragmentManager, parent);
         taxes = new Taxes(getPageIndex(input40.getHeader()), context, fragmentManager, parent);

         personal = new Personal();
         if (LoginLab.getDb() != null) {
             personal.buildView(getPageIndex(input27.getHeader()), context, fragmentManager, parent);
         }

    }

    public static FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public static List<Input> getInputs() {
        return mInputs;
    }

    public static Input getInput(String title) {
        for (Input input : mInputs) {
            if (input.getHeader().equals(title)) {
                return input;
            }
        }
        return null;
    }

    public int getPageIndex(String title) {
        Input input;

        for (int i=0; i<mInputs.size(); i++) {
            input = mInputs.get(i);
            // TODO: Remove the following line & uncomment the following 3 lines
            return 0;
//            if (input.getHeader().equals(title)) {
//                return i;
//            }
        }
        return -1;
    }

    public static Context        getContext()        { return context;}

    public static Account401K    getAccount401k()    { return account401k; }
    public static Account403B    getAccount403b()    { return account403b; }
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

    public static void           setUserId(Integer userId) { mUserId = userId; }
    public static Integer        getUserId() {
        if (mUserId == null) {
            mUserId = CashFlowDb.MY_2017_doug_USER_ID;
        }

        return mUserId;
    }
}
