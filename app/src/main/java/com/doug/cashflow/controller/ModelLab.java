package com.doug.cashflow.controller;

import android.content.Context;

import com.doug.cashflow.model.accounts.Account401K;
import com.doug.cashflow.model.accounts.Account403B;
import com.doug.cashflow.model.accounts.AccountCashBalance;
import com.doug.cashflow.model.accounts.AccountIra;
import com.doug.cashflow.model.accounts.AccountRoth;
import com.doug.cashflow.model.accounts.Brokerage;
import com.doug.cashflow.model.accounts.Deductions;
import com.doug.cashflow.model.accounts.Expenses;
import com.doug.cashflow.model.accounts.Pension;
import com.doug.cashflow.model.accounts.Salary;
import com.doug.cashflow.model.accounts.Savings;
import com.doug.cashflow.model.accounts.SocialSecurity;
import com.doug.cashflow.model.accounts.Taxes;
import com.doug.cashflow.model.db.User;


public class ModelLab {
    private static final String TAG = "ModelLab";

    private static ModelLab sMainLab;
    private static Integer          mUserId = 0;
    private static User user;

    private static Account401K        mAccount401k;
    private static Account403B        mAccount403b;
    private static AccountCashBalance mCashBalance;
    private static AccountIra         mTraditionalIra;
    private static AccountRoth        mRothIra;
    private static Expenses           mExpenses;
    private static Taxes              mTaxes;
    private static SocialSecurity     mSocialSecurity;
    private static Brokerage          mBrokerage;
    private static Salary             mSalary;
    private static Deductions         mDeductions;
    private static Pension            mPension;
    private static Savings            mSavings;

    public static ModelLab getInstance(Context context) {
//        if (sMainLab == null) {
            sMainLab = new ModelLab(context);
//        }

        return sMainLab;
    }

    private ModelLab(Context context) {

        // TODO: modify the following lines when user is input
//        user=LoginLab.getDb().getUser("@");
//        if (user != null) {
//            mUserId=user.getId();
//        }

        Integer personalSimulationYear = InputLab.getPersonal().getSimulationYear();
        Integer personalCurrentAge = InputLab.getPersonal().getCurrentAge();
        Integer personalRetirementAge = InputLab.getPersonal().getRetirementAge();
        Integer personalLifeExpectancyAge = InputLab.getPersonal().getLifeExpectancyAge();
        Float   personalInflation = InputLab.getPersonal().getInflation()/100;

        Float   account401kBalance = InputLab.getAccount401k().getBalance();
        Float   account401kGrowthRate = InputLab.getAccount401k().getGrowthRate()/100;
        Float   account401kContributions = InputLab.getAccount401k().getContributions();
        Integer account401kStartWithdrawalsAge = InputLab.getAccount401k().getStartWithdrawalsAge();
        Integer account401kNumberOfWithdrawals = InputLab.getAccount401k().getNumberOfWithdrawals();

        Float   account403bBalance = InputLab.getAccount403b().getBalance();
        Float   account403bGrowthRate = InputLab.getAccount403b().getGrowthRate()/100;
        Float   account403bContributions = InputLab.getAccount403b().getContributions();
        Integer account403bStartWithdrawalsAge = InputLab.getAccount403b().getStartWithdrawalsAge();
        Integer account403bNumberOfWithdrawals = InputLab.getAccount403b().getNumberOfWithdrawals();

        Float   brokerageBalance = InputLab.getBrokerage().getBalance();
        Float   brokerageGrowthRate = InputLab.getBrokerage().getGrowthRate()/100;

        Float   cashBalanceBalance = InputLab.getCashBalance().getBalance();
        Float   cashBalanceGrowthRate = InputLab.getCashBalance().getGrowthRate()/100;
        Float   cashBalanceContributions = InputLab.getCashBalance().getContributions();
        Integer cashBalanceStartWithdrawalsAge = InputLab.getCashBalance().getStartWithdrawalsAge();
        Integer cashBalanceNumberOfWithdrawals = InputLab.getCashBalance().getNumberOfWithdrawals();

        Float   deductionsDeduction = InputLab.getDeductions().getDeductions();

        Float   expensesAnnualExpenses = InputLab.getExpenses().getAnnualExpenses();

        Float   iraRothBalance = InputLab.getRothIra().getBalance();
        Float   iraRothGrowthRate = InputLab.getRothIra().getGrowthRate()/100;
        Float   iraRothContributions = InputLab.getRothIra().getContributions();
        Integer iraRothStartWithdrawalsAge = InputLab.getRothIra().getStartWithdrawalsAge();
        Integer iraRothNumberOfWithdrawals = InputLab.getRothIra().getNumberOfWithdrawals();

        Float   iraTraditionalBalance = InputLab.getTraditionalIra().getBalance();
        Float   iraTraditionalGrowthRate = InputLab.getTraditionalIra().getGrowthRate()/100;
        Float   iraTraditionalContributions = InputLab.getTraditionalIra().getContributions();
        Integer iraTraditionalStartWithdrawalsAge = InputLab.getTraditionalIra().getStartWithdrawalsAge();
        Integer iraTraditionalNumberOfWithdrawals = InputLab.getTraditionalIra().getNumberOfWithdrawals();

        Integer pensionStartingAge = InputLab.getPension().getStartingAge();
        Float   pensionMonthlyAmount = InputLab.getPension().getMonthlyAmount();
        boolean pensionInflationAdjusted = InputLab.getPension().getInflationAdjusted();

        Float   salarySalary = InputLab.getSalary().getSalary();
        Float   salaryMeritIncrease = InputLab.getSalary().getMeritIncrease()/100;

        Float   savingsBalance = InputLab.getSavings().getBalance();
        Float   savingsGrowthRate = InputLab.getSavings().getGrowthRate()/100;

        Integer socialSecurityStartingAge = InputLab.getSocialSecurity().getStartingAge();
        Float   socialSecurityMonthlyAmount = InputLab.getSocialSecurity().getMonthlyAmount();

        Float   taxesFederalTaxRate = InputLab.getTaxes().getFederalTaxRate()/100;
        Float   taxesStateTaxRate = InputLab.getTaxes().getStateTaxRate()/100;

        mAccount401k = new Account401K(
                account401kBalance, account401kGrowthRate, account401kContributions, personalCurrentAge,
                personalSimulationYear, personalRetirementAge, personalLifeExpectancyAge, account401kStartWithdrawalsAge,
                account401kNumberOfWithdrawals);

        mAccount403b = new Account403B(
                account403bBalance, account403bGrowthRate, account403bContributions, personalCurrentAge,
                personalSimulationYear, personalRetirementAge, personalLifeExpectancyAge, account403bStartWithdrawalsAge,
                account403bNumberOfWithdrawals);

        mBrokerage = new Brokerage(
                brokerageBalance, brokerageGrowthRate, personalCurrentAge,
                personalSimulationYear, personalLifeExpectancyAge);

        mCashBalance = new AccountCashBalance(
                cashBalanceBalance, cashBalanceGrowthRate, cashBalanceContributions, personalCurrentAge,
                personalSimulationYear, personalRetirementAge, personalLifeExpectancyAge, cashBalanceStartWithdrawalsAge,
                cashBalanceNumberOfWithdrawals);

        mDeductions = new Deductions(
                deductionsDeduction, personalInflation, personalCurrentAge, personalSimulationYear, personalLifeExpectancyAge);

        mExpenses = new Expenses(expensesAnnualExpenses, personalInflation, personalCurrentAge,
                personalSimulationYear, personalLifeExpectancyAge);

        mRothIra = new AccountRoth(
                iraRothBalance, iraRothGrowthRate, iraRothContributions, personalCurrentAge,
                personalSimulationYear, personalRetirementAge, personalLifeExpectancyAge, iraRothStartWithdrawalsAge,
                iraRothNumberOfWithdrawals);

        mTraditionalIra = new AccountIra(
                iraTraditionalBalance, iraTraditionalGrowthRate, iraTraditionalContributions, personalCurrentAge,
                personalSimulationYear, personalRetirementAge, personalLifeExpectancyAge, iraTraditionalStartWithdrawalsAge,
                iraTraditionalNumberOfWithdrawals);

        mPension = new Pension(pensionMonthlyAmount, pensionStartingAge, pensionInflationAdjusted, personalInflation,
                personalCurrentAge, personalSimulationYear, personalLifeExpectancyAge);

        mSalary = new Salary(salarySalary, salaryMeritIncrease, personalCurrentAge, personalSimulationYear,
                personalLifeExpectancyAge, personalRetirementAge);

        mSavings = new Savings(
                savingsBalance, savingsGrowthRate, personalCurrentAge,
                personalSimulationYear, personalLifeExpectancyAge);

        mSocialSecurity = new SocialSecurity(socialSecurityMonthlyAmount, socialSecurityStartingAge, personalInflation,
                personalCurrentAge, personalSimulationYear, personalRetirementAge, personalLifeExpectancyAge);

        mTaxes = new Taxes(personalCurrentAge, personalSimulationYear, personalLifeExpectancyAge,
                taxesFederalTaxRate, taxesStateTaxRate);
    }

    public static Account401K    getAccount401k()    { return mAccount401k; }
    public static void           setAccount401k(Account401K account401k) {
        mAccount401k = account401k;
    }

    public static Account403B    getAccount403b()    { return mAccount403b; }
    public static void           setAccount403b(Account403B account403b) {
        mAccount403b = account403b;
    }

    public static AccountCashBalance    getCashBalance()    { return mCashBalance; }
    public static void           setCashBalance(AccountCashBalance cashBalance) {
        mCashBalance = cashBalance;
    }

    public static AccountIra getIraTraditional() { return mTraditionalIra; }
    public static void           setIraTraditional(AccountIra iraTraditional) {
        mTraditionalIra = iraTraditional;
    }

    public static AccountRoth    getIraRoth()        { return mRothIra; }
    public static void           setIraRoth(AccountRoth iraRoth) {
        mRothIra = iraRoth;
    }

    public static Expenses       getExpenses()       {
        return mExpenses;
    }
    public static void           setExpenses(Expenses expenses) {
        mExpenses = expenses;
    }

    public static Taxes          getTaxes()          { return mTaxes; }
    public static void           setTaxes(Taxes taxes) {
        mTaxes = taxes;
    }

    public static SocialSecurity getSocialSecurity() { return mSocialSecurity; }
    public static void           setSocialSecurity(SocialSecurity socialSecurity) { mSocialSecurity = socialSecurity; }

    public static Brokerage      getBrokerage()      { return mBrokerage; }
    public static void           setBrokerage(Brokerage brokerage) {
        mBrokerage = brokerage;
    }

    public static Salary         getSalary()         { return mSalary; }
    public static void           setSalary(Salary salary) {
        mSalary = salary;
    }

    public static Savings         getSavings()         { return mSavings; }
    public static void           setSavings(Savings  savings) {
        mSavings = savings;
    }

    public static Deductions     getDeductions()     { return mDeductions; }
    public static void           setDeductions(Deductions deductions) {
        mDeductions = deductions;
    }

    public static Pension        getPension()        { return mPension; }
    public static void           setPension(Pension pension) {
        mPension = pension;
    }

    // TODO: Uncomment the following 2 lines to get the user id
//    public static void           setUserId(Integer userId) { mUserId = userId; }
//    public static Integer        getUserId() { return mUserId; }
}
