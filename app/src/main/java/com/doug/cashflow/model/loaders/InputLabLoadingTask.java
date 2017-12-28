package com.doug.cashflow.model.loaders;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.doug.cashflow.controller.InputLab;

/**
 * Created by Doug on 5/16/2017.
 */

public class InputLabLoadingTask extends MyAsyncTaskLoader<String> {
    private final static String TAG = "InputLabLoadingTask";

    Context context;
    FragmentManager fragmentManager;

    public InputLabLoadingTask (Context context, FragmentManager fragmentManager) {
        super(context);

        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public String loadInBackground() {
        Log.d(TAG, "loadInBackground()");

        InputLab.getInstance(context, fragmentManager, null);

        return "";
    }
}
