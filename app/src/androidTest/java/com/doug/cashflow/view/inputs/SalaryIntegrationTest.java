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

public class SalaryIntegrationTest {
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

        integrationTestUtils.jumpToScreen(R.id.salary_menu_item);
    }

    @Test
    public void firstEntryDisplayed() {
        onView(withId(R.id.salary_salary)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void salaryChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getSalary().getName(context), R.string.field_salary);

        onView(withId(R.id.salary_salary)).perform(closeSoftKeyboard(), scrollTo(), replaceText("10.0")).
                check(matches(withText(InputLab.getSalary().getSalary().toString())));

        onView(withId(R.id.salary_salary)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getSalary().getName(context), R.string.field_salary, originalValueString);
    }

    @Test
    public void meritIncreaseChanged() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getSalary().getName(context), R.string.field_merit_increase);

        onView(withId(R.id.salary_merit_increase)).
                perform(closeSoftKeyboard(), scrollTo(), replaceText("5.0")).
                check(matches(withText(InputLab.getSalary().getMeritIncrease().toString())));

        onView(withId(R.id.salary_merit_increase)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getSalary().getName(context), R.string.field_merit_increase, originalValueString);
    }

    @Test
    public void lastEntryDisplayed() {
        onView(withId(R.id.salary_merit_increase)).perform(closeSoftKeyboard(), scrollTo());

        onView(withId(R.id.salary_merit_increase)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void saveToDatabase() {
        String originalValueString = integrationTestUtils.getOriginalRealValueFromDatabase(InputLab.getSalary().getName(context), R.string.field_salary);

        onView(withId(R.id.salary_salary)).perform(closeSoftKeyboard(), scrollTo(), replaceText("50.0"));

        InputLab.getSalary().saveToDatabase(CashFlowDb.MY_2017_doug_USER_ID);

        String field = context.getString(R.string.field_salary);
        String valueString = LoginLab.getRealData(CashFlowDb.MY_2017_doug_USER_ID, InputLab.getSalary().getName(context), field);

        assertThat("50.00", is(valueString));

        onView(withId(R.id.salary_salary)).perform(closeSoftKeyboard(), scrollTo(), replaceText(originalValueString));
        integrationTestUtils.restoreOriginalRealValueToDatabase(InputLab.getSalary().getName(context), R.string.field_salary, originalValueString);
    }

    @Test
    public void swipeLeftOnScreen() {

        onView(withId(R.id.container_salary)).perform(closeSoftKeyboard(), swipeLeft());

        onView(withId(R.id.savings_balance_label)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isDisplayed()));
    }

    @Test
    public void swipeRightOnScreen() {

        onView(withId(R.id.container_salary)).perform(closeSoftKeyboard(), swipeRight());

        onView(withId(R.id.personal_simulation_date)).perform(closeSoftKeyboard(), scrollTo()).check(matches(isDisplayed()));
    }
}
