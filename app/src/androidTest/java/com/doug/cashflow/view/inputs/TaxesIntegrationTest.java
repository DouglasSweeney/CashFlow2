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
 * Created by Doug on 6/28/2017.
 */

public class TaxesIntegrationTest {
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

        integrationTestUtils.jumpToScreen(R.id.taxes_menu_item);
    }

    @Test
    public void firstEntryDisplayed() {
        onView(withId(R.id.taxes_federal_tax_rate)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void federalRateChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getTaxes().getName(context), R.string.field_federal_tax_rate);

        onView(withId(R.id.taxes_federal_tax_rate)).perform(closeSoftKeyboard(), scrollTo(), replaceText("28.0")).
                check(matches(withText(InputLab.getTaxes().getFederalTaxRate().toString())));

        onView(withId(R.id.taxes_federal_tax_rate)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getTaxes().getName(context), R.string.field_federal_tax_rate, originalValueString);
    }

    @Test
    public void stateRateChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getTaxes().getName(context), R.string.field_state_tax_rate);

        onView(withId(R.id.taxes_state_tax_rate)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("6.0")).
                check(matches(withText(InputLab.getTaxes().getStateTaxRate().toString())));

        onView(withId(R.id.taxes_state_tax_rate)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getTaxes().getName(context), R.string.field_state_tax_rate, originalValueString);
    }

    @Test
    public void lastEntryDisplayed() {
        onView(withId(R.id.taxes_state_tax_rate)).perform(closeSoftKeyboard(), scrollTo());

        onView(withId(R.id.taxes_state_tax_rate)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void saveToDatabase() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getTaxes().getName(context), R.string.field_federal_tax_rate);

        onView(withId(R.id.taxes_federal_tax_rate)).perform(closeSoftKeyboard(), scrollTo(), replaceText("28.0"));

        InputLab.getTaxes().saveToDatabase(CashFlowDb.MY_2017_doug_USER_ID);

        String field = context.getString(R.string.field_federal_tax_rate);
        String valueString = LoginLab.getRealData(CashFlowDb.MY_2017_doug_USER_ID, InputLab.getTaxes().getName(context), field);

        assertThat("28.0", is(valueString));

        onView(withId(R.id.taxes_federal_tax_rate)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getTaxes().getName(context), R.string.field_federal_tax_rate, originalValueString);
    }

    @Test
    public void swipeLeftOnScreen() {

        onView(withId(R.id.container_taxes)).perform(closeSoftKeyboard(), swipeLeft());

        onView(withId(R.id.taxes_federal_tax_rate_label)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void swipeRightOnScreen() {

        onView(withId(R.id.container_taxes)).perform(closeSoftKeyboard(), swipeRight());

        onView(withId(R.id.social_security_starting_age_label)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isDisplayed()));
    }
}
