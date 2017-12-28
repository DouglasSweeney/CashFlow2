package com.doug.cashflow.view.inputs;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.InputPagerActivity;
import com.doug.cashflow.controller.LoginLab;
import com.doug.cashflow.R;
import com.doug.cashflow.model.system.ValidateInputs;

public class SocialSecurity {

    private TextView mStartingAgeField;
    private TextView mMonthlyAmountField;

    private Util mUtil = new Util();
    private Field textField = new Field();

    private int  pageIndex = -1;
    private View v;
    private Context context;
    private FragmentManager fragmentManager;

    public SocialSecurity(int pageIndex, Context context, FragmentManager fragmentManager, ViewGroup parent) {
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
        return context.getString(R.string.input_header_social_security);
    }

    public View createView(final Context context, final FragmentManager fragmentManager, ViewGroup parent) {
        String field;
        String valueString;
        TextView titleView;

        v = LayoutInflater.from(context).inflate(R.layout.input_social_security, parent, false);

        titleView = (TextView)v.findViewById(R.id.title);
        titleView.setText(getName(context));

        Field textField1 = new Field();
        mStartingAgeField = textField1.createField(v, R.id.social_security_starting_age,
                context.getString(R.string.field_starting_age),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_65, context);

        Field textField2 = new Field();
        mMonthlyAmountField = textField2.createField(v, R.id.social_security_monthly_amount,
                context.getString(R.string.field_monthly_amount),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_0_DOT_00, context);

        return v;
    }

    public Integer getStartingAge() {
        return mUtil.getIntegerInput(mStartingAgeField);
    }

    public Float getMonthlyAmount() {
        return mUtil.getFloatInput(mMonthlyAmountField);
    }

    public void saveToDatabase(Integer userId) {
        LoginLab.updateData(userId, getName(context), R.string.field_starting_age, getStartingAge());
        LoginLab.updateData(userId, getName(context), R.string.field_monthly_amount, getMonthlyAmount());
    }

    public void setStartingAge(String value) {
        mStartingAgeField.setText(value);
    }

    public void setMonthlyAmount(String value) {
        mMonthlyAmountField.setText(value);
    }
}
