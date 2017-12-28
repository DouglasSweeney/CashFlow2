package com.doug.cashflow.view.inputs.help;

import android.content.Context;

import com.doug.cashflow.view.inputs.Util;

/**
 * Created by Doug on 5/22/2017.
 */

public class Account403b extends Account implements Accounts {
    Util utils = new Util();
    String name;

    public Account403b(Context context, int title, int text) {
        super(context, title, text);

        name = utils.getString(title);
    }

    public String getName() {
        return name;
    }
}
