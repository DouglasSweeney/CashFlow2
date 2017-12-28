package com.doug.cashflow.controller;

import com.doug.cashflow.BuildConfig;
import com.doug.cashflow.R;
import com.doug.cashflow.model.accounts.Taxes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({})
@Config(constants = BuildConfig.class)
public class CalculateUnitTest {
    private Calculate calculate;

    @Before
    public void setUp() throws Exception {
        calculate = new Calculate();
    }

    @After
    public void tearDown() throws Exception {
        calculate = null;
    }

    @Test
    public void preCondition() throws InterruptedException {
        assertNotNull(calculate);
    }
}
