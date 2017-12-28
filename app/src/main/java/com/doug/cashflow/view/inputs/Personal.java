package com.doug.cashflow.view.inputs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView;

import com.doug.cashflow.controller.DatePickerFragment;
import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.InputPagerActivity;
import com.doug.cashflow.controller.LoginLab;
import com.doug.cashflow.R;
import com.doug.cashflow.model.system.Input;
import com.doug.cashflow.model.system.ValidateInputs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Personal extends Fragment {
    private static final String TAG = "Personal (Input)";

    private static final int REQUEST_SIMULATION_DATE = 0;
    private static final int REQUEST_BIRTH_DATE = 1;

    private TextView mSimulationDateField;
    private TextView mBirthDateField;
    private TextView mRetirementAgeField;
    private TextView mLifeExpectancyAgeField;
    private TextView mInflationField;

    private Util mUtil = new Util();

    private int  pageIndex = -1;
    private View     v;
    private Context  context;
    private FragmentManager fragmentManager;

    public Personal () {
    }

    public void buildView(int pageIndex, Context context, FragmentManager fragmentManager, ViewGroup parent) {
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
        return context.getString(R.string.input_header_personal);
    }

    public View createView(final Context context, final FragmentManager fragmentManager, ViewGroup parent) {
        TextView titleView;

        v = LayoutInflater.from(context).inflate(R.layout.input_personal, parent, false);

        titleView = (TextView)v.findViewById(R.id.title);
        titleView.setText(getName(context));

        mSimulationDateField = (TextView) v.findViewById(R.id.personal_simulation_date);
        updateSimulationDateField(LoginLab.getDate(InputLab.getUserId(), R.string.field_simulation_date));
        mSimulationDateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date = LoginLab.getDate(InputLab.getUserId(), R.string.field_simulation_date);

                DatePickerFragment dialog = DatePickerFragment.newInstance(date, v.getResources().getString(R.string.simulation_date_title));
                dialog.setTargetFragment(Personal.this, REQUEST_SIMULATION_DATE);
                dialog.show(LoginLab.getCurrentActivity().getSupportFragmentManager(), "");
            }
        });

        mBirthDateField = (TextView) v.findViewById(R.id.personal_birth_date);
        updateBirthDateField(LoginLab.getDate(InputLab.getUserId(), R.string.field_birth_date));
        mBirthDateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date = LoginLab.getDate(InputLab.getUserId(), R.string.field_birth_date);

                DatePickerFragment dialog = DatePickerFragment.newInstance(date, v.getResources().getString(R.string.birth_date_title));
                dialog.setTargetFragment(Personal.this, REQUEST_BIRTH_DATE);
                dialog.show(LoginLab.getCurrentActivity().getSupportFragmentManager(), "");
            }
        });

        Field textField1 = new Field();
        mRetirementAgeField = textField1.createField(v, R.id.personal_retirement_age,
                context.getString(R.string.field_retirement_age),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_65, context);

        Field textField2 = new Field();
        mLifeExpectancyAgeField = textField2.createField(v, R.id.personal_life_expectancy_age,
                context.getString(R.string.field_life_expectancy_age),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_95, context);

        Field textField3 = new Field();
        mInflationField = textField3.createField(v, R.id.personal_inflation,
                context.getString(R.string.field_inflation),
                getName(context), Util.SET_DEFAULT.SET_DEFAULT_0_DOT_0, context);

        return v;
    }

    public Date getSimulationDate(){
//        Log.d(TAG, "getSimulationDate()");

        String simulationDate = mSimulationDateField.getText().toString();

//        Log.d(TAG, "    simulationDate: " + simulationDate);
        Date date;

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = format.parse(simulationDate);
        }
        catch (Exception e) {
            date = new Date();
        }

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        Log.d(TAG, "    returning: " + calendar.get(Calendar.YEAR));

        return date;
    }

    public Date getBirthDate(){
        String birthDate = mBirthDateField.getText().toString();
        Date date;

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = format.parse(birthDate);
        }
        catch (Exception e) {
            date = new Date();
        }

        return date;
    }

    public Integer getRetirementAge() {
        return mUtil.getIntegerInput(mRetirementAgeField);
    }

    public Integer getLifeExpectancyAge() {
        return mUtil.getIntegerInput(mLifeExpectancyAgeField);
    }

    public Float getInflation() {
        return mUtil.getFloatInput(mInflationField);
    }

    public void saveToDatabase(Integer userId) {

        LoginLab.getDb().updateDate(userId, R.string.field_simulation_date, getSimulationDate());
        LoginLab.getDb().updateDate(userId, R.string.field_birth_date, getBirthDate());

        LoginLab.updateData(userId, getName(context), R.string.field_retirement_age, getRetirementAge());
        LoginLab.updateData(userId, getName(context), R.string.field_life_expectancy_age, getLifeExpectancyAge());
        LoginLab.updateData(userId, getName(context), R.string.field_inflation, getInflation());
    }

    public void updateSimulationDateField(Date date) {
        int month;
        int day;
        int year;

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mSimulationDateField.setText(new StringBuilder()
                .append(month + 1).append("/").append(day).append("/").append(year).append(" "));
    }

    public void updateBirthDateField(Date date) {
        int month;
        int day;
        int year;

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mBirthDateField.setText(new StringBuilder()
                .append(month + 1).append("/").append(day).append("/").append(year).append(" "));
    }

    public int getSimulationYear() {
        Date simulationDate = getSimulationDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simulationDate);

        int simulationYear = calendar.get(Calendar.YEAR);
        return simulationYear;
    }

    public int getBirthYear() {
        Date birthDate = getBirthDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);

        int birthYear = calendar.get(Calendar.YEAR);

        return birthYear;
    }

    public Integer getCurrentYear() {
        Integer year;
        GregorianCalendar calendar = new GregorianCalendar();

        year = calendar.get(GregorianCalendar.YEAR);

        return year;
    }

    public int getCurrentAge() {
        int currentYear = getCurrentYear();
        int birthYear = getBirthYear();
        int value = currentYear - birthYear - 1;

        return value;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_SIMULATION_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            updateSimulationDateField(date);
        }

        if (requestCode == REQUEST_BIRTH_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            updateBirthDateField(date);
        }
    }

    public void setSimulationDate(String value) {
        mSimulationDateField.setText(value);
    }

    public void setBirthDate(String value) {
        mBirthDateField.setText(value);
    }

    public void setRetirementAge(String value) {
        mRetirementAgeField.setText(value);
    }

    public void setLifeExpectancyAge(String value) { mLifeExpectancyAgeField.setText(value); }

    public void setInflation(String value) {
        mInflationField.setText(value);
    }
}
