package com.doug.cashflow.view.outputs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class UtilsUnitTest {

    private Utils utils;

    @Before
    public void setUp() throws Exception {

        utils = new Utils();
    }

    @After
    public void tearDown() throws Exception {
        utils = null;
    }

    @Test
    public void preCondition() throws Exception {

        assertNotNull(utils);
    }

    @Test
    public void validStripCents() throws Exception {

        String string = utils.stripCents(123.45);
//        string = string.replaceAll(" ", "");

        assertThat("123", is(string));
        assertThat("123.45", is(not(string)));
    }

    @Test
    public void validStripCentsNegative() throws Exception {

        String string = utils.stripCents(-123.45);
//        string = string.replaceAll(" ", "");

        assertThat("-123", is(string));
        assertThat("-123.45", is(not(string)));
    }

    @Test
    public void validStripCentsNoCentsInput() throws Exception {

        String string = utils.stripCents(123.00);
//        string = string.replaceAll(" ", "");

        assertThat("123", is(string));
        assertThat("123.00", is(not(string)));
    }

    @Test
    public void validStripCentsLeadingZeros() throws Exception {

        String string = utils.stripCents(0123.45);
//        string = string.replaceAll(" ", "");

        assertThat("123", is(string));
        assertThat("0123.45", is(not(string)));
    }
}
