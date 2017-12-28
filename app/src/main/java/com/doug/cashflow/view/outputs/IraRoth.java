package com.doug.cashflow.view.outputs;

import android.content.Context;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.ResultsDataNode;

import java.util.ArrayList;

public class IraRoth extends Account implements Accounts {
    private static final Integer title = R.string.output_header_ira_roth;

    private Context mContext;

    private final Utils    utils = new Utils();

    public IraRoth(Context context, ArrayList<ResultsDataNode> values) {
        super(context, values, title);

        mContext = context;
    }

    public String getName() {
        return utils.getString(mContext, title);
    }
}
