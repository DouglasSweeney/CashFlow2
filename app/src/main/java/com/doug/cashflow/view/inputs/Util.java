package com.doug.cashflow.view.inputs;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.util.Log;
import android.widget.TextView;

//import android.app.FragmentManager;

import com.doug.cashflow.R;
import com.doug.cashflow.controller.AlertDialogFragment;
import com.doug.cashflow.controller.InputPagerActivity;
import com.doug.cashflow.controller.LoginLab;
import com.doug.cashflow.model.system.ValidateInputs;

/**
 * Created by Doug on 4/20/2017.
 */

public class Util {
    private static final String TAG = "TestUtil (Input)";

    public enum SET_DEFAULT {SET_DEFAULT_0_DOT_00, SET_DEFAULT_0_DOT_0, SET_DEFAULT_0, SET_DEFAULT_65, SET_DEFAULT_95};

    private boolean testing = false;

    public void setTesting() {
        testing = true;
    }

    public boolean verifyIntegerInput(String data, FragmentManager fragmentManager, Integer category, Integer field, Integer extra) {

        if (!verifyInteger(data)) {
            if (testing == true) {
                return false;
            }

            return false;
        }
        else {
            return true;
        }
    }

    public boolean verifyFloatInput(String data, FragmentManager fragmentManager, Integer category, Integer field, Integer extra) {
        if (!verifyFloat(data)) {
            if (testing == true) {
                return false;
            }

            return false;
        }
        else {
            return true;
        }
    }

    private boolean verifyInteger(String integerInput) {
        Integer answer;
        boolean valid = false;

        integerInput = stripCommas(integerInput);

        if (integerInput.length() == 0) {
            return false;
        }

        if (integerInput.matches("\\d+") == false) {
            return false;
        }

        try {

//            Log.i(TAG, "integerInput: " + integerInput);
            answer = new Integer(integerInput);
            valid = true;
        } catch (Exception e) {
            valid = false;
        }

//        Log.i(TAG, "valid: " + valid);

        return valid;
    }

    private boolean verifyFloat(String floatInput) {
        Float answer;
        boolean valid = false;

        floatInput = stripCommas(floatInput);

        if (floatInput.length() == 0) {
            return false;
        }

        if (floatInput.matches("[\\d]*[\\\\.]{0,1}[\\d]{0,2}") == false) { // look for #.##; '.' might not exist
            return false;
        }

        try {
            answer = new Float(floatInput);
            valid = true;
        } catch (Exception e) {
            valid = false;
        }

        return valid;
    }

    public Integer convertInteger(String integerInput) {
        Integer answer;

        try {
            answer = Integer.valueOf(integerInput);
        } catch (Exception e) {
            answer = -2;
        }

        return answer;
    }

    public Float convertFloat(String floatInput) {
        Float answer;

        floatInput = stripCommas(floatInput);
        try {
            answer = Float.valueOf(floatInput);
        } catch (Exception e) {
            answer = 0.0f;
        }

        return answer;
    }

    public Double convertDouble(String input) {
        Double answer;

        input = stripCommas(input);
        try {
            answer = Double.valueOf(input);
        } catch (Exception e) {
            answer = 0.0;
        }

        return answer;
    }

    public boolean convertBoolean (String booleanInput) {
        boolean answer;

        try {
            answer = Boolean.valueOf(booleanInput);
        } catch (Exception e) {
            answer = false;
        }

        return answer;
    }

    public Integer getIntegerInput(TextView inputField){
        Integer returnValue;
        String field;

        field = inputField.getText().toString();
        field = stripCommas(field);

        returnValue=convertInteger(field);

        return returnValue;
    }

    public Float getFloatInput(TextView inputField){
        Float returnValue;
        String field;

        field = inputField.getText().toString();
        field = stripCommas(field);

        returnValue=convertFloat(field);

        return returnValue;
    }
/*
    public Double getDoubleInput(TextView inputField){
        Double returnValue;
        String field;

        field = inputField.getText().toString();
        field = stripCommas(field);

        returnValue=convertDouble(field);

        return returnValue;
    }
*/
    public String getString(Integer index) {
        return LoginLab.getCurrentActivity().getResources().getString(index);

    }

    public String stripCommas(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ',') {
                input = input.replace(",", "");
            }
        }

        return input;
    }

    private void setDefault0Dot00IfLengthIsZero(Editable s) {
        if (s.length() == 0) {
            s.insert(0, "0.00");
        }
    }

    private void setDefault0Dot0IfLengthIsZero(Editable s) {
        if (s.length() == 0) {
            s.insert(0, "0.0");
        }
    }

    private void setDefault0IfLengthIsZero(Editable s) {
        if (s.length() == 0) {
            s.insert(0, "0");
        }
    }

    private void setDefault65IfLengthIsZero(Editable s) {
        if (s.length() == 0) {
            s.insert(0, "65");
        }
    }

    private void setDefault95IfLengthIsZero(Editable s) {
        if (s.length() == 0) {
            s.insert(0, "95");
        }
    }

    private void setDefaultIfLengthIsZero(SET_DEFAULT setDefault, Editable field) {
        switch (setDefault) {
            case SET_DEFAULT_0_DOT_00:
                setDefault0Dot00IfLengthIsZero(field);
                break;
            case SET_DEFAULT_0_DOT_0:
                setDefault0Dot0IfLengthIsZero(field);
                break;
            case SET_DEFAULT_0:
                setDefault0IfLengthIsZero(field);
                break;
            case SET_DEFAULT_65:
                setDefault65IfLengthIsZero(field);
                break;
            case SET_DEFAULT_95:
                setDefault95IfLengthIsZero(field);
                break;
        }
    }

    public void callbackAfterTextChanged(SET_DEFAULT setDefault, Editable field, String name, Context context, int field_index) {
        ValidateInputs validateInputs = new ValidateInputs(context);

        if (field != null) {
            setDefaultIfLengthIsZero(setDefault, field);
        }

        Log.d(TAG, "InputPagerActivity.getCurrentItem(): " +
                InputPagerActivity.getCurrentItem());
        Log.d(TAG, "name: " +
                name);
        Log.d(TAG, "InputPagerActivity.getPageIndex(name): " +
                InputPagerActivity.getPageIndex(name));
        Log.d(TAG, "field_index: " +
                field_index);
        Log.d(TAG, "R.id.account_401k_growth_rate: " +
                R.id.account_401k_growth_rate);

        if (InputPagerActivity.getCurrentItem() == InputPagerActivity.getPageIndex(name)) {
            switch (field_index) {
                case R.id.account_401k_balance:
                    validateInputs.account401kBalance();
                    break;
                case R.id.account_401k_growth_rate:
                    validateInputs.account401kGrowthRate();
                    break;
                case R.id.account_401k_contributions:
                    validateInputs.account401kAnnualContribution();
                    break;
                case R.id.account_401k_start_withdrawals_age:
                    validateInputs.account401kStartWithdrawalsAge();
                    break;
                case R.id.account_401k_number_of_withdrawals:
                    validateInputs.account401kNumberOfWithdrawals();
                    break;
                case R.id.account_403b_balance:
                    validateInputs.account403bBalance();
                    break;
                case R.id.account_403b_growth_rate:
                    validateInputs.account403bGrowthRate();
                    break;
                case R.id.account_403b_contributions:
                    validateInputs.account403bAnnualContribution();
                    break;
                case R.id.account_403b_start_withdrawals_age:
                    validateInputs.account403bStartWithdrawalsAge();
                    break;
                case R.id.account_403b_number_of_withdrawals:
                    validateInputs.account403bNumberOfWithdrawals();
                    break;
                case R.id.brokerage_balance:
                    validateInputs.brokerageBalance();
                    break;
                case R.id.brokerage_growth_rate:
                    validateInputs.brokerageGrowthRate();
                    break;
                case R.id.cash_balance_balance:
                    validateInputs.accountCashBalanceBalance();
                    break;
                case R.id.cash_balance_growth_rate:
                    validateInputs.accountCashBalanceGrowthRate();
                    break;
                case R.id.cash_balance_contributions:
                    validateInputs.accountCashBalanceAnnualContribution();
                    break;
                case R.id.cash_balance_start_withdrawals_age:
                    validateInputs.accountCashBalanceStartWithdrawalsAge();
                    break;
                case R.id.cash_balance_number_of_withdrawals:
                    validateInputs.accountCashBalanceNumberOfWithdrawals();
                    break;
                case R.id.deductions_deductions:
                    validateInputs.deductionsDeductions();
                    break;
                case R.id.expenses_expense:
                    validateInputs.expenseExpenses();
                    break;
                case R.id.roth_ira_balance:
                    validateInputs.accountRothBalance();
                    break;
                case R.id.roth_ira_growth_rate:
                    validateInputs.accountRothGrowthRate();
                    break;
                case R.id.roth_ira_contributions:
                    validateInputs.accountRothAnnualContribution();
                    break;
                case R.id.roth_ira_start_withdrawals_age:
                    validateInputs.accountRothStartWithdrawalsAge();
                    break;
                case R.id.roth_ira_number_of_withdrawals:
                    validateInputs.accountRothNumberOfWithdrawals();
                    break;
                case R.id.traditional_ira_balance:
                    validateInputs.accountIraBalance();
                    break;
                case R.id.traditional_ira_growth_rate:
                    validateInputs.accountIraGrowthRate();
                    break;
                case R.id.traditional_ira_contributions:
                    validateInputs.accountIraAnnualContribution();
                    break;
                case R.id.traditional_ira_start_withdrawals_age:
                    validateInputs.accountIraStartWithdrawalsAge();
                    break;
                case R.id.traditional_ira_number_of_withdrawals:
                    validateInputs.accountIraNumberOfWithdrawals();
                    break;
                case R.id.pension_starting_age:
                    validateInputs.pensionStartingAge();
                    break;
                case R.id.pension_monthly_amount:
                    validateInputs.pensionMonthlyAmount();
                    break;
                case R.id.personal_retirement_age:
                    validateInputs.personalRetirementAge();
                    break;
                case R.id.personal_life_expectancy_age:
                    validateInputs.personalDeathAge();
                    break;
                case R.id.personal_inflation:
                    validateInputs.personalInflationRate();
                    break;
                case R.id.salary_salary:
                    validateInputs.salarySalary();
                    break;
                case R.id.salary_merit_increase:
                    validateInputs.salaryMeritIncrease();
                    break;
                case R.id.savings_balance:
                    validateInputs.savingsBalance();
                    break;
                case R.id.savings_growth_rate:
                    validateInputs.savingsGrowthRate();
                    break;
                case R.id.social_security_starting_age:
                    validateInputs.socialSecurityStartingAge();
                    break;
                case R.id.social_security_monthly_amount:
                    validateInputs.socialSecurityMonthlyAmount();
                    break;
                case R.id.taxes_federal_tax_rate:
                    validateInputs.taxesFederal();
                    break;
                case R.id.taxes_state_tax_rate:
                    validateInputs.taxesState();
                    break;
            }
        }
    }
}