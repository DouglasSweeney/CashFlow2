package com.doug.cashflow.view.inputs.help;

import android.content.Context;

import com.doug.cashflow.view.inputs.Util;

public class Personal extends Account implements Accounts {
    private Util utils = new Util();
    private String name;

    public Personal(Context context, int title, int text) {
        super(context, title, text);

        name = utils.getString(title);
    }

    public String getName() {
        return name;
    }
}
