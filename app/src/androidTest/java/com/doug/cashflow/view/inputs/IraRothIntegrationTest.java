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

public class IraRothIntegrationTest {
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

        integrationTestUtils.jumpToScreen(R.id.account_roth_ira_menu_item);
    }

    @Test
    public void firstEntryDisplayed() {
        onView(withId(R.id.roth_ira_balance)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void balanceChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getRothIra().getName(context), R.string.field_balance);

        onView(withId(R.id.roth_ira_balance)).perform(replaceText("3000.0")).
                check(matches(withText(InputLab.getRothIra().getBalance().toString())));

        onView(withId(R.id.roth_ira_balance)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getRothIra().getName(context), R.string.field_balance, originalValueString);
    }

    @Test
    public void growthRateChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getRothIra().getName(context), R.string.field_growth_rate);

        onView(withId(R.id.roth_ira_growth_rate)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("5.0")).
                check(matches(withText(InputLab.getRothIra().getGrowthRate().toString())));

        onView(withId(R.id.roth_ira_growth_rate)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getRothIra().getName(context), R.string.field_growth_rate, originalValueString);
    }

    @Test
    public void contributionsChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getRothIra().getName(context), R.string.field_contributions);

        onView(withId(R.id.roth_ira_contributions)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("5000.0")).
                check(matches(withText(InputLab.getRothIra().getContributions().toString())));

        onView(withId(R.id.roth_ira_contributions)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getRothIra().getName(context), R.string.field_contributions, originalValueString);
    }

    @Test
    public void startWithdrawalsAgeChanged() {
        String originalValueString = integrationTestUtils.getOriginalIntegerValueFromDatabase(InputLab.getRothIra().getName(context), R.string.field_start_withdrawals_age);

        onView(withId(R.id.roth_ira_start_withdrawals_age)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("62")).
                check(matches(withText(InputLab.getRothIra().getStartWithdrawalsAge().toString())));

        onView(withId(R.id.roth_ira_start_withdrawals_age)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalIntegerValueToDatabase(InputLab.getRothIra().getName(context), R.string.field_start_withdrawals_age, originalValueString);
    }

    @Test
    public void numberOfWithdrawalsChanged() {
        String originalValueString = integrationTestUtils.getOriginalIntegerValueFromDatabase(InputLab.getRothIra().getName(context), R.string.field_number_of_withdrawals);

        onView(withId(R.id.roth_ira_number_of_withdrawals)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("15")).
                check(matches(withText(InputLab.getRothIra().getNumberOfWithdrawals().toString())));

        onView(withId(R.id.roth_ira_number_of_withdrawals)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalIntegerValueToDatabase(InputLab.getRothIra().getName(context), R.string.field_number_of_withdrawals, originalValueString);
    }

    @Test
    public void lastEntryDisplayed() {
        // Verify that the full "401K" screen is displayed
        onView(withId(R.id.roth_ira_number_of_withdrawals)).perform(closeSoftKeyboard(), scrollTo());

        onView(withId(R.id.roth_ira_number_of_withdrawals)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void saveToDatabase() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getRothIra().getName(context), R.string.field_balance);

        onView(withId(R.id.roth_ira_balance)).perform(closeSoftKeyboard(), scrollTo(), replaceText("1,234.50"));

        InputLab.getRothIra().saveToDatabase(CashFlowDb.MY_2017_doug_USER_ID);

        String field = context.getString(R.string.field_balance);
        String valueString = LoginLab.getRealData(CashFlowDb.MY_2017_doug_USER_ID, InputLab.getRothIra().getName(context), field);

        assertThat("1,234.50", is(valueString));

        onView(withId(R.id.roth_ira_balance)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getRothIra().getName(context), R.string.field_balance, originalValueString);
    }

    @Test
    public void swipeLeftOnScreen() {

        onView(withId(R.id.container_ira_roth)).perform(closeSoftKeyboard(), swipeLeft());

        onView(withId(R.id.traditional_ira_balance_label)).check(matches(isDisplayed()));
    }

    @Test
    public void swipeRightOnScreen() {

        onView(withId(R.id.container_ira_roth)).perform(closeSoftKeyboard(), swipeRight());

        onView(withId(R.id.expenses_expense_label)).check(matches(isDisplayed()));
    }
}
