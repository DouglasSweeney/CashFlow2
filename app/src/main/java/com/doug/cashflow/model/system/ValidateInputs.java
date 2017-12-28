package com.doug.cashflow.model.system;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.LoginLab;

public class ValidateInputs {
    private static final String TAG = "ValidateInputs";

    public final int        MIN_AGE = 0;
    public final int        MAX_AGE = 150;

    public final int        MIN_SOCIAL_SECURITY_AGE = 62;
    public final int        MAX_SOCIAL_SECURITY_AGE = 70;

    public final double     MIN_INFLATION_RATE = -30.0;
    public final double     MAX_INFLATION_RATE =  30.0;

    public final double     MIN_GROWTH_RATE = -30.0;
    public final double     MAX_GROWTH_RATE =  30.0;

    public final double     MIN_MERIT_INCREASE = -30.0;
    public final double     MAX_MERIT_INCREASE =  30.0;

    public final double     MIN_ANNUAL_CONTRIBUTION = 0.0;
    public final double     MAX_ANNUAL_CONTRIBUTION = 50000.0;

    public final int        MIN_NUMBER_OF_WITHDRAWALS = 0;
    public final int        MAX_NUMBER_OF_WITHDRAWALS = 100;

    public final double     MIN_TAX_RATE = 0.0;
    public final double     MAX_TAX_RATE = 10000.0;

    public final int        MIN_YEAR = 0;

    private String          popupDialogTitle = "Input Error";
    private ToastFlags      toastFlags = new ToastFlags();

    private Context context;

    public ValidateInputs(Context context) {
        this.context = context;
    }

    public boolean validate401k() {
        boolean valid = true;

//        Log.d(TAG, "validate401k ");
        if (!account401kBalance())
            valid = false;

        if (!account401kGrowthRate())
            valid = false;

        if (!account401kAnnualContribution())
            valid = false;

        if (!account401kStartWithdrawalsAge())
            valid = false;

        if (!account401kNumberOfWithdrawals())
            valid = false;

        return valid;
    }

    public boolean validate403b() {
        boolean valid = true;

        if (!account403bBalance())
            valid = false;

        if (!account403bGrowthRate())
            valid = false;

        if (!account403bAnnualContribution())
            valid = false;

        if (!account403bStartWithdrawalsAge())
            valid = false;

        if (!account403bNumberOfWithdrawals())
            valid = false;

        return valid;
    }

    public boolean validateBrokerage() {
        boolean valid = true;

        if (!brokerageBalance())
            valid = false;

        if (!brokerageGrowthRate())
            valid = false;

        return valid;
    }

    public boolean validateCashBalance() {
        boolean valid = true;

        if (!accountCashBalanceBalance())
            valid = false;

        if (!accountCashBalanceGrowthRate())
            valid = false;

        if (!accountCashBalanceAnnualContribution())
            valid = false;

        if (!accountCashBalanceStartWithdrawalsAge())
            valid = false;

        if (!accountCashBalanceNumberOfWithdrawals())
            valid = false;

        return valid;
    }

    public boolean validateDeductions() {
        boolean valid = true;

        if (!deductionsDeductions())
            valid = false;

        return valid;
    }

    public boolean validateExpenses() {
        boolean valid = true;

        if (!expenseExpenses())
            valid = false;

        return valid;
    }

    public boolean validateRoth() {
        boolean valid = true;

        if (!accountRothBalance())
            valid = false;

        if (!accountRothGrowthRate())
            valid = false;

        if (!accountRothAnnualContribution())
            valid = false;

        if (!accountRothStartWithdrawalsAge())
            valid = false;

        if (!accountRothNumberOfWithdrawals())
            valid = false;

        return valid;
    }

    public boolean validateIra() {
        boolean valid = true;

        if (!accountIraBalance())
            valid = false;

        if (!accountIraGrowthRate())
            valid = false;

        if (!accountIraAnnualContribution())
            valid = false;

        if (!accountIraStartWithdrawalsAge())
            valid = false;

        if (!accountIraNumberOfWithdrawals())
            valid = false;

        return valid;
    }

    public boolean validatePension() {
        boolean valid = true;

        if (!pensionStartingAge())
            valid = false;

        if (!pensionMonthlyAmount())
            valid = false;

        return valid;
    }

    public boolean validatePersonal() {
        boolean valid = true;

        if (!personalRetirementAge())
            valid = false;

        if (!personalDeathAge())
            valid = false;

        if (!personalInflationRate())
            valid = false;

        if (!personalCurrentAge())
            valid = false;

        if (!ages())
            valid = false;

        if (!personalCurrentYear())
            valid = false;

        if (!personalBirthYear())
            valid = false;

        return valid;
    }

    public boolean validateSalary() {
        boolean valid = true;

        if (!salarySalary())
            valid = false;

        if (!salaryMeritIncrease())
            valid = false;

        return valid;
    }

    public boolean validateSavings() {
        boolean valid = true;

        if (!savingsBalance())
            valid = false;

        if (!savingsGrowthRate())
            valid = false;

        return valid;
    }

    public boolean validateSocialSecurity() {
        boolean valid = true;

        if (!socialSecurityStartingAge())
            valid = false;

        if (!socialSecurityMonthlyAmount())
            valid = false;

        return valid;
    }

    public boolean validateTaxes() {
        boolean valid = true;

        if (!taxesFederal())
            valid = false;

        if (!taxesState())
            valid = false;

        return valid;
    }

    public boolean validate() {
        boolean valid = true;

        valid = validate401k();
        if (valid) {
            valid = validate403b();
        }

        if (valid) {
            valid = validateBrokerage();
        }

        if (valid) {
            valid = validateCashBalance();
        }

        if (valid) {
            valid = validateDeductions();
        }

        if (valid) {
            valid = validateExpenses();
        }

        if (valid) {
            valid = validateRoth();
        }

        if (valid) {
            valid = validateIra();
        }

        if (valid) {
            valid = validatePension();
        }

        if (valid) {
            valid = validatePersonal();
        }

        if (valid) {
            valid = validateSalary();
        }

        if (valid) {
            valid = validateSavings();
        }

        if (valid) {
            valid = validateSocialSecurity();
        }

        if (valid) {
            valid = validateTaxes();
        }

        return valid;
    }

//    public void showMessageDialog(Object nullObject, final String error, String title) {
//        Toast.makeText(LoginLab.getCurrentActivity(), error, Toast.LENGTH_SHORT).show();
//    }

    public void showMessageDialog(int page, int entry, final String error, String title) {
        if (!toastFlags.isSet(page, entry)) {
            Toast.makeText(LoginLab.getCurrentActivity(), error, Toast.LENGTH_SHORT).show();

            toastFlags.set(page, entry);
        }
    }
    public boolean account401kBalance() {
        // Log.i(TAG, "Calling the ValidateInputs account401kBalance ().");

        double balance = InputLab.getAccount401k().getBalance();
        boolean valid = true;

        if (balance < 0.0) {
            showMessageDialog(InputLab.getAccount401k().getPageIndex(), 0, "401K Balance (" + balance + ") is not positive.",
                        popupDialogTitle);

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs account401kBalance().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean account401kGrowthRate() {
        // Log.i(TAG, "Calling the ValidateInputs account401kGrowthRate ().");

        double growthRate = InputLab.getAccount401k().getGrowthRate();
        boolean valid = true;

        if (growthRate < MIN_GROWTH_RATE || growthRate > MAX_GROWTH_RATE) {
            int page = InputLab.getAccount401k().getPageIndex();
                showMessageDialog(page, 1,
                    "401K Growth Rate (" + growthRate + ") must be between " + MIN_GROWTH_RATE + " and " + MAX_GROWTH_RATE + ".",
                    popupDialogTitle);

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs account401kGrowthRate().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean account401kAnnualContribution() {
        // Log.i(TAG, "Calling the ValidateInputs account401kAnnualContribution ().");

        double contribution = InputLab.getAccount401k().getContributions();
        boolean valid = true;

        if (contribution < MIN_ANNUAL_CONTRIBUTION || contribution > MAX_ANNUAL_CONTRIBUTION) {
            showMessageDialog(InputLab.getAccount401k().getPageIndex(), 2,
                    "401K Annual Contribution (" + contribution + ") must be between " + MIN_ANNUAL_CONTRIBUTION + " and " + MAX_ANNUAL_CONTRIBUTION + ".",
                    popupDialogTitle
                    );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs account401kAnnualContribution().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean account401kStartWithdrawalsAge() {
        // Log.i(TAG, "Calling the ValidateInputs account401kStartRetirementAge ().");

        int startAge = InputLab.getAccount401k().getStartWithdrawalsAge();
        boolean valid = true;

        if (startAge < MIN_AGE || startAge > MAX_AGE) {
            showMessageDialog(InputLab.getAccount401k().getPageIndex(), 3,
                    "401K Start Withdrawals Age (" + startAge + ") must be between " + MIN_AGE + " and " + MAX_AGE + ".",
                    popupDialogTitle
                    );

            valid = false;
        }

        return valid;
    }

    public boolean account401kNumberOfWithdrawals() {
        int numberOfWithdrawals = InputLab.getAccount401k().getNumberOfWithdrawals();
        boolean valid = true;

//        Log.i(TAG, "numberOfWithdrawals: " + numberOfWithdrawals);
        if (numberOfWithdrawals < MIN_NUMBER_OF_WITHDRAWALS || numberOfWithdrawals > MAX_NUMBER_OF_WITHDRAWALS) {
            showMessageDialog(InputLab.getAccount401k().getPageIndex(), 4,
                    "401K Number Of Withdrawals (" + numberOfWithdrawals + ") must be between " + MIN_NUMBER_OF_WITHDRAWALS + " and " + MAX_NUMBER_OF_WITHDRAWALS + ".",
                    popupDialogTitle
                    );

            Log.i(TAG, "valid: " + valid);
            valid = false;
        }

//        Log.i(TAG, "Leaving the ValidateInputs account401kNumberOfWithdrawals().");
//        Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean account403bBalance() {
        double balance = InputLab.getAccount403b().getBalance();
        boolean valid = true;

        if (balance < 0.0) {
            showMessageDialog(InputLab.getAccount403b().getPageIndex(), 0,  "403B Balance (" + balance + ") is not positive.",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs account403bBalance().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean account403bGrowthRate() {
//        Log.i(TAG, "Calling the ValidateInputs account403bGrowthRate ().");

        double growthRate = InputLab.getAccount403b().getGrowthRate();
        boolean valid = true;

        if (growthRate < MIN_GROWTH_RATE || growthRate > MAX_GROWTH_RATE) {
            showMessageDialog(InputLab.getAccount403b().getPageIndex(), 1,
                    "403B Growth Rate (" + growthRate + ") must be between " + MIN_GROWTH_RATE + " and " + MAX_GROWTH_RATE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs account403bGrowthRate().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean account403bAnnualContribution() {
        // Log.i(TAG, "Calling the ValidateInputs account403bAnnualContribution ().");

        double contribution = InputLab.getAccount403b().getContributions();
        boolean valid = true;

        if (contribution < MIN_ANNUAL_CONTRIBUTION || contribution > MAX_ANNUAL_CONTRIBUTION) {
            showMessageDialog(InputLab.getAccount403b().getPageIndex(), 2,
                    "403B Annual Contribution (" + contribution + ") must be between " + MIN_ANNUAL_CONTRIBUTION + " and " + MAX_ANNUAL_CONTRIBUTION + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs account403bAnnualContribution().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean account403bStartWithdrawalsAge() {
        // Log.i(TAG, "Calling the ValidateInputs account403bStartRetirementAge ().");

        int startAge = InputLab.getAccount403b().getStartWithdrawalsAge();
        boolean valid = true;

        if (startAge < MIN_AGE || startAge > MAX_AGE) {
            showMessageDialog(InputLab.getAccount403b().getPageIndex(), 3,
                    "403B Start Withdrawals Age (" + startAge + ") must be between " + MIN_AGE + " and " + MAX_AGE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs account403bStartRetirementAge().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean account403bNumberOfWithdrawals() {
        // Log.i(TAG, "Calling the ValidateInputs account403bNumberOfWithdrawals().");

        int numberOfWithdrawals = InputLab.getAccount403b().getNumberOfWithdrawals();
        boolean valid = true;

        if (numberOfWithdrawals < MIN_NUMBER_OF_WITHDRAWALS || numberOfWithdrawals > MAX_NUMBER_OF_WITHDRAWALS) {
            showMessageDialog(InputLab.getAccount403b().getPageIndex(), 4,
                    "403B Number Of Withdrawals (" + numberOfWithdrawals + ") must be between " + MIN_NUMBER_OF_WITHDRAWALS + " and " + MAX_NUMBER_OF_WITHDRAWALS + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs account403bNumberOfWithdrawals().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean brokerageBalance() {
        // Log.i(TAG, "Calling the ValidateInputs brokerageBalance ().");

        double balance = InputLab.getBrokerage().getBalance();
        boolean valid = true;

        if (balance < 0.0) {
            showMessageDialog(InputLab.getBrokerage().getPageIndex(), 0,  "Brokerage Balance (" + balance + ") is not positive.",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs brokerageBalance().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean brokerageGrowthRate() {
        // Log.i(TAG, "Calling the ValidateInputs brokerageGrowthRate ().");

        double growthRate = InputLab.getBrokerage().getGrowthRate();
        boolean valid = true;

        if (growthRate < MIN_GROWTH_RATE || growthRate > MAX_GROWTH_RATE) {
            showMessageDialog(InputLab.getBrokerage().getPageIndex(), 1,
                    "Brokerage Growth Rate (" + growthRate + ") must be between " + MIN_GROWTH_RATE + " and " + MAX_GROWTH_RATE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs brokerageGrowthRate().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountCashBalanceBalance() {
        // Log.i(TAG, "Calling the ValidateInputs accountCashBalanceBalance ().");

        double balance = InputLab.getCashBalance().getBalance();
        boolean valid = true;

        if (balance < 0.0) {
            showMessageDialog(InputLab.getCashBalance().getPageIndex(), 0, "Cash Balance Balance (" + balance + ") is not positive.",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountCashBalanceBalance().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountCashBalanceGrowthRate() {
        // Log.i(TAG, "Calling the ValidateInputs account401kGrowthRate ().");

        double growthRate = InputLab.getCashBalance().getGrowthRate();
        boolean valid = true;

        if (growthRate < MIN_GROWTH_RATE || growthRate > MAX_GROWTH_RATE) {
            showMessageDialog(InputLab.getCashBalance().getPageIndex(), 1,
                    "Cash Balance Growth Rate (" + growthRate + ") must be between " + MIN_GROWTH_RATE + " and " + MAX_GROWTH_RATE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountCashBalanceGrowthRate().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountCashBalanceAnnualContribution() {
        // Log.i(TAG, "Calling the ValidateInputs accountCashBalanceAnnualContribution ().");

        double contribution = InputLab.getCashBalance().getContributions();
        boolean valid = true;

        if (contribution < MIN_ANNUAL_CONTRIBUTION || contribution > MAX_ANNUAL_CONTRIBUTION) {
            showMessageDialog(InputLab.getCashBalance().getPageIndex(), 2,
                    "Cash Balance Annual Contribution (" + contribution + ") must be between " + MIN_ANNUAL_CONTRIBUTION + " and " + MAX_ANNUAL_CONTRIBUTION + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountCahBalanceAnnualContribution().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountCashBalanceStartWithdrawalsAge() {
        // Log.i(TAG, "Calling the ValidateInputs accountCashBalanceStartWithdrawalsAge ().");

        int startAge = InputLab.getCashBalance().getStartWithdrawalsAge();
        boolean valid = true;

        if (startAge < MIN_AGE || startAge > MAX_AGE) {
            showMessageDialog(InputLab.getCashBalance().getPageIndex(), 3,
                    "Cash Balance Start Withdrawals Age (" + startAge + ") must be between " + MIN_AGE + " and " + MAX_AGE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountCashBalanceStartWithdrawa;sAge().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountCashBalanceNumberOfWithdrawals() {
        // Log.i(TAG, "Calling the ValidateInputs accountCashBalanceNumberOfWithdrawals().");

        int numberOfWithdrawals = InputLab.getCashBalance().getNumberOfWithdrawals();
        boolean valid = true;

        if (numberOfWithdrawals < MIN_NUMBER_OF_WITHDRAWALS || numberOfWithdrawals > MAX_NUMBER_OF_WITHDRAWALS) {
            showMessageDialog(InputLab.getCashBalance().getPageIndex(), 4,
                    "Cash Balance Number Of Withdrawals (" + numberOfWithdrawals + ") must be between " + MIN_NUMBER_OF_WITHDRAWALS + " and " + MAX_NUMBER_OF_WITHDRAWALS + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountCashBalanceNumberOfWithdrawals().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean deductionsDeductions() {
        // Log.i(TAG, "Calling the ValidateInputs deductionsDeductions().");

        double deductions = InputLab.getDeductions().getDeductions();
        boolean valid = true;

        if (deductions < 0.0) {
            showMessageDialog(InputLab.getDeductions().getPageIndex(), 0, "Deductions (" + deductions + ") is negative.",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs deductionsDeductions().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean expenseExpenses() {
        // Log.i(TAG, "Calling the ValidateInputs expenseExpenses().");

        double expenses = InputLab.getExpenses().getAnnualExpenses();
        boolean valid = true;

        if (expenses < 0.0) {
            showMessageDialog(InputLab.getExpenses().getPageIndex(), 0, "Expenses (" + expenses + ") is not positive.", popupDialogTitle);

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs expensesExpenses().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountRothBalance() {
        // Log.i(TAG, "Calling the ValidateInputs accountRothBalance ().");

        double balance = InputLab.getRothIra().getBalance();
        boolean valid = true;

        if (balance < 0.0) {
            showMessageDialog(InputLab.getRothIra().getPageIndex(), 0, "IRA (Roth) Balance (" + balance + ") is not positive.",
                    popupDialogTitle
                    );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountRothBalance().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountRothGrowthRate() {
        // Log.i(TAG, "Calling the ValidateInputs accountRothGrowthRate ().");

        double growthRate = InputLab.getRothIra().getGrowthRate();
        boolean valid = true;

        if (growthRate < MIN_GROWTH_RATE || growthRate > MAX_GROWTH_RATE) {
            showMessageDialog(InputLab.getRothIra().getPageIndex(), 1,
                    "IRA (Roth) Growth Rate (" + growthRate + ") must be between " + MIN_GROWTH_RATE + " and " + MAX_GROWTH_RATE + ".",
                    popupDialogTitle
                    );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountRothGrowthRate().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountRothAnnualContribution() {
        // Log.i(TAG, "Calling the ValidateInputs accountRothAnnualContribution ().");

        double contribution = InputLab.getRothIra().getContributions();
        boolean valid = true;

        if (contribution < MIN_ANNUAL_CONTRIBUTION || contribution > MAX_ANNUAL_CONTRIBUTION) {
            showMessageDialog(InputLab.getRothIra().getPageIndex(), 2,
                    "IRA (Roth) Annual Contribution (" + contribution + ") must be between " + MIN_ANNUAL_CONTRIBUTION + " and " + MAX_ANNUAL_CONTRIBUTION + ".",
                    popupDialogTitle
                    );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountRothAnnualContribution().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountRothStartWithdrawalsAge() {
        // Log.i(TAG, "Calling the ValidateInputs accountRothStartWithdrawalsAge ().");

        int startAge = InputLab.getRothIra().getStartWithdrawalsAge();
        boolean valid = true;

        if (startAge < MIN_AGE || startAge > MAX_AGE) {
            showMessageDialog(InputLab.getRothIra().getPageIndex(), 3,
                    "IRA (Roth) Start Withdrawals Age (" + startAge + ") must be between " + MIN_AGE + " and " + MAX_AGE + ".",
                    popupDialogTitle
                    );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountRothStartWithdrawalsAge().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountRothNumberOfWithdrawals() {
        // Log.i(TAG, "Calling the ValidateInputs accountRothNumberOfWithdrawals().");

        int numberOfWithdrawals = InputLab.getRothIra().getNumberOfWithdrawals();
        boolean valid = true;

        if (numberOfWithdrawals < MIN_NUMBER_OF_WITHDRAWALS || numberOfWithdrawals > MAX_NUMBER_OF_WITHDRAWALS) {
            showMessageDialog(InputLab.getRothIra().getPageIndex(), 4,
                    "IRA (Roth) Number Of Withdrawals (" + numberOfWithdrawals + ") must be between " + MIN_NUMBER_OF_WITHDRAWALS + " and " + MAX_NUMBER_OF_WITHDRAWALS + ".",
                    popupDialogTitle
                    );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountRothNumberOfWithdrawals().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountIraBalance() {
        // Log.i(TAG, "Calling the ValidateInputs accountIraBalance ().");

        double balance = InputLab.getTraditionalIra().getBalance();
        boolean valid = true;

        if (balance < 0.0) {
            showMessageDialog(InputLab.getTraditionalIra().getPageIndex(), 0, "IRA (Traditional) Balance (" + balance + ") is not positive.",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountIraBalance().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountIraGrowthRate() {
        // Log.i(TAG, "Calling the ValidateInputs accountIraGrowthRate ().");

        double growthRate = InputLab.getTraditionalIra().getGrowthRate();
        boolean valid = true;

        if (growthRate < MIN_GROWTH_RATE || growthRate > MAX_GROWTH_RATE) {
            showMessageDialog(InputLab.getTraditionalIra().getPageIndex(), 1,
                    "IRA (Traditional) Growth Rate (" + growthRate + ") must be between " + MIN_GROWTH_RATE + " and " + MAX_GROWTH_RATE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountIra GrowthRate().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountIraAnnualContribution() {
        // Log.i(TAG, "Calling the ValidateInputs accountIraAnnualContribution ().");

        double contribution = InputLab.getTraditionalIra().getContributions();
        boolean valid = true;

        if (contribution < MIN_ANNUAL_CONTRIBUTION || contribution > MAX_ANNUAL_CONTRIBUTION) {
            showMessageDialog(InputLab.getTraditionalIra().getPageIndex(), 2,
                    "IRA (Traditional) Annual Contribution (" + contribution + ") must be between " + MIN_ANNUAL_CONTRIBUTION + " and " + MAX_ANNUAL_CONTRIBUTION + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountIraAnnualContribution().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountIraStartWithdrawalsAge() {
        // Log.i(TAG, "Calling the ValidateInputs accountIraStartWithdrawalsAge ().");

        int startAge = InputLab.getTraditionalIra().getStartWithdrawalsAge();
        boolean valid = true;

        if (startAge < MIN_AGE || startAge > MAX_AGE) {
            showMessageDialog(InputLab.getTraditionalIra().getPageIndex(), 3,
                    "IRA (Traditional) Start Withdrawals Age (" + startAge + ") must be between " + MIN_AGE + " and " + MAX_AGE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountIraStartWithdrawalsAge().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean accountIraNumberOfWithdrawals() {
        // Log.i(TAG, "Calling the ValidateInputs accountIraNumberOfWithdrawals().");

        int numberOfWithdrawals = InputLab.getTraditionalIra().getNumberOfWithdrawals();
        boolean valid = true;

        if (numberOfWithdrawals < MIN_NUMBER_OF_WITHDRAWALS || numberOfWithdrawals > MAX_NUMBER_OF_WITHDRAWALS) {
            showMessageDialog(InputLab.getTraditionalIra().getPageIndex(), 4,
                    "IRA (Traditional) Number Of Withdrawals (" + numberOfWithdrawals + ") must be between " + MIN_NUMBER_OF_WITHDRAWALS + " and " + MAX_NUMBER_OF_WITHDRAWALS + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs accountIraNumberOfWithdrawals().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean pensionMonthlyAmount() {
        // Log.i(TAG, "Calling the ValidateInputs pensionMonthlyAmount().");

        double monthlyAmount = InputLab.getPension().getMonthlyAmount();
        boolean valid = true;

        if (monthlyAmount < 0.0) {
            showMessageDialog(InputLab.getPension().getPageIndex(), 0,
                    "Pension Monthly Amount (" + monthlyAmount + ") is not positive.",
                    popupDialogTitle
                    );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs pensionMonthlyAmount().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean pensionStartingAge() {
        // Log.i(TAG, "Calling the ValidateInputs pensionStartingAge().");

        int startAge = InputLab.getPension().getStartingAge();
        boolean valid = true;

        if (startAge < MIN_AGE || startAge > MAX_AGE) {
            showMessageDialog(InputLab.getPension().getPageIndex(), 1,
                    "Pension Starting Age (" + startAge + ") must be between " + MIN_AGE + " and " + MAX_AGE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs pensionStartingAge().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean pensionInflationAdjusted() {
        // Log.i(TAG, "Calling the ValidateInputs pensionInflationAdjusted().");

        boolean inflationAdjusted = InputLab.getPension().getInflationAdjusted();
        boolean valid = false;

        if (inflationAdjusted) {
            valid = true;
        }
        else {
            valid = true;
        }

        // Log.i(TAG, "Leaving the ValidateInputs pensionStartingAge().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean personalCurrentAge() {
        // Log.i(TAG, "Calling the ValidateInputs personalCurrentAge().");

        int currentAge = InputLab.getPersonal().getCurrentAge();
        boolean valid = true;

        if (currentAge < MIN_AGE || currentAge > MAX_AGE) {
            showMessageDialog(InputLab.getPersonal().getPageIndex(), 0,
                    "Personal Current Age (" + currentAge + ") must be between " + MIN_AGE + " and " + MAX_AGE + ".",
                    popupDialogTitle
            );

            valid = false;
        }


        // Log.i(TAG, "Leaving the ValidateInputs personalCurrentAge().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean personalDeathAge() {
        // Log.i(TAG, "Calling the Validate personalDeathAge().");

        int deathAge = InputLab.getPersonal().getLifeExpectancyAge();
        boolean valid = true;

        if (deathAge < MIN_AGE || deathAge > MAX_AGE) {
            showMessageDialog(InputLab.getPersonal().getPageIndex(), 1,
                    "Personal Life Expectancy Age (" + deathAge + ") must be between " + MIN_AGE + " and " + MAX_AGE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs personalDeathAge().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean personalRetirementAge() {
        // Log.i(TAG, "Calling the Validate personalRetirementAge().");

        int retirementAge = InputLab.getPersonal().getRetirementAge();
        boolean valid = true;

        if (retirementAge < MIN_AGE || retirementAge > MAX_AGE) {
            showMessageDialog(InputLab.getPersonal().getPageIndex(), 2,
                    "Personal Retirement Age (" + retirementAge + ") must be between " + MIN_AGE + " and " + MAX_AGE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs personalRetirementAge().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean ages() {
        // Log.i(TAG, "Calling the ValidateInputs ages().");

        int currentAge = InputLab.getPersonal().getCurrentAge();
        int deathAge = InputLab.getPersonal().getLifeExpectancyAge();
        int withdrawalsAge401k = InputLab.getAccount401k().getStartWithdrawalsAge();

        boolean valid = true;

        if (currentAge > deathAge) {
            showMessageDialog(InputLab.getPersonal().getPageIndex(), 3,
                    "Current Age (" + currentAge + ") is greater than the Life Expectancy Age (" + deathAge + ")",
                    popupDialogTitle
            );

            valid = false;
        }

        if (valid && withdrawalsAge401k > deathAge) {
            showMessageDialog(InputLab.getPersonal().getPageIndex(), 4,
                    "401K Withdrawals Age (" + withdrawalsAge401k + ") is greater than the Life Expectancy Age (" + deathAge + ")",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs ages().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean personalCurrentYear() {
        // Log.i(TAG, "Calling the ValidateInputs personalCurrentYear().");

        int currentYear = InputLab.getPersonal().getCurrentYear();
        boolean valid = true;

        if (currentYear < MIN_YEAR) {
            showMessageDialog(InputLab.getPersonal().getPageIndex(), 6, "Current Year (" + currentYear + ") is not above the min year.", popupDialogTitle);

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs personalCurrentYear().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean personalBirthYear() {
        // Log.i(TAG, "Calling the ValidateInputs personalBirthYear().");

        int currentYear = InputLab.getPersonal().getBirthYear();
        boolean valid = true;

        if (currentYear < MIN_YEAR) {
            showMessageDialog(InputLab.getPersonal().getPageIndex(), 7, "Birth Year (" + currentYear + ") is not positive.", popupDialogTitle);

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs personalBirthYear().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean personalInflationRate() {
        // Log.i(TAG, "Calling the ValidateInputs personalInflationRate().");

        double inflationRate = InputLab.getPersonal().getInflation();
        boolean valid = true;

        if (inflationRate < MIN_INFLATION_RATE || inflationRate > MAX_INFLATION_RATE) {
            showMessageDialog(InputLab.getPersonal().getPageIndex(), 8,
                    "Personal Inflation Rate (" + inflationRate + ") must be between " + MIN_INFLATION_RATE + " and " + MAX_INFLATION_RATE + ".",
                    popupDialogTitle);

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs personalInflationRate().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean salarySalary() {
        // Log.i(TAG, "Calling the ValidateInputs salarySalary().");

        double salary = InputLab.getSalary().getSalary();
        boolean valid = true;

        if (salary < 0.0) {
            showMessageDialog(InputLab.getSalary().getPageIndex(), 0, "Salary (" + salary + ") is not positive.",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs salarySalary().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean salaryMeritIncrease() {
        // Log.i(TAG, "Calling the ValidateInputs salaryMeritIncrease().");

        double meritIncrease = InputLab.getSalary().getMeritIncrease();
        boolean valid = true;

        if (meritIncrease < MIN_MERIT_INCREASE || meritIncrease > MAX_MERIT_INCREASE) {
            showMessageDialog(InputLab.getSalary().getPageIndex(), 1,
                    "Salary Merit Increase (" + meritIncrease + ") must be between " + MIN_MERIT_INCREASE + " and " + MAX_MERIT_INCREASE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs salaryMeritIncrease().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean savingsBalance() {
        // Log.i(TAG, "Calling the ValidateInputs savingsBalance().");

        double balance = InputLab.getSavings().getBalance();
        boolean valid = true;

        if (balance < 0.0) {
            showMessageDialog(InputLab.getSavings().getPageIndex(), 0, "Savings Balance (" + balance + ") is not positive.", popupDialogTitle);

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs savingsBalance().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean savingsGrowthRate() {
        // Log.i(TAG, "Calling the ValidateInputs savingsGrowthRate().");

        double growthRate = InputLab.getSavings().getGrowthRate();
        boolean valid = true;

        if (growthRate < MIN_GROWTH_RATE || growthRate > MAX_GROWTH_RATE) {
            showMessageDialog(InputLab.getSavings().getPageIndex(), 1,
                    "Savings Growth Rate (" + growthRate + ") must be between " + MIN_GROWTH_RATE + " and " + MAX_GROWTH_RATE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs savingsGrowthRate().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

     public boolean socialSecurityStartingAge() {
        Log.i(TAG, "Calling the ValidateInputs socialSecurityStartingAge().");

        int startAge = InputLab.getSocialSecurity().getStartingAge();
        boolean valid = true;

         System.out.println(TAG + "startAge" + startAge);
         if (startAge < MIN_SOCIAL_SECURITY_AGE || startAge > MAX_SOCIAL_SECURITY_AGE) {
            showMessageDialog(InputLab.getSocialSecurity().getPageIndex(), 0,
                    "Social Security Starting Age (" + startAge + ") must be between " + MIN_SOCIAL_SECURITY_AGE + " and " + MAX_SOCIAL_SECURITY_AGE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        System.out.println(TAG + "Leaving the ValidateInputs socialSecurityStartingAge().");
        System.out.println(TAG + "                   valid = " + valid);

        return valid;
    }

    public boolean socialSecurityMonthlyAmount() {
        // Log.i(TAG, "Calling the ValidateInputs socialSecurityMonthlyAmount().");

        double monthlyAmount = InputLab.getSocialSecurity().getMonthlyAmount();
        boolean valid = true;

        if (monthlyAmount < 0.0) {
            showMessageDialog(InputLab.getSocialSecurity().getPageIndex(), 1, "Social Security Monthly Amount (" + monthlyAmount + ") is negative.", popupDialogTitle);

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs socialSecurityMonthlyAmount().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean taxesFederal() {
        // Log.i(TAG, "Calling the ValidateInputs taxesFederal().");

        Float taxRate = InputLab.getTaxes().getFederalTaxRate();
        boolean valid = true;

        if (taxRate < MIN_TAX_RATE || taxRate > MAX_TAX_RATE) {
            showMessageDialog(InputLab.getTaxes().getPageIndex(), 0,
                    "Federal Tax Rate (" + taxRate + ") must be between " + MIN_TAX_RATE + " and " + MAX_TAX_RATE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs taxesFederal().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }

    public boolean taxesState() {
        // Log.i(TAG, "Calling the ValidateInputs taxesState().");

        Float taxRate = InputLab.getTaxes().getStateTaxRate();
        boolean valid = true;

        if (taxRate < MIN_TAX_RATE || taxRate > MAX_TAX_RATE) {
            showMessageDialog(InputLab.getTaxes().getPageIndex(), 1,
                    "State Tax Rate (" + taxRate + ") must be between " + MIN_TAX_RATE + " and " + MAX_TAX_RATE + ".",
                    popupDialogTitle
            );

            valid = false;
        }

        // Log.i(TAG, "Leaving the ValidateInputs taxesState().");
        // Log.i(TAG, "                   valid = " + valid);

        return valid;
    }
}
