package com.doug.cashflow.model.loaders;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.LoginActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Doug on 6/4/2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class InputLabLoadingTaskIntegrationTest {
    // SUT
    private InputLabLoadingTask inputLabLoadingTask;

    private Context context;

    @ClassRule
    public static ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(
            LoginActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    @BeforeClass
    public static void launchActivity() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
    }

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();

        inputLabLoadingTask = new InputLabLoadingTask(context, null);
    }

    @Test
    public void preConditions() {
        Assert.assertNotNull(inputLabLoadingTask);
    }
}
