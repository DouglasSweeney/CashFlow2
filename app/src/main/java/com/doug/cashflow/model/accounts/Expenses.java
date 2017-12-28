package com.doug.cashflow.model.accounts;

import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.model.system.ResultsDataNode;

import static com.doug.cashflow.model.db.CashFlowDb.MY_2017_doug_USER_ID;

public class Expenses extends Account {
//    private static final String TAG = "ExpensesModel";

    public Expenses(double expense, double growthRate, int currentAge, int currentYear, int deathAge) {
    	
        initialize(expense, growthRate, currentAge, currentYear, deathAge);
        
        /* Force age 59 to have a hardcoded expense */
//        Log.d(TAG, "InputLab.getUserId(): " + InputLab.getUserId());
//        Log.d(TAG, "My_2017_doug_USER_ID: " + MY_2017_doug_USER_ID);
/*        if (InputLab.getUserId().equals(MY_2017_doug_USER_ID)) {
            ResultsDataNode node;

            node = getNodeBasedOnAge(59);
            if (node != null) {
                node.setBeginningValue(32500);
                node.setEndingValue(32500);
            }
        }
        */
    }
}
