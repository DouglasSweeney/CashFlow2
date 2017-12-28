package com.doug.cashflow.model.accounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class AccountRothUnitTest {
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
    public void checkAnnualContributions() throws Exception {
        Double  balance = 100.0;
        Double  growthRate = 0.0;
        Double  annualContributions = 10.0;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer retirementAge = 68;
        Integer deathAge = 95;
        Integer startWithdrawalsAge = 70;
        Integer numberOfWithdrawals = 10;

        account = new AccountRoth(balance, growthRate, annualContributions, currentAge, currentYear,
                retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        // growth rate of 0%
        balance = 110.0;
        for (int i=0; i<retirementAge-currentAge; i++, balance+=annualContributions)
            assertThat(balance, is(account.getListOfValues().get(i).getEndingValue()));
    }

    @Test
    public void checkBalancesOverEntireList() throws Exception {
        Double  balance = 100.0;
        Double  growthRate = 0.05;
        Double  annualContributions = 0.0;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer retirementAge = 68;
        Integer deathAge = 95;
        Integer startWithdrawalsAge = 0;
        Integer numberOfWithdrawals = 0;

        account = new AccountRoth(balance, growthRate, annualContributions, currentAge, currentYear,
                retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        // growth rate of 5% & no contributions
        for (int i=0; i<deathAge-currentAge; i++) {
            assertThat(balance, is(account.getListOfValues().get(i).getBeginningValue()));

            balance = balance + (balance*growthRate);
            assertThat(balance, is(account.getListOfValues().get(i).getEndingValue()));
        }
    }

    @Test
    public void checkWithdrawalsStartWithdrawalsAgeGreaterThanCurrentAge() throws Exception {
        Double  balance = 100.0;
        Double  growthRate = 0.00;
        Double  annualContributions = 0.0;
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer retirementAge = 65;
        Integer deathAge = 95;
        Integer startWithdrawalsAge = 65;
        Integer numberOfWithdrawals = 10;
        Integer indexOffset;

        account = new AccountRoth(balance, growthRate, annualContributions, currentAge, currentYear,
                retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        // Get index up to the retirementAge
        indexOffset = 0;
        if (currentAge < startWithdrawalsAge) {
            while (currentAge < startWithdrawalsAge) {
                indexOffset = indexOffset + 1;
                currentAge++;
            }
        }
        for (int i=0; i<numberOfWithdrawals; i++) {
            assertThat(account.getListOfValues().get(indexOffset+i).getWithdrawal(), is(greaterThan(0.0)));
        }
        assertThat(account.getListOfValues().get(indexOffset+numberOfWithdrawals).getWithdrawal(), is(0.0));
    }

    @Test
    public void checkWithdrawalsStartWithdrawalsAgeLessThanCurrentAge() throws Exception {
        Double  balance = 100.0;
        Double  growthRate = 0.00;
        Double  annualContributions = 0.0;
        Integer currentAge = 65;
        Integer currentYear = 2017;
        Integer retirementAge = 65;
        Integer deathAge = 95;
        Integer startWithdrawalsAge = 62;
        Integer numberOfWithdrawals = 5;
        Integer indexOffset;

        account = new AccountRoth(balance, growthRate, annualContributions, currentAge, currentYear,
                retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        // Get index up to the retirementAge
        indexOffset = 0;
        if (currentAge > startWithdrawalsAge) {
            while (currentAge > startWithdrawalsAge) {
                indexOffset = indexOffset + 1;
                currentAge--;
            }
        }
/*
        System.out.println("indexOffset: " + indexOffset);
        Integer age = currentAge;
        Integer year = currentYear;
        for (int i=0; i<numberOfWithdrawals; i++, age++, year++) {
            System.out.print("Year: " + year + " ");
            System.out.print("Age: " + age + " ");
            System.out.print("Begin: " + account.getList().get(i).getBeginningValue() + " ");
            System.out.print("Withdrawals: " + account.getList().get(i).getWithdrawal() + " ");
            System.out.println("Ending: " + account.getList().get(i).getEndingValue() + " ");
        }
*/

        for (int i=0; i<numberOfWithdrawals-indexOffset; i++) {
            assertThat(account.getListOfValues().get(i).getWithdrawal(), is(greaterThan(0.0)));
        }
        assertThat(account.getListOfValues().get(numberOfWithdrawals).getWithdrawal(), closeTo(0.0, 0.0001));
    }

    @Test
    public void checkWithdrawalsStartWithdrawalsAgeEqualsCurrentAge() throws Exception {
        Double  balance = 100.0;
        Double  growthRate = 0.00;
        Double  annualContributions = 0.0;
        Integer currentAge = 63;
        Integer currentYear = 2017;
        Integer retirementAge = 63;
        Integer deathAge = 95;
        Integer startWithdrawalsAge = 63;
        Integer numberOfWithdrawals = 10;

        account = new AccountRoth(balance, growthRate, annualContributions, currentAge, currentYear,
                retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        for (int i=0; i<numberOfWithdrawals; i++) {
            assertThat(account.getListOfValues().get(i).getWithdrawal(), is(greaterThan(0.0)));
        }
        assertThat(account.getListOfValues().get(numberOfWithdrawals).getWithdrawal(), closeTo(0.0, 0.0001));
    }
}
