package com.doug.cashflow.view.outputs;

import android.content.Context;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.ResultsDataNode;

import java.util.ArrayList;

public class Taxes extends FederalAndStateAmounts implements Accounts {
    private static final Integer title = R.string.output_header_taxes;

    private Context mContext;
    private final Utils    utils = new Utils();

    public Taxes(Context context, ArrayList<ResultsDataNode> values) {
        super(context, values, title);

        mContext = context;
    }

    public String getName() {
        return utils.getString(mContext, title);
    }
}

