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
 * Created by Doug on 6/27/2017.
 */

public class SavingsIntegrationTest {
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

        integrationTestUtils.jumpToScreen(R.id.savings_menu_item);
    }

    @Test
    public void firstEntryDisplayed() {
        // Verify that the "savings" screen is displayed
        onView(withId(R.id.savings_balance_label)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void balanceChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getSavings().getName(context), R.string.field_balance);

        onView(withId(R.id.savings_balance)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("3000.0")).
                check(matches(withText(InputLab.getSavings().getBalance().toString())));

        onView(withId(R.id.savings_balance)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getSavings().getName(context), R.string.field_balance, originalValueString);
    }

    @Test
    public void growthRateChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getSavings().getName(context), R.string.field_growth_rate);

        onView(withId(R.id.savings_growth_rate)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("5.0")).
                check(matches(withText(InputLab.getSavings().getGrowthRate().toString())));

        onView(withId(R.id.savings_growth_rate)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getSavings().getName(context), R.string.field_growth_rate, originalValueString);
    }

    @Test
    public void lastEntryDisplayed() {
        // Verify that the full screen is displayed
        onView(withId(R.id.savings_growth_rate)).perform(closeSoftKeyboard(), scrollTo());

        onView(withId(R.id.savings_growth_rate)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void saveToDatabase() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getSavings().getName(context), R.string.field_balance);

        onView(withId(R.id.savings_balance)).perform(closeSoftKeyboard(), scrollTo(), replaceText("1,234.50"));

        InputLab.getSavings().saveToDatabase(CashFlowDb.MY_2017_doug_USER_ID);

        String field = context.getString(R.string.field_balance);
        String valueString = LoginLab.getRealData(CashFlowDb.MY_2017_doug_USER_ID, InputLab.getSavings().getName(context), field);

        assertThat("1,234.50", is(valueString));

        onView(withId(R.id.savings_balance)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getSavings().getName(context), R.string.field_balance, originalValueString);
    }

    @Test
    public void swipeLeftScreen() {

        onView(withId(R.id.container_savings)).perform(swipeLeft());
        
        onView(withId(R.id.social_security_starting_age_label)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void swipeRightScreen() {

        onView(withId(R.id.container_savings)).perform(swipeRight());

        // Verify that a savings field isDisplayed; basically the screen hasn't moved
        onView(withId(R.id.salary_salary_label)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isDisplayed()));
    }
}