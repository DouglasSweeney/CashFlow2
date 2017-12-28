package com.doug.cashflow.view.outputs;

import android.content.Context;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.ResultsDataNode;

import java.util.ArrayList;

public class Brokerage extends Account implements Accounts {
    private static final Integer title = R.string.output_header_brokerage;

    private Context mContext;
    private Utils    utils = new Utils();

    public Brokerage(Context context, ArrayList<ResultsDataNode> values) {
        super(context, values, title);

        mContext = context;
    }

    public String getName() {
        return utils.getString(mContext, title);
    }
}
