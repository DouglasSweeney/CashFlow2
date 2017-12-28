package com.doug.cashflow.model.accounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class SocialSecurityUnitTest {
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
        Double  monthlyAmount = 100.0;
        Integer startAge = 67;
        Double  inflation = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Integer retirementAge = 65;
        Double  tempDouble;
        Integer index;

        account = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);

        index = 0;
        while (currentAge < startAge) {
            index = index + 1;
            currentAge = currentAge + 1;
        }

        tempDouble = account.getList().get(index).getBeginningValue();
        assertThat(100.0*12, closeTo(tempDouble, 0.0001));

        tempDouble = account.getList().get(index+1).getBeginningValue();
        assertThat(105.0*12, closeTo(tempDouble, 0.0001));
    }

    @Test
    public void checkProperDataStructure() throws Exception {
        Double  monthlyAmount = 100.0;
        Integer startAge = 67;
        Double  inflation = 0.05;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Integer retirementAge = 65; // not used
        Double  tempDouble;
        Integer index;

        account = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);

        index = 0;
        while (currentAge < startAge) {
            index = index + 1;
            currentAge = currentAge + 1;
        }

        // Ensure that zeros are in data structure before starting to collect
        for (int i=0; i<index; i++) {
            tempDouble = account.getList().get(i).getEndingValue();
            assertThat(0.0, closeTo(tempDouble, 0.0001));
        }

        // Ensure starting to collect at startAge
        tempDouble = account.getList().get(index).getEndingValue();
        assertThat(monthlyAmount*12, closeTo(tempDouble, 0.0001));

        // Ensure inflation properly calculated from startAge+1 to deathAge
        Double value = monthlyAmount*12+(monthlyAmount*12*inflation);
        for (int i=index+1; i<account.getSize(); i++) {
            tempDouble = account.getList().get(i).getEndingValue();
            assertThat(value, closeTo(tempDouble, 0.0001));
            value = value + (value * inflation);
        }
    }
}
