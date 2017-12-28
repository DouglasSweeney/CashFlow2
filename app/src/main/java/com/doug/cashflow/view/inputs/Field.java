package com.doug.cashflow.view.inputs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.doug.cashflow.R;
import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.LoginLab;

public class Field {
    private static final String TAG = "Field (inputs)";

    TextView textView;
    Integer  rIdField;
    Util.SET_DEFAULT setDefault;
    Context context;

    String   name;
    private final Util mUtil = new Util();

    public TextView createField(final View v, Integer parameterRIdField, String inputField, String parameterName,
                                final Util.SET_DEFAULT parameterSetDefault, Context parameterContext) {
        String valueString;

        name = parameterName;
        rIdField = parameterRIdField;
        setDefault = parameterSetDefault;
        context = parameterContext;

        textView = (TextView) v.findViewById(parameterRIdField);
//        System.out.println(TAG +  "inputField: <" + inputField + ">");
//        System.out.println(TAG +  "context.getString(R.string.field_balance): <" + context.getString(R.string.field_balance) + ">");
        if (
                inputField.equals(context.getString(R.string.field_balance)) ||
                inputField.equals(context.getString(R.string.field_growth_rate)) ||
                inputField.equals(context.getString(R.string.field_contributions)) ||
                inputField.equals(context.getString(R.string.field_yearly_deductions)) ||
                inputField.equals(context.getString(R.string.field_annual_expenses)) ||
                inputField.equals(context.getString(R.string.field_monthly_amount)) ||
                inputField.equals(context.getString(R.string.field_inflation)) ||
                inputField.equals(context.getString(R.string.field_salary)) ||
                inputField.equals(context.getString(R.string.field_merit_increase)) ||
                inputField.equals(context.getString(R.string.field_federal_tax_rate)) ||
                inputField.equals(context.getString(R.string.field_state_tax_rate))
           ) {
            valueString = LoginLab.getRealData(InputLab.getUserId(), parameterName, inputField);
        }
        else {
            valueString = LoginLab.getIntegerData(InputLab.getUserId(), parameterName, inputField);
        }

        textView.setText(valueString);

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d(TAG, "afterTextChanged name: " + name);

                mUtil.callbackAfterTextChanged(setDefault, s, name, context, rIdField);
            }
        });
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus) {
                    Log.d(TAG, "onFocusChange name: " + name);
                    mUtil.callbackAfterTextChanged(setDefault, null, name, context, rIdField);
                }
            }
        });

        return textView;
    }
}
