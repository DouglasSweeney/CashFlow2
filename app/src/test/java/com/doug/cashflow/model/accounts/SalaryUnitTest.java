package com.doug.cashflow.model.accounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class SalaryUnitTest {
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
        Double  salary = 100.0;
        Double  meritIncrease = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Integer retirementAge = 65;
        Double  tempDouble;

        account = new Salary(salary, meritIncrease, currentAge, currentYear, deathAge, retirementAge);

        tempDouble = account.getList().get(0).getBeginningValue();
        assertThat(100.0, closeTo(tempDouble, 0.0001));

        tempDouble = account.getList().get(0).getEndingValue();
        assertThat(105.0, closeTo(tempDouble, 0.0001));
    }

    @Test
    public void ensureNoSalaryAfterRetirement() throws Exception {
        Double  salary = 100.0;
        Double  meritIncrease = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Integer retirementAge = 65;
        Double  tempDouble;
        Integer indexOffset;

        account = new Salary(salary, meritIncrease, currentAge, currentYear, deathAge, retirementAge);

        indexOffset = 0;
        while (currentAge < retirementAge) {
            indexOffset = indexOffset + 1;
            currentAge = currentAge + 1;
        }

        // Assumes a salary
        tempDouble = account.getList().get(indexOffset).getBeginningValue();
        assertThat(0.0, greaterThanOrEqualTo(tempDouble));

        tempDouble = account.getList().get(indexOffset + 1).getBeginningValue();
        assertThat(0.0, closeTo(tempDouble, 0.0001));

        tempDouble = account.getList().get(indexOffset + 1).getEndingValue();
        assertThat(0.0, closeTo(tempDouble, 0.0001));
    }
}
