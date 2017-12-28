package com.doug.cashflow.model.system;

import android.content.Context;
import android.util.Log;

import com.doug.cashflow.R;
import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.InputPagerActivity;

public class ValidateInputPage {
    private static final String TAG = "ValidateInputPage";
    public boolean validate(Context context, Integer position) {
        boolean valid = false;
        Input input = InputLab.getInputs().get(position);
        ValidateInputs validateInputs = new ValidateInputs(context);
        Log.d(TAG,"input: <" + input.getHeader() + ">");
        if (input.getHeader().equals(InputLab.getAccount401k().getName(context))) {
            valid = validateInputs.validate401k();
        }
        else
        if (input.getHeader().equals(InputLab.getAccount403b().getName(context))) {
            valid = validateInputs.validate403b();
        }
        else
        if (input.getHeader().equals(InputLab.getBrokerage().getName(context))) {
            valid = validateInputs.validateBrokerage();
        }
        else
        if (input.getHeader().equals(InputLab.getCashBalance().getName(context))) {
            valid = validateInputs.validateCashBalance();
        }
        else
        if (input.getHeader().equals(InputLab.getDeductions().getName(context))) {
            valid = validateInputs.validateDeductions();
        }
        else
        if (input.getHeader().equals(InputLab.getExpenses().getName(context))) {
            valid = validateInputs.validateExpenses();
        }
        else
        if (input.getHeader().equals(InputLab.getRothIra().getName(context))) {
            valid = validateInputs.validateRoth();
        }
        else
        if (input.getHeader().equals(InputLab.getTraditionalIra().getName(context))) {
            valid = validateInputs.validateIra();
        }
        else
        if (input.getHeader().equals(InputLab.getPension().getName(context))) {
            valid = validateInputs.validatePension();
        }
        else
        if (input.getHeader().equals(InputLab.getPersonal().getName(context))) {
            valid = validateInputs.validatePersonal();
        }
        else
        if (input.getHeader().equals(InputLab.getSalary().getName(context))) {
            valid = validateInputs.validateSalary();
        }
        else
        if (input.getHeader().equals(InputLab.getSavings().getName(context))) {
            valid = validateInputs.validateSavings();
        }
        else
        if (input.getHeader().equals(InputLab.getSocialSecurity().getName(context))) {
            valid = validateInputs.validateSocialSecurity();
        }
        else
        if (input.getHeader().equals(InputLab.getTaxes().getName(context))) {
            valid = validateInputs.validateTaxes();
        }

        return valid;
    }
}
