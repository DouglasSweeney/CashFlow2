package com.doug.cashflow.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.doug.cashflow.model.db.CashFlowDb;
import com.doug.cashflow.model.db.User;

import java.util.Date;

/**
 * Created by Doug on 5/7/2017.
 */

public class LoginLab {
    private static final String  TAG        = "LoginLab";
    private static final Integer MY_USER_ID = 1;

    private static LoginLab          sMainLab;
    private static AppCompatActivity mActivity;
    private static CashFlowDb mDb;

    public static LoginLab getInstance(Context context, boolean testing) {
        if (sMainLab == null) {
            sMainLab = new LoginLab(context, testing);
        }
        return sMainLab;
    }

    private LoginLab(Context context, boolean testing) {
        if (testing == false) {
            mDb = new CashFlowDb(context);

            if (mDb.getNumberOfRecordsInHeadersTable() == 0) {
                mDb.populateHeadersTable();
            }

            if (mDb.getNumberOfRecordsInFieldsTable() == 0) {
                mDb.populateFieldsTable();
            }

            if (mDb.getNumberOfRecordsInUserTable() == 0) {
                User user = new User(MY_USER_ID, "@", "@"); // Insert my data
                mDb.insertUser(user);
            }

            if (mDb.getNumberOfRecordsInRealDataTable() == 0 && mDb.getNumberOfRecordsInIntegerDataTable() == 0) {
                mDb.populateDataTable(MY_USER_ID);
            }

            mDb.setDatabasePointers();
        }
    }

    public static void           setCurrentActivity(AppCompatActivity activity)    { mActivity = activity; }
    public static AppCompatActivity getCurrentActivity() { return mActivity;
    }

    public static CashFlowDb getDb() { return mDb; }

    public static String getRealData(Integer user_id, String header, String field) {
        return mDb.getRealData(user_id, header, field);
    }

    public static String getIntegerData(Integer user_id, String header, String field) {
        return mDb.getIntegerData(user_id, header, field);
    }

    public static Date getDate(Integer userId, Integer field) {
        return  mDb.getDate(userId, field);
    }
/*
    public static void updateData(Integer userId, String header, Integer field, Double value) {
        String fieldString = LoginLab.getCurrentActivity().getResources().getString(field);
        mDb.updateDataTable(userId, header, fieldString, value);
    }
*/
    public static void updateData(Integer userId, String header, Integer field, Float value) {
        String fieldString = LoginLab.getCurrentActivity().getResources().getString(field);
        mDb.updateDataTable(userId, header, fieldString, value);
    }

    public static void updateData(Integer userId, String header, Integer field, Integer value) {
        String fieldString = LoginLab.getCurrentActivity().getResources().getString(field);
        mDb.updateDataTable(userId, header, fieldString, value);
    }
}
