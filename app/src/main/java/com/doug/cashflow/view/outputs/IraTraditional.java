package com.doug.cashflow.view.outputs;

import android.content.Context;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.ResultsDataNode;

import java.util.ArrayList;

public class IraTraditional extends Account implements Accounts {
    private static final Integer title = R.string.output_header_ira_traditional;

    private Context mContext;
    private final Utils    utils = new Utils();

    public IraTraditional(Context context, ArrayList<ResultsDataNode> values) {
        super(context, values, title);

        mContext = context;
    }

    public String getName() {
        return utils.getString(mContext, title);
    }
}
