package com.doug.cashflow.view.inputs.help;

import android.content.Context;

import com.doug.cashflow.view.inputs.Util;

public class Account401k extends Account implements Accounts {
    private Util utils = new Util();
    private String name;

    public Account401k(Context context, int title, int text) {
        super(context, title, text);

        name = utils.getString(title);
    }

    public String getName() {
        return name;
    }
}
