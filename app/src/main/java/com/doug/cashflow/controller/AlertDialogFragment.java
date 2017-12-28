package com.doug.cashflow.controller;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.doug.cashflow.R;

public class AlertDialogFragment extends DialogFragment {
    static TextView errorView;
    static Bundle args = new Bundle();
    static Activity activity;
    public static final String ARG_CATEGORY = "ARG_CATEGORY";
    public static final String ARG_FIELD = "ARG_FIELD";

    public static AlertDialogFragment newInstance(Integer category, Integer field, Integer extra) {
        String categoryString;
        String fieldString;
        String extraString;

        //       args.putSerializable(ARG_CATEGORY, error);
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.setRetainInstance(true);
        //        dialog.setArguments(args);

        errorView = (TextView)LayoutInflater.from(LoginLab.getCurrentActivity()).inflate(R.layout.error_string, null);

        categoryString = LoginLab.getCurrentActivity().getResources().getString(category);
        fieldString = LoginLab.getCurrentActivity().getResources().getString(field);
        extraString = LoginLab.getCurrentActivity().getResources().getString(extra);
        errorView.setText("\n" + categoryString + ": " + fieldString + " " + extraString);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        activity = getActivity();
        return new AlertDialog.Builder(LoginLab.getCurrentActivity())
                .setTitle(R.string.errorTitle)
                .setView(errorView)
                .setPositiveButton(R.string.ok, null)
                .create();
    }
}