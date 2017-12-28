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

public class Taxes {

    private TextView mFederalTaxRateField;
    private TextView mStateTaxRateField;

    private Util mUtil = new Util();

    private int  pageIndex = -1;
    private View v;
    private Context context;
    private FragmentManager fragmentManager;

    public Taxes(int pageIndex, Context context, FragmentManager fragmentManager, ViewGroup parent) {
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
        return context.getString(R.string.input_header_taxes);
    }

    public View createView(final Context context, final FragmentManager fragmentManager, ViewGroup parent) {
        TextView titleView;

        v = LayoutInflater.from(context).inflate(R.layout.input_taxes, parent, false);

        titleView = (TextView)v.findViewById(R.id.title);
        titleView.setText(getName(context));

        Field textField1 = new Field();
        mFederalTaxRateField = textField1.createField(v, R.id.taxes_federal_tax_rate,
                context.getString(R.string.field_federal_tax_rate),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_0_DOT_0, context);


        Field textField2 = new Field();
        mStateTaxRateField = textField2.createField(v, R.id.taxes_state_tax_rate,
                context.getString(R.string.field_state_tax_rate),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_0_DOT_0, context);

        return v;
    }

    public Float getFederalTaxRate() {
        return mUtil.getFloatInput(mFederalTaxRateField);
    }

    public Float getStateTaxRate() {
        return mUtil.getFloatInput(mStateTaxRateField);
    }

    public void saveToDatabase(Integer userId) {
        LoginLab.updateData(userId, getName(context), R.string.field_federal_tax_rate, getFederalTaxRate());
        LoginLab.updateData(userId, getName(context), R.string.field_state_tax_rate, getStateTaxRate());
    }

    public void setFederalTaxRate(String value) {
        mFederalTaxRateField.setText(value);
    }

    public void setStateTaxRate(String value) {
        mStateTaxRateField.setText(value);
    }
}
