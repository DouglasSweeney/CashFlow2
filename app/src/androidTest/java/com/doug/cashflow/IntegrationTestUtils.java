package com.doug.cashflow;

import android.content.Context;

import com.doug.cashflow.controller.LoginLab;
import com.doug.cashflow.model.db.CashFlowDb;
import com.doug.cashflow.view.inputs.Util;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

public class IntegrationTestUtils {
    Context   context;

    public IntegrationTestUtils(Context context) {
        this.context = context;
    }

    public String getOriginalRealValueFromDatabase(String header, Integer fieldIndex) {
        String field = context.getString(fieldIndex);
        String originalValueString = LoginLab.getRealData(getUserIdForTests(), header, field);

        return originalValueString;
    }

    public String getOriginalIntegerValueFromDatabase(String header, Integer fieldIndex) {
        String field = context.getString(fieldIndex);
        String originalValueString = LoginLab.getIntegerData(getUserIdForTests(), header, field);

        return originalValueString;
    }

    public void restoreOriginalRealValueToDatabase(String header, Integer field, String originalValueString) {
        Util util = new Util();

        String modifiedValueString = util.stripCommas(originalValueString);

        LoginLab.updateData(getUserIdForTests(), header, field, Float.valueOf(modifiedValueString));
    }

    public void restoreOriginalIntegerValueToDatabase(String header, Integer field, String originalValueString) {
        Util util = new Util();

        String modifiedValueString = util.stripCommas(originalValueString);

        LoginLab.updateData(getUserIdForTests(), header, field, Integer.valueOf(modifiedValueString));
    }

    public String getUsername() {
        return "2017_doug";
    }

    public String getPassword() {
        return "2017_doug";
    }

    public Integer getIdForDefaultOutputsScreen() {
        return R.id.plot;
    }

    public Integer getIdForHelpScreenTitle() { return R.id.title; }

    public Integer getUserIdForTests() {
        return CashFlowDb.MY_2017_doug_USER_ID;
    }

    // Get the index from the menu/input_menu.xml
    private Integer getIndexForJumpMenu(Integer jumpIndex) {
        Integer index;

        int[][] map = {
                {R.id.account_401k_menu_item, 0},
                {R.id.account_403b_menu_item, 1},
                {R.id.brokerage_menu_item, 2},
                {R.id.account_cash_balance_menu_item, 3},
                {R.id.deductions_menu_item, 4},
                {R.id.expenses_menu_item, 5},
                {R.id.account_roth_ira_menu_item, 6},
                {R.id.account_traditional_ira_menu_item, 7},
                {R.id.pension_menu_item, 8},
                {R.id.personal_menu_item, 9},
                {R.id.salary_menu_item, 10},
                {R.id.savings_menu_item, 11},
                {R.id.social_security_menu_item, 12},
                {R.id.taxes_menu_item, 13}
        };

        for (index = 0; index < map.length; index++) {
            if (map[index][0] == jumpIndex) {
                break;
            }
        }

        return index;
    }

    // Assumes the system is on the input 401k screen
    public void jumpToScreen(Integer jumpIdIndex) {
        int index = 0;

        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString((R.string.jump)))).perform(click());

        index = getIndexForJumpMenu(jumpIdIndex);

        onData(anything()).inAdapterView(withId(R.id.select_dialog_listview)).atPosition(index).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
