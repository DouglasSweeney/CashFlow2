package com.doug.cashflow.view.inputs;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

/**
 * Created by Doug on 1/10/2017.
 */

public class Deductions {

    private static final String TAG = "Deductions";

    private TextView mDeductionsField;

    private Util mUtil = new Util();

    private int  pageIndex = -1;
    private View v;
    private Context context;
    private FragmentManager fragmentManager;

    public Deductions(int pageIndex, Context context, FragmentManager fragmentManager, ViewGroup parent) {
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
        return context.getString(R.string.input_header_deductions);
    }

    public View createView(final Context context, final FragmentManager fragmentManager, ViewGroup parent) {
        TextView titleView;

        v = LayoutInflater.from(context).inflate(R.layout.input_deductions, parent, false);

        titleView = (TextView)v.findViewById(R.id.title);
        titleView.setText(getName(context));

        Field textField1 = new Field();
        mDeductionsField = textField1.createField(v, R.id.deductions_deductions,
                context.getString(R.string.field_yearly_deductions),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_0_DOT_00, context);

        return v;
    }

    public Float getDeductions() {
        return mUtil.getFloatInput(mDeductionsField);
    }

    public void saveToDatabase(Integer userId) {
        Log.d(TAG, "saveToDatabase: <" + getDeductions() + ">");

        LoginLab.updateData(userId, getName(context), R.string.field_yearly_deductions, getDeductions());
    }

    public void setDeductions(String value) {
        mDeductionsField.setText(value);
    }

}

