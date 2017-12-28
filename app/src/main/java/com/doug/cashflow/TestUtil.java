package com.doug.cashflow;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import static org.powermock.api.mockito.PowerMockito.when;


public class TestUtil {
    boolean test = false;

    public void setTest() {
        test = true;
    }

    public boolean getTest() {
        return test;
    }

    @SuppressWarnings({"unused", "ConstantConditions"})
    public final boolean checkNotInLowMemoryCondition(AppCompatActivity activity) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();

        if ((activity.getSystemService(Context.ACCOUNT_SERVICE)) != null) {
            ((ActivityManager) activity.getSystemService(Context.ACCOUNT_SERVICE)).getMemoryInfo(memoryInfo);
        }

        return memoryInfo.lowMemory;
//        assertFalse("Low memory condition.", memoryInfo.lowMemory);
    }

    public final void MockContextGetStringInputNames(Context mockContext) {
        // Input names
        when(mockContext.getString(R.string.input_header_401k)).thenReturn("401K (Input)");
        when(mockContext.getString(R.string.input_header_403b)).thenReturn("403B (Input)");
        when(mockContext.getString(R.string.input_header_brokerage)).thenReturn("Brokerage (Input)");
        when(mockContext.getString(R.string.input_header_cash_balance)).thenReturn("Cash Balance (Input)");
        when(mockContext.getString(R.string.input_header_deductions)).thenReturn("Deductions (Input)");
        when(mockContext.getString(R.string.input_header_expenses)).thenReturn("Expenses (Input)");
        when(mockContext.getString(R.string.input_header_ira_roth)).thenReturn("IRA-Roth (Input)");
        when(mockContext.getString(R.string.input_header_ira_traditional)).thenReturn("IRA-Traditional (Input)");
        when(mockContext.getString(R.string.input_header_pension)).thenReturn("Pension (Input)");
        when(mockContext.getString(R.string.input_header_personal)).thenReturn("Personal (Input)");
        when(mockContext.getString(R.string.input_header_salary)).thenReturn("Salary (Input)");
        when(mockContext.getString(R.string.input_header_savings)).thenReturn("Savings (Input)");
        when(mockContext.getString(R.string.input_header_social_security)).thenReturn("Social Security (Input)");
        when(mockContext.getString(R.string.input_header_taxes)).thenReturn("Taxes (Input)");
    }

    public final void MockContextGetStringInputHeaders(Context mockContext) {
        when(mockContext.getString(R.string.input_header_401k)).thenReturn("401K (Input)");
        when(mockContext.getString(R.string.input_header_403b)).thenReturn("403B (Input)");
        when(mockContext.getString(R.string.input_header_brokerage)).thenReturn("Brokerage (Input)");
        when(mockContext.getString(R.string.input_header_cash_balance)).thenReturn("Cash Balance (Input)");
        when(mockContext.getString(R.string.input_header_deductions)).thenReturn("Deductions (Input)");
        when(mockContext.getString(R.string.input_header_expenses)).thenReturn("Expenses (Input)");
        when(mockContext.getString(R.string.input_header_ira_roth)).thenReturn("IRA-Roth (Input)");
        when(mockContext.getString(R.string.input_header_ira_traditional)).thenReturn("IRA-Traditional (Input)");
        when(mockContext.getString(R.string.input_header_pension)).thenReturn("Pension (Input)");
        when(mockContext.getString(R.string.input_header_personal)).thenReturn("Personal (Input)");
        when(mockContext.getString(R.string.input_header_salary)).thenReturn("Salary (Input)");
        when(mockContext.getString(R.string.input_header_savings)).thenReturn("Savings (Input)");
        when(mockContext.getString(R.string.input_header_social_security)).thenReturn("Social Security (Input)");
        when(mockContext.getString(R.string.input_header_taxes)).thenReturn("Taxes (Input)");
    }

    public final void MockContextGetStringDatabaseIntegers(Context mockContext) {
        when(mockContext.getString(R.string.field_number_of_withdrawals)).thenReturn("Number Of Withdrawals");
        when(mockContext.getString(R.string.field_start_withdrawals_age)).thenReturn("Start Withdrawals Age");
        when(mockContext.getString(R.string.field_starting_age)).thenReturn("Starting Age");
        when(mockContext.getString(R.string.field_retirement_age)).thenReturn("Retirement Age");
        when(mockContext.getString(R.string.field_life_expectancy_age)).thenReturn("Life Expectancy Age");
        when(mockContext.getString(R.string.field_inflation_adjusted)).thenReturn("Inflation Adjusted");
    }

    public final void MockContextGetStringDatabaseReal(Context mockContext) {
        // Floats
        when(mockContext.getString(R.string.field_balance)).thenReturn("Balance");
        when(mockContext.getString(R.string.field_growth_rate)).thenReturn("Growth Rate (%)");
        when(mockContext.getString(R.string.field_contributions)).thenReturn("Contributions");
        when(mockContext.getString(R.string.field_yearly_deductions)).thenReturn("Yearly Deductions");
        when(mockContext.getString(R.string.field_annual_expenses)).thenReturn("Annual Expenses");
        when(mockContext.getString(R.string.field_monthly_amount)).thenReturn("Monthly Amount");
        when(mockContext.getString(R.string.field_inflation)).thenReturn("Inflation (%)");
        when(mockContext.getString(R.string.field_salary)).thenReturn("Salary");
        when(mockContext.getString(R.string.field_merit_increase)).thenReturn("Merit Increase (%)");
        when(mockContext.getString(R.string.field_federal_tax_rate)).thenReturn("Federal Tax Rate (%)");
        when(mockContext.getString(R.string.field_state_tax_rate)).thenReturn("State Tax Rate (%)");
    }
}