package com.doug.cashflow.controller;

import android.content.Context;
import android.content.Intent;

import com.doug.cashflow.model.system.ValidateInputs;

/**
 * Created by Doug on 5/29/2017.
 */

public class Utils {

    public void goToInputsActivity() {
        Intent intent2 = new Intent(LoginLab.getCurrentActivity(), InputPagerActivity.class);
        LoginLab.getCurrentActivity().startActivity(intent2);
    }

    protected void goToOutputsActivity() {
        Context context = LoginLab.getCurrentActivity().getApplicationContext();
        ValidateInputs validateInputs = new ValidateInputs(context);

        if (validateInputs.validate() == true) {
            Intent intent = new Intent(LoginLab.getCurrentActivity(), OutputPagerActivity.class);
            LoginLab.getCurrentActivity().startActivity(intent);
        }
    }

    public void goToHelpActivity() {
        Intent intent = new Intent(LoginLab.getCurrentActivity(), HelpPagerActivity.class);
        LoginLab.getCurrentActivity().startActivity(intent);
    }

    protected void goToAssetsLicensesActivity(Context context, String directory, String filename) {
        Intent intent4 = FileReadActivity.newIntent(LoginLab.getCurrentActivity().getApplicationContext(),
                FileReadActivity.LICENSE_DIRECTORY, "cashflow.txt");
        LoginLab.getCurrentActivity().startActivity(intent4);
    }

    public void goToLoginActivity() {
        Intent intent3 = new Intent(LoginLab.getCurrentActivity(), LoginActivity.class);
        LoginLab.getCurrentActivity().startActivity(intent3);
    }
}
