package com.doug.cashflow.model.accounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class SavingsUnitTest {
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
    public void checkDeposit() throws Exception {
        Double  balance = 100.0;
        Double  growthRate = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;

        account = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        balance = account.getList().get(0).getBeginningValue();
        assertThat(100.0, closeTo(balance, 0.0001));

        account.deposit(currentAge, 50.0);

        balance = balance + (balance * growthRate);
        Double endingBalance = balance + 50.0;
        assertThat(155.0, closeTo(endingBalance, 0.0001)); // 155.0 = 105.0 + 50.0
    }

    @Test
    public void checkWithdraw() throws Exception {
        Double  balance = 100.0;
        Double  growthRate = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;

        account = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        balance = account.getList().get(0).getBeginningValue();
        assertThat(100.0, closeTo(balance, 0.0001));

        account.withdraw(currentAge, 50.0);

        balance = balance + (balance * growthRate);
        Double endingBalance = balance - 50.0;
        assertThat(55.0, closeTo(endingBalance, 0.0001)); // 55.0 = 105.0 - 50.0
    }
}
