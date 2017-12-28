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

public class DeductionsIntegrationTest {
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

        integrationTestUtils.jumpToScreen(R.id.deductions_menu_item);
    }

    @Test
    public void firstEntryDisplayed() {
        onView(withId(R.id.deductions_deductions_label)).perform(scrollTo()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void deductionsChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getDeductions().getName(context), R.string.field_yearly_deductions);

        onView(withId(R.id.deductions_deductions)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("3000.0")).
                check(matches(withText(InputLab.getDeductions().getDeductions().toString())));

        onView(withId(R.id.deductions_deductions)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getDeductions().getName(context), R.string.field_yearly_deductions, originalValueString);
    }

    @Test
    public void lastEntryDisplayed() {
        onView(withId(R.id.deductions_deductions_label)).perform(scrollTo()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void saveToDatabase() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getDeductions().getName(context), R.string.field_yearly_deductions);

        onView(withId(R.id.deductions_deductions)).perform(closeSoftKeyboard(), scrollTo(), replaceText("1,234.50"));

        InputLab.getDeductions().saveToDatabase(CashFlowDb.MY_2017_doug_USER_ID);

        String field = context.getString(R.string.field_yearly_deductions);
        String valueString = LoginLab.getRealData(CashFlowDb.MY_2017_doug_USER_ID, InputLab.getDeductions().getName(context), field);

        assertThat("1,234.50", is(valueString));

        onView(withId(R.id.deductions_deductions)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getDeductions().getName(context), R.string.field_balance, originalValueString);
    }

    @Test
    public void swipeLeftDeductions() {

        onView(withId(R.id.container_deductions)).perform(swipeLeft());

        // Verify that a expenses screen isDisplayed
        onView(withId(R.id.expenses_expense_label)).check(matches(isDisplayed()));
    }

    @Test
    public void swipeRightDeductions() {

        onView(withId(R.id.container_deductions)).perform(swipeRight());

        onView(withId(R.id.cash_balance_balance_label)).check(matches(isDisplayed()));
    }
}
