package com.doug.cashflow.view.outputs;

import android.annotation.SuppressLint;
import android.content.Context;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.doug.cashflow.R;
import com.doug.cashflow.model.system.ResultsDataNode;

import java.util.ArrayList;

public class FederalAndStateAmounts {
//    private static final String TAG = "FederalAndStateAmounts";

    private final View     view;
    private Context mContext;
    private final Utils    utils = new Utils();

    public FederalAndStateAmounts(Context context, ArrayList<ResultsDataNode> values, Integer name) {
//        Log.d(TAG, "Entering FederalAndStateAmounts");
        mContext = context;

        String title = utils.getString(context, name);
        view = createView(context, values, title);
    }

    public View getView() {
        return view;
    }

    private String getHeaders() {
        return String.format("%-5.5s%-4.4s%-9.9s%-7.7s%-9.9s\n",
                utils.getString(mContext, R.string.output_header_year),
                utils.getString(mContext, R.string.output_header_age),
                utils.getString(mContext, R.string.output_header_federal),
                utils.getString(mContext, R.string.output_header_state),
                utils.getString(mContext, R.string.output_header_total));
    }

    private String getData(ResultsDataNode node) {
        return String.format("%-5s%-3s%-8.8s%-8.8s%-8.8s\n",
                node.getYear(),
                node.getAge(),
                utils.stripCents(node.getFederalTaxes()),
                utils.stripCents(node.getStateTaxes()),
                utils.stripCents(node.getFederalTaxes() + (node.getStateTaxes())));
    }

    public String getData(ArrayList<ResultsDataNode> values) {
        ResultsDataNode node;
        StringBuilder string;

        string = new StringBuilder(getHeaders());

        for (int i=0; i<values.size(); i++) {
            node = values.get(i);
            string.append(getData(node));
        }

        return string.toString();
    }

    @SuppressWarnings("WeakerAccess")
    @SuppressLint("InflateParams")
    public View createView(Context context, ArrayList<ResultsDataNode> values, String title) {
        View     view;
        String   valueString;
        TextView titleView;
        TextView mTableField;

        view = LayoutInflater.from(context).inflate(R.layout.output, null);

        titleView=(TextView) view.findViewById(R.id.title);
        if (titleView != null) {
            titleView.setText(title);
        }

        mTableField=(TextView) view.findViewById(R.id.text_area);
        valueString = getData(values);
        if (mTableField != null){
            mTableField.setText(valueString);
        }

        return view;
    }
}
