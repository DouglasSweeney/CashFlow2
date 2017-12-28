package com.doug.cashflow.controller;

import android.content.Context;
import android.util.Log;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.Input;
import com.doug.cashflow.model.system.ResultsDataNode;
import com.doug.cashflow.view.outputs.Account401k;
import com.doug.cashflow.view.outputs.Account403b;
import com.doug.cashflow.view.outputs.Brokerage;
import com.doug.cashflow.view.outputs.CashBalance;
import com.doug.cashflow.view.outputs.Deductions;
import com.doug.cashflow.view.outputs.Expenses;
import com.doug.cashflow.view.outputs.IncomeGraph;
import com.doug.cashflow.view.outputs.IraRoth;
import com.doug.cashflow.view.outputs.IraTraditional;
import com.doug.cashflow.view.outputs.Pension;
import com.doug.cashflow.view.outputs.Salary;
import com.doug.cashflow.view.outputs.Savings;
import com.doug.cashflow.view.outputs.SavingsGraph;
import com.doug.cashflow.view.outputs.SocialSecurity;
import com.doug.cashflow.view.outputs.Taxes;

import java.util.ArrayList;
import java.util.List;

public class OutputLab {
    private static final String TAG = "OutputLab";
    private static final Integer GRAPH_DIVISOR = 1000;

    private static OutputLab        sOutputLab;
    private static Number           mAges[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mExpenseValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mAccount401kValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mAccount403bValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mBrokerageValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mCashBalanceValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mIraRothValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mIraTraditionalValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mPensionValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mSalaryValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mSavingsValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mSocialSecurityValues[] = new Number[ModelLab.getExpenses().getSize()];
    private static Number           mSavings[] = new Number[ModelLab.getExpenses().getSize()];
    private static ArrayList<Input> mOutputs = new ArrayList<>();

    private static Account401k    account401k;
    private static Account403b    account403b;
    private static Brokerage      brokerage;
    private static CashBalance    cashBalance;
    private static Deductions     deductions;
    private static IncomeGraph    incomeGraph;
    private static IraRoth        iraRoth;
    private static IraTraditional iraTraditional;
    private static Expenses       expenses;
    private static Pension        pension;
    private static Salary         salary;
    private static SocialSecurity socialSecurity;
    private static Savings        savings;
    private static SavingsGraph   savingsGraph;
    private static Taxes          taxes;

    public static OutputLab getInstance(Context context) {
//        if (sOutputLab == null) {
            sOutputLab = new OutputLab(context);
//        }

        return sOutputLab;
    }

    private OutputLab(Context context) {
        mOutputs.clear();

        Input input = new Input();
        input.setHeader(context.getString(R.string.output_header_401k)); // 401K
        mOutputs.add(input);

        Input input2 = new Input();
        input2.setHeader(context.getString(R.string.output_header_403b)); // 403B
        mOutputs.add(input2);

        Input input5 = new Input();
        input5.setHeader(context.getString(R.string.output_header_brokerage)); // Brokerage
        mOutputs.add(input5);

        Input input10 = new Input();
        input10.setHeader(context.getString(R.string.output_header_cash_balance)); // Cash Balance
        mOutputs.add(input10);

        Input input11 = new Input();
        input11.setHeader(context.getString(R.string.output_header_deductions)); // Deductions
        mOutputs.add(input11);

        Input input12 = new Input();
        input12.setHeader(context.getString(R.string.output_header_expenses)); // Expenses
        mOutputs.add(input12);

        Input input50 = new Input();
        input50.setHeader(context.getString(R.string.output_header_income_graph)); // Income Graph
        mOutputs.add(input50);

        Input input15 = new Input();
        input15.setHeader(context.getString(R.string.output_header_ira_roth)); // IRA (Roth)
        mOutputs.add(input15);

        Input input20 = new Input();
        input20.setHeader(context.getString(R.string.output_header_ira_traditional)); // IRA (Traditional)
        mOutputs.add(input20);

        Input input25 = new Input();
        input25.setHeader(context.getString(R.string.output_header_pension)); // Pension
        mOutputs.add(input25);

        Input input30 = new Input();
        input30.setHeader(context.getString(R.string.output_header_salary)); // Salary
        mOutputs.add(input30);

        Input input32 = new Input();
        input32.setHeader(context.getString(R.string.output_header_savings)); // Savings
        mOutputs.add(input32);

        Input input33 = new Input();
        input33.setHeader(context.getString(R.string.output_header_savings_graph)); // Savings Graph
        mOutputs.add(input33);

        Input input35 = new Input();
        input35.setHeader(context.getString(R.string.output_header_social_security)); // Social Security
        mOutputs.add(input35);

        Input input40 = new Input();
        input40.setHeader(context.getString(R.string.output_header_taxes)); // Taxes
        mOutputs.add(input40);

        account401k =    new Account401k(context, ModelLab.getAccount401k().getListOfValues());
        account403b =    new Account403b(context, ModelLab.getAccount403b().getListOfValues());
        brokerage =      new Brokerage(context, ModelLab.getBrokerage().getListOfValues());
        cashBalance =    new CashBalance(context, ModelLab.getCashBalance().getListOfValues());
        deductions =     new Deductions(context, ModelLab.getDeductions().getListOfValues());
        expenses =       new Expenses(context, ModelLab.getExpenses().getListOfValues());
        iraRoth =        new IraRoth(context, ModelLab.getIraRoth().getListOfValues());
        iraTraditional = new IraTraditional(context, ModelLab.getIraTraditional().getListOfValues());
        pension =        new Pension(context, ModelLab.getPension().getListOfValues());
        salary =         new Salary(context, ModelLab.getSalary().getListOfValues());
        savings =        new Savings(context, ModelLab.getSavings().getListOfValues());
        socialSecurity = new SocialSecurity(context, ModelLab.getSocialSecurity().getListOfValues());
        taxes =          new Taxes(context, ModelLab.getTaxes().getListOfValues());

        // Get domain labels for income & savings graphs
        ResultsDataNode node;
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            node = ModelLab.getExpenses().get(i);
            mExpenseValues[i] = node.getBeginningValue() / GRAPH_DIVISOR;
            mAges[i] = node.getAge();
        }

        // Get values for the line chart graph
        // 401K
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            node = ModelLab.getAccount401k().get(i);
//            Log.d(TAG, "    Account401K: i= " + i);
//            Log.d(TAG, "    Account401k: node.getWithdrawal()" + node.getWithdrawal());
//            Log.d(TAG, "    Account401k: node.getDeposit()" + node.getDeposit());
            mAccount401kValues[i] = node.getWithdrawal() / GRAPH_DIVISOR;
//            Log.d(TAG, "    Account401k: mAccount401kValues[i]: " + mAccount401kValues[i]);
        }

        // 403B
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            node = ModelLab.getAccount403b().get(i);
            mAccount403bValues[i] = node.getWithdrawal() / GRAPH_DIVISOR;
        }

        // Brokerage
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
//            Log.d(TAG, "    Brokerage: i= " + i);
            node = ModelLab.getBrokerage().get(i);
//            Log.d(TAG, "    Brokerage: node.getWithdrawal()" + node.getWithdrawal());
//            Log.d(TAG, "    Brokerage: node.getDeposit()" + node.getDeposit());
//            Log.d(TAG, "    Brokerage: node.getBeginningValue()" + node.getBeginningValue());
//            Log.d(TAG, "    Brokerage: node.getEndingValue()" + node.getEndingValue());
            mBrokerageValues[i] = node.getWithdrawal() / GRAPH_DIVISOR;
        }

        // Cash Balance
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            node = ModelLab.getCashBalance().get(i);
            mCashBalanceValues[i] = node.getWithdrawal() / GRAPH_DIVISOR;
        }

        // IRA (Roth)
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            node = ModelLab.getIraRoth().get(i);
            mIraRothValues[i] = node.getWithdrawal() / GRAPH_DIVISOR;
        }

        // IRA (Traditional)
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            node = ModelLab.getIraTraditional().get(i);
            mIraTraditionalValues[i] = node.getWithdrawal() / GRAPH_DIVISOR;
        }

        // Pension
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            node = ModelLab.getPension().get(i);
//            Log.d(TAG, "    getPension(): " + node.getPension());
//            Log.d(TAG, "    getBeginningValue(): " + node.getBeginningValue());
//            Log.d(TAG, "    getEndingValue(): " + node.getEndingValue());
            mPensionValues[i] = node.getEndingValue() / GRAPH_DIVISOR;
        }

        // Salary
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            node = ModelLab.getSalary().get(i);
//            Log.d(TAG, "    getSalary(): " + node.getSalary());
//            Log.d(TAG, "    getBeginningValue(): " + node.getBeginningValue());
//            Log.d(TAG, "    getEndingValue(): " + node.getEndingValue());
            mSalaryValues[i] = node.getEndingValue() / GRAPH_DIVISOR;
        }

        // Savings
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            node = ModelLab.getSavings().get(i);
            mSavingsValues[i] = node.getWithdrawal() / GRAPH_DIVISOR;
        }

        // Social Security
         for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
             node = ModelLab.getSocialSecurity().get(i);
             mSocialSecurityValues[i] = node.getEndingValue() / GRAPH_DIVISOR;
         }
         // TODO: Need to uncomment for distribution
        incomeGraph = new IncomeGraph(context, R.string.output_header_income_graph, mAges,
                mExpenseValues, mAccount401kValues, mAccount403bValues, mBrokerageValues, mCashBalanceValues, mIraRothValues, mIraTraditionalValues,
                mPensionValues, mSalaryValues, mSavingsValues, mSocialSecurityValues);

        // Get values for the line chart graph
        for (int i = 0; i < ModelLab.getExpenses().getSize(); i++) {
            // 401K
            node = ModelLab.getAccount401k().get(i);

            mSavings[i] = node.getEndingValue();

            // 403B
            node = ModelLab.getAccount403b().get(i);
            mSavings[i] = (Number)((double)(mSavings[i]) + node.getEndingValue());

            // Brokerage
            node = ModelLab.getBrokerage().get(i);
            mSavings[i] = (Number)((double)(mSavings[i]) + node.getEndingValue());

            // Cash Balance
            node = ModelLab.getCashBalance().get(i);
            mSavings[i] = (Number)((double)(mSavings[i]) + node.getEndingValue());

            // IRA (Roth)
            node = ModelLab.getIraRoth().get(i);
            mSavings[i] = (Number)((double)(mSavings[i]) + node.getEndingValue());

            // IRA (Traditional)
            node = ModelLab.getIraTraditional().get(i);
            mSavings[i] = (Number)((double)(mSavings[i]) + node.getEndingValue());

            // Savings
            node = ModelLab.getSavings().get(i);
            mSavings[i] = (Number)((double)(mSavings[i]) + node.getEndingValue());

            mSavings[i] = (Number)((double)(mSavings[i]) / 1000000.0);
        }

        // TODO: Need to uncomment for distribution
        savingsGraph = new SavingsGraph(context, R.string.output_header_savings_graph, mAges, mSavings);
    }

    public static List<Input> getOutputs() {
        return mOutputs;
    }

    public Input getOutput(String title) {
        for (Input output : mOutputs) {
            if (output.getHeader().equals(title)) {
                return output;
            }
        }
        return null;
    }

    public static Account401k    getAccount401k()    { return account401k; }
    public static Account403b    getAccount403b()    { return account403b; }
    public static Brokerage      getBrokerage()      { return brokerage; }
    public static CashBalance    getCashBalance()    { return cashBalance; }
    public static Deductions     getDeductions()     { return deductions; }
    public static Expenses       getExpenses()       { return expenses; }
    public static IncomeGraph    getIncomeGraph()    { return incomeGraph; }
    public static IraRoth        getIraRoth()        { return iraRoth; }
    public static IraTraditional getIraTraditional() { return iraTraditional; }
    public static Pension        getPension()        { return pension; }
    public static Salary         getSalary()         { return salary; }
    public static Savings        getSavings()        { return savings; }
    public static SavingsGraph   getSavingsGraph()   { return savingsGraph; }
    public static SocialSecurity getSocialSecurity() { return socialSecurity; }
    public static Taxes          getTaxes()          { return taxes; }

    public static void           start() {
    }

    public static void           cleanup() {
    }
}
