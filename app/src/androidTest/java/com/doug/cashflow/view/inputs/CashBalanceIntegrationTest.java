package com.doug.cashflow.view.inputs;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.doug.cashflow.R;
import com.doug.cashflow.IntegrationTestUtils;
import com.doug.cashflow.model.db.CashFlowDb;
import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.LoginActivity;
import com.doug.cashflow.controller.LoginLab;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Doug on 6/23/2017.
 */

public class CashBalanceIntegrationTest {
    private Context context;
    private IntegrationTestUtils integrationTestUtils;

    @ClassRule
    public static ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(
            LoginActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    private void launchActivity() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
    }

    @Before
    public void setUp() throws InterruptedException {

        context = InstrumentationRegistry.getTargetContext();

        integrationTestUtils = new IntegrationTestUtils(context);

        launchActivity();

        // Enter some data into username/password fields
        onView(withId(R.id.email_username)).perform(replaceText(integrationTestUtils.getUsername()));
        onView(withId(R.id.password)).perform(replaceText(integrationTestUtils.getPassword()));

        onView(withId(R.id.password)).perform(closeSoftKeyboard());

        // Depress the "Login" Button
        onView(withId(R.id.button)).perform(click());

        integrationTestUtils.jumpToScreen(R.id.account_cash_balance_menu_item);
    }

    @Test
    public void firstEntryDisplayed() {
        // Verify that the "cash_balance" screen is displayed
        onView(withId(R.id.cash_balance_balance)).perform(scrollTo()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void balanceChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getCashBalance().getName(context), R.string.field_balance);

        onView(withId(R.id.cash_balance_balance)).
                perform(scrollTo(), replaceText("3000.0")).
                check(matches(withText(InputLab.getCashBalance().getBalance().toString())));

        onView(withId(R.id.cash_balance_balance)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getCashBalance().getName(context), R.string.field_balance, originalValueString);
    }

    @Test
    public void growthRateChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getCashBalance().getName(context), R.string.field_growth_rate);

        onView(withId(R.id.cash_balance_growth_rate)).
                perform(scrollTo(), replaceText("5.0")).
                check(matches(withText(InputLab.getCashBalance().getGrowthRate().toString())));

        onView(withId(R.id.cash_balance_growth_rate)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getCashBalance().getName(context), R.string.field_growth_rate, originalValueString);
    }

    @Test
    public void contributionsChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getCashBalance().getName(context), R.string.field_contributions);

        onView(withId(R.id.cash_balance_contributions)).
                perform(scrollTo(), replaceText("5000.0")).
                check(matches(withText(InputLab.getCashBalance().getContributions().toString())));

        onView(withId(R.id.cash_balance_contributions)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getCashBalance().getName(context), R.string.field_contributions, originalValueString);
    }

    @Test
    public void startWithdrawalsAgeChanged() {
        String originalValueString = integrationTestUtils.getOriginalIntegerValueFromDatabase(InputLab.getCashBalance().getName(context), R.string.field_start_withdrawals_age);

        onView(withId(R.id.cash_balance_start_withdrawals_age)).
                perform(scrollTo(), replaceText("62")).
                check(matches(withText(InputLab.getCashBalance().getStartWithdrawalsAge().toString())));

        onView(withId(R.id.cash_balance_start_withdrawals_age)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalIntegerValueToDatabase(InputLab.getCashBalance().getName(context), R.string.field_start_withdrawals_age, originalValueString);
    }

    @Test
    public void numberOfWithdrawalsChanged() {
        String originalValueString = integrationTestUtils.getOriginalIntegerValueFromDatabase(InputLab.getCashBalance().getName(context), R.string.field_number_of_withdrawals);

        onView(withId(R.id.cash_balance_number_of_withdrawals)).
                perform(scrollTo(), replaceText("15")).
                check(matches(withText(InputLab.getCashBalance().getNumberOfWithdrawals().toString())));

        onView(withId(R.id.cash_balance_number_of_withdrawals)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalIntegerValueToDatabase(InputLab.getCashBalance().getName(context), R.string.field_number_of_withdrawals, originalValueString);
    }

    @Test
    public void lastEntryDisplayed() {
        // Verify that the full "401K" screen is displayed
        onView(withId(R.id.cash_balance_number_of_withdrawals)).perform(scrollTo(), closeSoftKeyboard());

        onView(withId(R.id.cash_balance_number_of_withdrawals)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void saveToDatabase() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getCashBalance().getName(context), R.string.field_balance);

        onView(withId(R.id.cash_balance_balance)).perform(scrollTo(), replaceText("1,234.50"));

        InputLab.getCashBalance().saveToDatabase(CashFlowDb.MY_2017_doug_USER_ID);

        String field = context.getString(R.string.field_balance);
        String valueString = LoginLab.getRealData(CashFlowDb.MY_2017_doug_USER_ID, InputLab.getCashBalance().getName(context), field);

        assertThat("1,234.50", is(valueString));

        onView(withId(R.id.cash_balance_balance)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getCashBalance().getName(context), R.string.field_balance, originalValueString);
    }

    @Test
    public void swipeLeftCashBalance() {

        onView(withId(R.id.container_cash_balance)).perform(swipeLeft());

        onView(withId(R.id.deductions_deductions_label)).perform(scrollTo()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void swipeRightCashBalance() {

        onView(withId(R.id.container_cash_balance)).perform(swipeRight());

        // Verify that a cash_balance field isDisplayed; basically the screen hasn't moved
        onView(withId(R.id.brokerage_balance)).perform(scrollTo()).check(matches(isDisplayed()));
    }
}
