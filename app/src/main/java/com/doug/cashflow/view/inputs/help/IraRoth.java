package com.doug.cashflow.view.inputs.help;

import android.content.Context;

import com.doug.cashflow.view.inputs.Util;

/**
 * Created by Doug on 5/22/2017.
 */

public class IraRoth extends Account implements Accounts {
    private Util utils = new Util();
    private String name;

    public IraRoth(Context context, int title, int text) {
        super(context, title, text);

        name = utils.getString(title);
    }

    public String getName() {
        return name;
    }
}
