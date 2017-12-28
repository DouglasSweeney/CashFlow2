package com.doug.cashflow.model.accounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class ExpensesUnitTest {
    private Account account;

    @Before
    public void setUp() throws Exception {

        account = null;
    }

    @After
    public void tearDown() throws Exception {
        account = null;
    }

    @Test
    public void checkConstructor() throws Exception {
        Double  balance = 100.0;
        Double  growthRate = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;

        account = new Expenses(balance, growthRate, currentAge, currentYear, deathAge);

        balance = account.getList().get(0).getBeginningValue();
        assertThat(100.0, closeTo(balance, 0.0001));

        balance = account.getList().get(0).getEndingValue();
        assertThat(105.0, closeTo(balance, 0.0001));
    }
}
