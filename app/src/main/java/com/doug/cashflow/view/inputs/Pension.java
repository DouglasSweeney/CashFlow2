package com.doug.cashflow.view.inputs;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.InputPagerActivity;
import com.doug.cashflow.controller.LoginLab;
import com.doug.cashflow.R;
import com.doug.cashflow.model.system.ValidateInputs;

public class Pension {
    private static final String TAG = "Pension";

    private TextView mStartingAgeField;
    private TextView mMonthlyAmountField;
    private CheckBox mInflationAdjustedField;

    private Util mUtil = new Util();

    private int  pageIndex = -1;
    private View v;
    private Context context;
    private FragmentManager fragmentManager;

    public Pension(int pageIndex, Context context, FragmentManager fragmentManager, ViewGroup parent) {
        this.pageIndex = pageIndex;
        this.context = context;
        this.fragmentManager = fragmentManager;

        v = createView(context, fragmentManager, parent);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public View getView() {
        return v;
    }

    public String getName(Context context) {
        return context.getString(R.string.input_header_pension);
    }

    public View createView(final Context context, final FragmentManager fragmentManager, ViewGroup parent) {
        String field;
        String valueString;
        TextView titleView;

        v = LayoutInflater.from(context).inflate(R.layout.input_pension, parent, false);

        titleView = (TextView)v.findViewById(R.id.title);
        titleView.setText(getName(context));

        Field textField1 = new Field();
        mStartingAgeField = textField1.createField(v, R.id.pension_starting_age,
                context.getString(R.string.field_starting_age),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_65, context);

        Field textField2 = new Field();
        mMonthlyAmountField = textField2.createField(v, R.id.pension_monthly_amount,
                context.getString(R.string.field_monthly_amount),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_0_DOT_00, context);

        mInflationAdjustedField = (CheckBox) v.findViewById(R.id.pension_inflation_adjusted);
        field = context.getString(R.string.field_inflation_adjusted);
        valueString = LoginLab.getIntegerData(InputLab.getUserId(), getName(context), field);
        if (valueString == null || valueString.equals("0")) {
            mInflationAdjustedField.setChecked(false);
        }
        else {
            mInflationAdjustedField.setChecked(true);
        }
        mInflationAdjustedField.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });

        return v;
    }

    public Integer getStartingAge() {
        return mUtil.getIntegerInput(mStartingAgeField);
    }

    public Float getMonthlyAmount() {
        return mUtil.getFloatInput(mMonthlyAmountField);
    }

//    public boolean getInflationAdjusted() {
//        return mInflationAdjustedField.isChecked();
//    }

    public boolean getInflationAdjusted() {
        String field;
        boolean adjusted;

        field = mInflationAdjustedField.getText().toString();

        adjusted = mUtil.convertBoolean(field);

        return adjusted;
    }

    public void saveToDatabase(Integer userId) {
        LoginLab.updateData(userId, getName(context), R.string.field_starting_age, getStartingAge());
        LoginLab.updateData(userId, getName(context), R.string.field_monthly_amount, getMonthlyAmount());
        if (getInflationAdjusted() == true) {
            LoginLab.updateData(userId, getName(context), R.string.field_inflation_adjusted, 1);
        }
        else {
            LoginLab.updateData(userId, getName(context), R.string.field_inflation_adjusted, 0);
        }
    }


    public void setStartingAge(String value) {
        mStartingAgeField.setText(value);
    }

    public void setMonthlyAmount(String value) {
        mMonthlyAmountField.setText(value);
    }

    public void setInflationAdjusted(final Boolean value) {
//        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
//            public void run() {
                if (value) {
                     mInflationAdjustedField.setChecked(true);
                }
                else {
                    mInflationAdjustedField.setChecked(false);
                }
//            }
//        });
    }
}
