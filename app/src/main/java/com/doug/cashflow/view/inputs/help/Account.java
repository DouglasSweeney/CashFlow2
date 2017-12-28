package com.doug.cashflow.view.inputs.help;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.doug.cashflow.R;
import com.doug.cashflow.view.outputs.Utils;

/**
 * Created by Doug on 5/22/2017.
 */

public class Account {
    private static final String TAG="Account (Output)";

    private TextView mTextView;
    private TextView mTitleView;
    private Context  mContext;

    private String   title;
    private String   text;
    private Utils utils = new Utils();

    public Account(Context context, Integer name, Integer data) {
        title = utils.getString(context, name);
        text = utils.getString(context, data);
    }

    public String getTitleText() {
        return title;
    }

    public String getTextText() {
        return text;
    }

    public TextView getTitleView() {
        return mTitleView;
    }

    public TextView getTextView() {
        return mTextView;
    }


    public View createView(Context context, String title) {
        View view;

//        Log.d(TAG, "createView(LayoutInflater inflater, ViewGroup container, String title)");
//        Log.d(TAG, "    title: " + title);

//        TextView titleView;

        view = LayoutInflater.from(context).inflate(R.layout.help, null);

        mTitleView=(TextView) view.findViewById(R.id.title);
        mTitleView.setText(title);

        mTextView=(TextView) view.findViewById(R.id.text_area);
        mTextView.setText(text);

        return view;
    }
}
