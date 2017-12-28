package com.doug.cashflow.controller;

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
import com.doug.cashflow.model.system.ResultsDataNode;

public class Calculate {
    private static final String TAG = "Calculate";

    public void run() {
        int    currentAge = 0;
        double deposits = 0.0;
        double expense = 0.0;
        double partialExpense = 0.0;
        double incomeValue;
        double tax = 0.0;
        double amount;
        ResultsDataNode node;

        Double taxableWithdrawals;

        Account401K        account401k = ModelLab.getAccount401k();
        Account403B        account403b = ModelLab.getAccount403b();
        Brokerage          brokerage = ModelLab.getBrokerage();
        AccountCashBalance cashBalance = ModelLab.getCashBalance();
        Deductions         deductions = ModelLab.getDeductions();
        Expenses           expenses = ModelLab.getExpenses();
        AccountIra         iraTraditional = ModelLab.getIraTraditional();
        AccountRoth        iraRoth = ModelLab.getIraRoth();
        Pension            pension = ModelLab.getPension();
        Salary             salary = ModelLab.getSalary();
        Savings            savings = ModelLab.getSavings();
        SocialSecurity     socialSecurity = ModelLab.getSocialSecurity();
        Taxes              taxes = ModelLab.getTaxes();

        for (int year=expenses.getSmallestYear(); year<=expenses.getLargestYear(); year++) {
            expense = expenses.getBeginningValue(year);

            incomeValue = 0.0;

            // Taxable withdrawals
            if (salary != null) {
                incomeValue += salary.getEndingValue(year);
            }
            if (pension != null) {
                incomeValue += pension.getEndingValue(year);
            }
            if (socialSecurity != null) {
                incomeValue += socialSecurity.getEndingValue(year);
            }

            if (account401k != null) {
                incomeValue += account401k.getWithdrawals(year);
            }
            if (account403b != null) {
                incomeValue += account403b.getWithdrawals(year);
            }
            if (cashBalance != null) {
                incomeValue += cashBalance.getWithdrawals(year);
            }
            if (iraTraditional != null) {
                incomeValue += iraTraditional.getWithdrawals(year);
            }

            taxableWithdrawals = incomeValue;

            // Non taxable withdrawals
            if (iraRoth != null) {
                incomeValue += iraRoth.getWithdrawals(year);
            }
            if (brokerage != null) {
                incomeValue += brokerage.getWithdrawals(year);
            }
            if (savings != null) {
                incomeValue += savings.getWithdrawals(year);
            }

            currentAge = expenses.convertYearToAge(year);

            partialExpense = expense - incomeValue;

            taxableWithdrawals = taxableWithdrawals - deductions.getBeginningValue(year);
            if (taxes != null && taxableWithdrawals > 0)
                tax = taxes.compute(currentAge, taxableWithdrawals);
            else
                tax = 0.0;

            // Withdraw $ from savings and/or brokerage
            if (tax > 0.0) {
                amount = savings.withdraw(currentAge, tax);
                tax -= amount;

                amount = brokerage.withdraw(currentAge, tax);
                tax -= amount;

                if (iraRoth.getNumberOfWithdrawals() == 0 && iraRoth.getBeginningValue(year) > partialExpense) {
                    amount = iraRoth.withdraw(currentAge, tax);
                    tax -= amount;
                }

                if (account401k.getNumberOfWithdrawals() == 0 && account401k.getBeginningValue(year) > partialExpense) {
                    amount = account401k.withdraw(currentAge, tax);
                    tax -= amount;
                }
                if (account403b.getNumberOfWithdrawals() == 0 && account403b.getBeginningValue(year) > partialExpense) {
                    amount = account403b.withdraw(currentAge, tax);
                    tax -= amount;
                }
                if (cashBalance.getNumberOfWithdrawals() == 0 && cashBalance.getBeginningValue(year) > partialExpense) {
                    amount = cashBalance.withdraw(currentAge, tax);
                    tax -= amount;
                }
                if (iraTraditional.getNumberOfWithdrawals() == 0 && iraTraditional.getBeginningValue(year) > partialExpense) {
                    iraTraditional.withdraw(currentAge, tax);
                }
            }

            if (partialExpense > 0.0) {

                amount = savings.withdraw(currentAge, partialExpense);
                partialExpense -= amount;

                amount = brokerage.withdraw(currentAge, partialExpense);
                partialExpense -= amount;

                if (iraRoth.getNumberOfWithdrawals() == 0 && iraRoth.getBeginningValue(year) > partialExpense) {
                    iraRoth.withdraw(currentAge, partialExpense);
                }
                if (account401k.getNumberOfWithdrawals() == 0 && account401k.getBeginningValue(year) > partialExpense) {
                    account401k.withdraw(currentAge, partialExpense);
                }
                if (account403b.getNumberOfWithdrawals() == 0 && account403b.getBeginningValue(year) > partialExpense) {
                    account403b.withdraw(currentAge, partialExpense);
                }
                if (cashBalance.getNumberOfWithdrawals() == 0 && cashBalance.getBeginningValue(year) > partialExpense) {
                    cashBalance.withdraw(currentAge, partialExpense);
                }
                if (iraTraditional.getNumberOfWithdrawals() == 0 && iraTraditional.getBeginningValue(year) > partialExpense) {
                    iraTraditional.withdraw(currentAge, partialExpense);
                }
            }

            partialExpense = incomeValue - expense;
            if (partialExpense > 0) {
                savings.deposit(currentAge, partialExpense);
            }
        }
    }
}
