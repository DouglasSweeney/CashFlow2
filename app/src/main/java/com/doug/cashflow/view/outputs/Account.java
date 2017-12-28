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

public class Account {
//    private static final String TAG="Account (Output)";

    private View view;
    private Context mContext;
    private final Utils    utils = new Utils();

    public Account() {

    }

    public Account(Context context, ArrayList<ResultsDataNode> values, Integer name) {

        mContext = context;
        String title = context.getString(name);

        view = createView(context, values, title);
    }

    private String getHeaders() {
        String year = utils.getString(mContext, R.string.output_header_year);
        String age = utils.getString(mContext, R.string.output_header_age);
        String begin = utils.getString(mContext, R.string.output_header_begin);
        String deposit = utils.getString(mContext, R.string.output_header_deposit);
        String withdraw = utils.getString(mContext, R.string.output_header_withdraw);
        String end = utils.getString(mContext, R.string.output_header_end);

        return String.format("%-5.5s%-4.4s%-6.6s%-8.8s%-9.9s%-4.4s\n",
                year, age, begin, deposit, withdraw, end);
    }

    private String getData(ResultsDataNode node) {
//        Log.d(TAG, "getData(ResultsDataNode node)");
//        Log.d(TAG, "    node.getAge(): " + node.getAge());
//        Log.d(TAG, "    node.getYear(): " + node.getYear());

        return String.format("%-5s%-3s%-7.7s%-7.7s%-7.7s%-7.7s\n",
                node.getYear(),
                node.getAge(),
                utils.stripCents(node.getBeginningValue()),
                utils.stripCents(node.getDeposit()),
                utils.stripCents(node.getWithdrawal()),
                utils.stripCents(node.getEndingValue()));
    }

    @SuppressWarnings("WeakerAccess")
    public String getData(ArrayList<ResultsDataNode> values) {
//        Log.d(TAG, "getData(ArrayList<ResultsDataNode values)");
//        Log.d(TAG, "    values.toString(): " + values.toString());
//        Log.d(TAG, "    values.size(): " + values.size());
//        Log.d(TAG, "    values.isEmpty():" + values.isEmpty());

        StringBuilder stringBuilder = new StringBuilder(getHeaders());
        for (int i = 0; i<values.size(); i++) {
//            node = values.get(i);
            stringBuilder.append(getData(values.get(i)));
        }
        return stringBuilder.toString();
    }

    public View getView() {
        return view;
    }

    @SuppressLint("InflateParams")
    public View createView(Context context, ArrayList<ResultsDataNode> values, String title) {
//        Log.d(TAG, "createView(LayoutInflater inflater, ViewGroup container, String title)");
//        Log.d(TAG, "    title: " + title);

        String valueString;
        TextView titleView;
        TextView mTableField;

        view = LayoutInflater.from(context).inflate(R.layout.output, null);

        titleView=(TextView) view.findViewById(R.id.title);
        if (titleView != null) {
            titleView.setText(title);
        }

        mTableField=(TextView) view.findViewById(R.id.text_area);
        valueString=getData(values);
        if (mTableField != null)
            mTableField.setText(valueString);

        return view;
    }
}
