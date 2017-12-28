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

import com.doug.cashflow.R;
import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.InputPagerActivity;
import com.doug.cashflow.controller.LoginLab;
import com.doug.cashflow.model.system.ValidateInputs;

public class Savings {

    private TextView mBalanceField;
    private TextView mGrowthRateField;

    private Util mUtil = new Util();

    private int  pageIndex = -1;
    private View v;
    private Context context;
    private FragmentManager fragmentManager;

    public Savings(int pageIndex, Context context, FragmentManager fragmentManager, ViewGroup parent) {
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
        return context.getString(R.string.input_header_savings);
    }

    public View createView(final Context context, final FragmentManager fragmentManager, ViewGroup parent) {
        TextView titleView;

        v = LayoutInflater.from(context).inflate(R.layout.input_savings, parent, false);

        titleView = (TextView)v.findViewById(R.id.title);
        titleView.setText(getName(context));

        Field textField1 = new Field();
        mBalanceField = textField1.createField(v, R.id.savings_balance,
                context.getString(R.string.field_balance),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_0_DOT_00, context);

        Field textField2 = new Field();
        mGrowthRateField = textField2.createField(v, R.id.savings_growth_rate,
                context.getString(R.string.field_growth_rate),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_0_DOT_0, context);

        return v;
    }

    public Float getBalance() {
        return mUtil.getFloatInput(mBalanceField);
    }

    public Float getGrowthRate() {
        return mUtil.getFloatInput(mGrowthRateField);
    }

    public void saveToDatabase(Integer userId) {
        LoginLab.updateData(userId, getName(context), R.string.field_balance, getBalance());
        LoginLab.updateData(userId, getName(context), R.string.field_growth_rate, getGrowthRate());
    }

    public void setBalance(String value) {
        mBalanceField.setText(value);
    }

    public void setGrowthRate(String value) {
        mGrowthRateField.setText(value);
    }
}
