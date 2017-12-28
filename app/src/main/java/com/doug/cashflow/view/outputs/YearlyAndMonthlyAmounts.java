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

public class YearlyAndMonthlyAmounts {
//    private static final String TAG = "YearlyAndMonthlyAmounts";
    private static final Integer NUMBER_OF_MONTHS = 12;

    private View     view;
    private Context  mContext;
    private final Utils    utils = new Utils();


    public YearlyAndMonthlyAmounts(Context context, ArrayList<ResultsDataNode> values, Integer name) {
//        Log.d(TAG, "Entering YearlyAndMonthlyAmounts constructor");
        mContext = context;
        String title = utils.getString(context, name);
        view = createView(context, values, title);
    }

    private String getHeaders(Context context) {
        return String.format("%-5.5s%-4.4s%-7.7s%-9.9s\n",
                utils.getString(context, R.string.output_header_year),
                utils.getString(context, R.string.output_header_age),
                utils.getString(context, R.string.output_header_yearly),
                utils.getString(context, R.string.output_header_monthly));
    }

    private String getData(ResultsDataNode node) {
        return String.format("%-5s%-3s%-7.7s%-7.7s\n",
                node.getYear(),
                node.getAge(),
                utils.stripCents(node.getEndingValue()),
                utils.stripCents(node.getEndingValue()/NUMBER_OF_MONTHS));
    }

    @SuppressWarnings("WeakerAccess")
    String getData(ArrayList<ResultsDataNode> values) {
        ResultsDataNode node;
        StringBuilder string;

        string = new StringBuilder(getHeaders(mContext));

        for (int i=0; i<values.size(); i++) {
            node = values.get(i);
            string.append(getData(node));
        }

        return string.toString();
    }

    public View getView() {
        return view;
    }

    @SuppressWarnings("WeakerAccess")
    @SuppressLint("InflateParams")
    View createView(Context context, ArrayList<ResultsDataNode> values, String title) {
        String   valueString;
        TextView titleView;
        TextView mTableField;

        view = LayoutInflater.from(context).inflate(R.layout.output, null);

        titleView=(TextView) view.findViewById(R.id.title);
        if (titleView != null) {
            titleView.setText(title);
        }

        mTableField=(TextView) view.findViewById(R.id.text_area);
        if (mTableField != null) {
            valueString = getData(values);
            mTableField.setText(valueString);
        }

        return view;
    }
}
