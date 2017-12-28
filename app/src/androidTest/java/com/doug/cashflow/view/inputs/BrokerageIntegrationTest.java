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

public class BrokerageIntegrationTest {
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

        integrationTestUtils.jumpToScreen(R.id.brokerage_menu_item);
    }

    @Test
    public void firstEntryDisplayed() {
        // Verify that the "brokerage" screen is displayed
        onView(withId(R.id.brokerage_balance)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void balanceChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getBrokerage().getName(context), R.string.field_balance);

        onView(withId(R.id.brokerage_balance)).
                perform(scrollTo(), replaceText("3000.0")).
                check(matches(withText(InputLab.getBrokerage().getBalance().toString())));

        onView(withId(R.id.brokerage_balance)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getBrokerage().getName(context), R.string.field_balance, originalValueString);
    }

    @Test
    public void growthRateChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getBrokerage().getName(context), R.string.field_growth_rate);

        onView(withId(R.id.brokerage_growth_rate)).
                perform(scrollTo(), replaceText("5.0")).
                check(matches(withText(InputLab.getBrokerage().getGrowthRate().toString())));

        onView(withId(R.id.brokerage_growth_rate)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getBrokerage().getName(context), R.string.field_growth_rate, originalValueString);
    }

    @Test
    public void lastEntryDisplayed() {
        // Verify that the full screen is displayed
        onView(withId(R.id.brokerage_growth_rate)).perform(scrollTo(), closeSoftKeyboard());

        onView(withId(R.id.brokerage_growth_rate)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void saveToDatabase() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getBrokerage().getName(context), R.string.field_balance);

        onView(withId(R.id.brokerage_balance)).perform(scrollTo(), replaceText("1,234.50"));

        InputLab.getBrokerage().saveToDatabase(CashFlowDb.MY_2017_doug_USER_ID);

        String field = context.getString(R.string.field_balance);
        String valueString = LoginLab.getRealData(CashFlowDb.MY_2017_doug_USER_ID, InputLab.getBrokerage().getName(context), field);

        assertThat("1,234.50", is(valueString));

        onView(withId(R.id.brokerage_balance)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getBrokerage().getName(context), R.string.field_balance, originalValueString);
    }

    @Test
    public void swipeLeftBrokerage() {

        onView(withId(R.id.container_brokerage)).perform(swipeLeft());

        // Verify that a brokerage screen isDisplayed
        onView(withId(R.id.cash_balance_balance)).check(matches(isDisplayed()));
    }

    @Test
    public void swipeRightBrokerage() {

        onView(withId(R.id.container_brokerage)).perform(swipeRight());

        // Verify that a brokerage field isDisplayed; basically the screen hasn't moved
        onView(withId(R.id.account_403b_balance)).check(matches(isDisplayed()));
    }
}
