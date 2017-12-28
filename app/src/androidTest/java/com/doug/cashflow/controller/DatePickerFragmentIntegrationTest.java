package com.doug.cashflow.controller;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.doug.cashflow.IntegrationTestUtils;
import com.doug.cashflow.R;
import com.doug.cashflow.model.system.ResultsDataNode;
import com.doug.cashflow.view.inputs.Personal;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.CharMatcher.isNot;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class DatePickerFragmentIntegrationTest {
    private DatePickerFragment datePickerFragment;

    private Context context;
    private IntegrationTestUtils integrationTestUtils;
    private AppCompatActivity activity;
    private String dateString;

    @ClassRule
    public static ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(
            LoginActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    public void launchActivity() {
        Intent intent = new Intent();
        activity = activityRule.launchActivity(intent);
    }

    @Before
    public void setUp() {
/*
        context = InstrumentationRegistry.getTargetContext();

        integrationTestUtils = new IntegrationTestUtils(context);

        launchActivity();

        // Enter some data into username/password fields
        onView(withId(R.id.email_username)).perform(replaceText(integrationTestUtils.getUsername()));
        onView(withId(R.id.password)).perform(replaceText(integrationTestUtils.getPassword()));

        onView(withId(R.id.password)).perform(closeSoftKeyboard());

        // Depress the "Login" Button
        onView(withId(R.id.button)).perform(click());

        LoginLab.getInstance(context, false);
        LoginLab.setCurrentActivity(activity);

        InputLab.getInstance(context, activity.getSupportFragmentManager(), null);
*/
        datePickerFragment = null;
    }

    @Test
    public void preCondition() throws InterruptedException {
        Date date = new Date();

        final Calendar calendar = Calendar.getInstance();

        // set time before today
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, 11); // zero-based
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        date.setTime(calendar.getTime().getTime());

        datePickerFragment = DatePickerFragment.newInstance(date, "Title");

        assertNotNull(datePickerFragment);
    }
}
