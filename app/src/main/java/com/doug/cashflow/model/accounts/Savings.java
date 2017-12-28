package com.doug.cashflow.model.accounts;

public class Savings extends Account implements Accounts {
    private final static String TAG = "Savings (Model)";

    public Savings(double balance, double growthRate, int currentAge, int currentYear, int deathAge) {
        initialize(balance, growthRate, currentAge, currentYear, deathAge);
    }

    public void deposit(int currentAge, double depositAmount) {
        recomputeGrowthWithDeposit(currentAge, depositAmount);
    }

    public double withdraw(int currentAge, double withdrawalAmount) {
        int currentYear = convertAgeToYear(currentAge);
        double value;
        double amount;
        double beginningValue = getBeginningValue(currentYear);
        double deposits = getDeposits(currentYear);
        double withdrawals = getWithdrawals(currentYear);

        value = beginningValue + deposits - withdrawals;

        if (withdrawalAmount <= value) {
            amount = withdrawalAmount;
        }
        else {
            amount = value;
        }


        recomputeGrowthWithWithdrawal(currentAge, amount);

        return amount;
    }

    /**
     * Get the taxability of this account.
     *
     * @return boolean
     */
    public boolean isTaxable() {
        return false;
    }
}
