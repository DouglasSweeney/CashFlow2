package com.doug.cashflow.controller;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.doug.cashflow.R;
import com.doug.cashflow.model.db.CashFlowDb;
import com.doug.cashflow.IntegrationTestUtils;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class InputFragmentIntegrationTest {
    private Context context;
    private IntegrationTestUtils integrationTestUtils;

    @ClassRule
    public static ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(
            LoginActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    public void launchActivity() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
    }

    @Before
    public void setUp() {

        context = InstrumentationRegistry.getTargetContext();

        integrationTestUtils = new IntegrationTestUtils(context);

        launchActivity();

        // Enter some data into username/password fields
        onView(withId(R.id.email_username)).perform(replaceText(integrationTestUtils.getUsername()));
        onView(withId(R.id.password)).perform(replaceText(integrationTestUtils.getPassword()));

        onView(withId(R.id.password)).perform(closeSoftKeyboard());

        // Depress the "Login" Button
        onView(withId(R.id.button)).perform(click());
    }

    @Test
    public void actionBarActionsSave() {
        String originalValueString = integrationTestUtils.getOriginalIntegerValueFromDatabase(InputLab.getAccount401k().getName(context), R.string.field_start_withdrawals_age);

        // modify a value to save
        onView(withId(R.id.account_401k_start_withdrawals_age)).perform(closeSoftKeyboard(), scrollTo(), replaceText("67"));

        // Click on the action bar
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        // click on the menu item
        onView(withText(context.getString(R.string.actions))).perform(click());

        // click on the sub menu item
        onView(withText(context.getString(R.string.save_inputs))).perform(click());

        String field = context.getString(R.string.field_start_withdrawals_age);
        String valueString = LoginLab.getIntegerData(CashFlowDb.MY_2017_doug_USER_ID, InputLab.getAccount401k().getName(context), field);

        assertThat("67", is(valueString));

        // restore the original string
        onView(withId(R.id.account_401k_start_withdrawals_age)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalIntegerValueToDatabase(InputLab.getAccount401k().getName(context), R.string.field_start_withdrawals_age, originalValueString);
    }

    @Test
    public void actionBarActionsOutputs() {
        // Click on the action bar
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        // click on the menu item
        onView(withText(context.getString(R.string.actions))).perform(click());

        // click on the sub menu item
        onView(withText(context.getString(R.string.outputs))).perform(click());

        // restore the original string
        onView(withId(integrationTestUtils.getIdForDefaultOutputsScreen())).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarActionsHelp() {
        // Click on the action bar
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        // click on the menu item
        onView(withText(context.getString(R.string.actions))).perform(click());

        // click on the sub menu item
        onView(withText(context.getString(R.string.help))).perform(click());

        onView(allOf(withId(R.id.title), isDisplayed())).
                check(matches(withText(startsWith(context.getString(R.string.help_input_header_401k)))));
    }

    @Test
    public void actionBarActionsAboutLicenseCashFlow() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString(R.string.actions))).perform(click());

        onView(withText(context.getString(R.string.about))).perform(click());

        onView(withText(context.getString(R.string.license_cash_flow))).perform(click());

        onView(withId(R.id.filename)).
                check(matches(withText(startsWith("Copyright 2017"))));
    }

    @Test
    public void actionBarActionsAboutLicenseAndroidPlot() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString(R.string.actions))).perform(click());

        onView(withText(context.getString(R.string.about))).perform(click());

        onView(withText(context.getString(R.string.license_android_plot))).perform(click());

        onView(withId(R.id.filename)).
                check(matches(withText(startsWith("Copyright 2017"))));
    }

    @Test
    public void actionBarActionsAboutLicenseApachev2() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString(R.string.actions))).perform(click());

        onView(withText(context.getString(R.string.about))).perform(click());

        onView(withText(context.getString(R.string.license_apache_v2))).perform(click());

        onView(withId(R.id.filename)).check(matches(isDisplayed()));
    }

    @Test
    public void actionBarActionsLogin() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString(R.string.actions))).perform(click());

        onView(withText(context.getString(R.string.login))).perform(click());

        onView(withId(R.id.email_username_label)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJump401kInput() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString((R.string.jump)))).perform(click());

        onView(withText(context.getString(R.string.input_header_401k))).perform(click());

        onView(withId(R.id.account_401k_balance))
                .perform(closeSoftKeyboard())
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJump403bInput() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString((R.string.jump)))).perform(click());

        onView(withText(context.getString(R.string.input_header_403b))).perform(click());

        onView(withId(R.id.account_403b_growth_rate))
                .perform(closeSoftKeyboard())
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpBrokerageInput() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString((R.string.jump)))).perform(click());

        onView(withText(context.getString(R.string.input_header_brokerage))).perform(click());

        onView(withId(R.id.brokerage_growth_rate))
                .perform(closeSoftKeyboard())
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpCashBalanceInput() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString((R.string.jump)))).perform(click());

        onView(withText(context.getString(R.string.input_header_cash_balance))).perform(click());

        onView(withId(R.id.cash_balance_growth_rate_label))
                .perform(closeSoftKeyboard())
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpDeductionsInput() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString((R.string.jump)))).perform(click());

        onView(withText(context.getString(R.string.input_header_deductions))).perform(click());

        onView(withId(R.id.deductions_deductions_label))
                .perform(closeSoftKeyboard())
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpExpensesInput() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString((R.string.jump)))).perform(click());

        onView(withText(context.getString(R.string.input_header_expenses))).perform(click());

        onView(withId(R.id.expenses_expense_label))
                .perform(closeSoftKeyboard())
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpIraRothInput() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString((R.string.jump)))).perform(click());

        onView(withText(context.getString(R.string.input_header_ira_roth))).perform(click());

        onView(withId(R.id.roth_ira_contributions_label))
                .perform(closeSoftKeyboard())
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpIraTraditionalInput() {
        openActionBarOverflowOrOptionsMenu(LoginLab.getCurrentActivity());

        onView(withText(context.getString((R.string.jump)))).perform(click());

        onView(withText(context.getString(R.string.input_header_ira_traditional))).perform(click());

        onView(withId(R.id.traditional_ira_contributions_label))
                .perform(closeSoftKeyboard())
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpPensionInput() {
        IntegrationTestUtils integrationTestUtils = new IntegrationTestUtils(context);

        integrationTestUtils.jumpToScreen(R.id.pension_menu_item);

        onView(withId(R.id.pension_monthly_amount_label))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpPersonalInput() {
        IntegrationTestUtils integrationTestUtils = new IntegrationTestUtils(context);

        integrationTestUtils.jumpToScreen(R.id.personal_menu_item);

        onView(withId(R.id.personal_simulation_date_label)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpSalaryInput() {
        IntegrationTestUtils integrationTestUtils = new IntegrationTestUtils(context);

        integrationTestUtils.jumpToScreen(R.id.salary_menu_item);

        onView(withId(R.id.salary_salary_label)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpSavingsInput() {
        IntegrationTestUtils integrationTestUtils = new IntegrationTestUtils(context);

        integrationTestUtils.jumpToScreen(R.id.savings_menu_item);

        onView(withId(R.id.savings_balance_label)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpSocialSecurityInput() {
        IntegrationTestUtils integrationTestUtils = new IntegrationTestUtils(context);

        integrationTestUtils.jumpToScreen(R.id.social_security_menu_item);

        onView(withId(R.id.social_security_starting_age)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void actionBarJumpTaxesInput() {
        IntegrationTestUtils integrationTestUtils = new IntegrationTestUtils(context);

        integrationTestUtils.jumpToScreen(R.id.taxes_menu_item);

        onView(withId(R.id.taxes_state_tax_rate_label)).check(matches(isCompletelyDisplayed()));
    }
}
