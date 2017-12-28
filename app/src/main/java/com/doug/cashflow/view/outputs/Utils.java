package com.doug.cashflow.view.outputs;

import android.content.Context;
import android.util.DisplayMetrics;

import com.doug.cashflow.controller.LoginLab;

import java.text.DecimalFormat;

public class Utils {
    String stripCents(double input) {
        String string;

        DecimalFormat defaultFormat = new DecimalFormat("######");

//        defaultFormat.setMinimumIntegerDigits(6);

        string = defaultFormat.format(input);

        // strip off leading '0' or ','
        int i = 0;
        int len = string.length();
        if (len > 2) {
            while (i < len-1 && (string.charAt(i) == '0' || string.charAt(i) == ',')) {
                StringBuilder leadingSpaces= new StringBuilder();
                for (int j = 0; j <= i; j++) {
                    leadingSpaces.append(" ");
                }
                string = leadingSpaces + string.substring(i+1, len);
                i++;
            }
        }

        return string;
    }

    public String getString(Context context, Integer index) {
        return context.getString(index);
    }

    boolean inLandscapeMode(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        return height <= width;
    }
}
