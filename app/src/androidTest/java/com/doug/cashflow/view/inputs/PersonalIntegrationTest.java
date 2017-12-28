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
 * Created by Doug on 6/29/2017.
 */

public class PersonalIntegrationTest {
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

        integrationTestUtils.jumpToScreen(R.id.personal_menu_item);
    }

    @Test
    public void firstEntryDisplayed() {
        onView(withId(R.id.personal_simulation_date)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void retirementAgeChanged() {
        String originalValueString = integrationTestUtils.getOriginalIntegerValueFromDatabase(InputLab.getPersonal().getName(context), R.string.field_retirement_age);

        onView(withId(R.id.personal_retirement_age)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("65")).
                check(matches(withText(InputLab.getPersonal().getRetirementAge().toString())));

        onView(withId(R.id.personal_retirement_age)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalIntegerValueToDatabase(InputLab.getPersonal().getName(context), R.string.field_retirement_age, originalValueString);
    }

    @Test
    public void lifeExpectancyAgeChanged() {
        String originalValueString = integrationTestUtils.getOriginalIntegerValueFromDatabase(InputLab.getPersonal().getName(context), R.string.field_life_expectancy_age);

        onView(withId(R.id.personal_life_expectancy_age)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("90")).
                check(matches(withText(InputLab.getPersonal().getLifeExpectancyAge().toString())));

        onView(withId(R.id.personal_life_expectancy_age)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalIntegerValueToDatabase(InputLab.getPersonal().getName(context), R.string.field_life_expectancy_age, originalValueString);
    }

    @Test
    public void inflationChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getPersonal().getName(context), R.string.field_inflation);

        onView(withId(R.id.personal_inflation)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("3.3")).
                check(matches(withText(InputLab.getPersonal().getInflation().toString())));

        onView(withId(R.id.personal_inflation)).perform(scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getPersonal().getName(context), R.string.field_inflation, originalValueString);
    }

    @Test
    public void lastEntryDisplayed() {
        onView(withId(R.id.personal_inflation)).perform(closeSoftKeyboard(), scrollTo());

        onView(withId(R.id.personal_inflation)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void saveToDatabase() {
        String originalValueString = integrationTestUtils.getOriginalIntegerValueFromDatabase(InputLab.getPersonal().getName(context), R.string.field_retirement_age);

        onView(withId(R.id.personal_retirement_age)).perform(closeSoftKeyboard(), scrollTo(), replaceText("56"));

        InputLab.getPersonal().saveToDatabase(CashFlowDb.MY_2017_doug_USER_ID);

        String field = context.getString(R.string.field_retirement_age);
        String valueString = LoginLab.getIntegerData(CashFlowDb.MY_2017_doug_USER_ID, InputLab.getPersonal().getName(context), field);

        assertThat("56", is(valueString));

        onView(withId(R.id.personal_retirement_age)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getPersonal().getName(context), R.string.field_retirement_age, originalValueString);
    }

    @Test
    public void swipeLeftOnScreen() {

        onView(withId(R.id.container_personal)).perform(swipeLeft());

        onView(withId(R.id.salary_salary_label)).check(matches(isDisplayed()));
    }

    @Test
    public void swipeRightOnScreen() {

        onView(withId(R.id.container_personal)).perform(closeSoftKeyboard(), swipeRight());

        onView(withId(R.id.pension_starting_age_label)).check(matches(isDisplayed()));
    }
}
