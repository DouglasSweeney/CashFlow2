package com.doug.cashflow.model.accounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class PensionUnitTest {
    private Account account;

    @Before
    public void setUp() throws Exception {

        account = null;
    }

    @After
    public void tearDown() throws Exception {
        account = null;
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkInflationAdjustedEqualsTrue() throws Exception {
        Double monthlyAmount = 50.0;
        Integer startAge = 59;
        boolean inflationAdjusted = true;
        Double  inflation = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Double  tempDouble;

        account = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge);

        tempDouble = account.getList().get(0).getEndingValue();
        assertThat(52.50*12, closeTo(tempDouble, 0.0001));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkInflationAdjustedEqualsFalse() throws Exception {
        Double monthlyAmount = 50.0;
        Integer startAge = 59;
        boolean inflationAdjusted = false;
        Double  inflation = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Double  tempDouble;

        account = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge);

        tempDouble = account.getList().get(0).getEndingValue();
        assertThat(50.0*12, closeTo(tempDouble, 0.0001));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void checkBeginningValueIsZero() throws Exception {
        Double monthlyAmount = 50.0;
        Integer startAge = 65;
        boolean inflationAdjusted = false;
        Double  inflation = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Double  tempDouble;

        account = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge);

        tempDouble = account.getList().get(0).getBeginningValue();
        assertThat(0.0, closeTo(tempDouble, 0.0001));
    }
}
