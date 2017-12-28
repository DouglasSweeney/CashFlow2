package com.doug.cashflow.model.accounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class AccountUnitTest {
    private Account account;

    @Before
    public void setUp() throws Exception {

        account = new Account();
    }

    @After
    public void tearDown() throws Exception {
        account = null;
    }

    @Test
    public void checkValidVerifyGetBalance() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        assertThat(100.0, is(account.getBalance()));
    }

    @Test
    public void checkValidVerifyGetGrowthRate() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        assertThat(0.05, is(account.getGrowthRate()));
    }

    @Test
    public void checkValidVerifyGetCurrentAge() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        assertThat(90, is(account.getCurrentAge()));
    }

    @Test
    public void checkValidVerifyGetCurrentYear() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        assertThat(2017, is(account.getCurrentYear()));
    }

    @Test
    public void checkValidVerifyGetLifeExpectancyAge() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        assertThat(95, is(account.getDeathAge()));
    }

    @Test
    public void checkValidVerifyListAge() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        Integer startAge = 90;
        for (int i=0; i<account.getSize(); i++, startAge++)
            assertThat(startAge, is(account.getListOfValues().get(i).getAge()));
    }

    @Test
    public void checkValidVerifyListYear() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        Integer startYear = 2017;
        for (int i=0; i<account.getSize(); i++, startYear++)
            assertThat(startYear, is(account.getListOfValues().get(i).getYear()));
    }

    @Test
    public void checkValidVerifyListStartingBalance() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        Double balance = 100.0;
        Double growthRate = 0.05;
        for (int i=0; i<account.getSize(); i++, balance = balance + (balance * growthRate))
            assertThat(balance, closeTo(account.getListOfValues().get(i).getBeginningValue(), 0.0001));
    }

    @Test
    public void checkValidVerifyListEndingBalance() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        Double growthRate = 0.05;
        Double balance = 100.0 + (100.0 * growthRate);
        for (int i=0; i<account.getSize(); i++, balance = balance + (balance * growthRate))
            assertThat(balance, closeTo(account.getListOfValues().get(i).getEndingValue(), 0.0001));
    }

    @Test
    public void checkValidVerifyGetSmallestYear() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        assertThat(2017, is(account.getListOfValues().get(0).getYear()));
    }

    @Test
    public void checkValidVerifyGetLargestYear() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);

        assertThat(2021, is(account.getListOfValues().get(account.getSize()-1).getYear()));
    }

    @Test
    public void checkConvertFirstAgeToYear() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);
        Integer age = account.convertAgeToYear(90);
        assertThat(2017, is(age));
    }

    @Test
    public void checkConvertLastAgeToYear() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);
        Integer year = account.convertAgeToYear(94);
        assertThat(2021, is(year));
    }

    @Test
    public void checkConvertFirstYearToAge() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);
        Integer age = account.convertYearToAge(2017);
        assertThat(90, is(age));
    }

    @Test
    public void checkConvertLastYearToAge() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);
        Integer age = account.convertYearToAge(2021);
        assertThat(94, is(age));
    }

    @Test
    public void checkGetDeposits() throws Exception {
        account.initialize(100.00, 0.05, 90, 2017, 95);
        account.deposit(90, 50.0);
        Double deposits = account.getDeposits(2017);
        assertThat(50.0, closeTo(deposits, 0.0001));
    }

    @Test
    public void checkRecomputeGrowthWithDepositFirstItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.recomputeGrowthWithDeposit(90, 50.0);
        Double endingBalance = account.getEndingValue(2017);

        assertThat(150.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkRecomputeGrowthWithDepositLastItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.recomputeGrowthWithDeposit(94, 50.0);
        Double endingBalance = account.getEndingValue(2021);

        assertThat(150.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkRecomputeGrowthWithWithdrawalFirstItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.recomputeGrowthWithWithdrawal(90, 50.0);
        Double endingBalance = account.getWithdrawals(2017);

        assertThat(50.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkRecomputeGrowthWithWithdrawalLastItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.recomputeGrowthWithWithdrawal(94, 50.0);
        Double endingBalance = account.getEndingValue(2021);

        assertThat(50.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkZeroBeginningValueFirstItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.zeroBeginningValues(91);

        Double endingBalance = account.getBeginningValue(2017);
        assertThat(100.0, closeTo(endingBalance, 0.0001));

        endingBalance = account.getBeginningValue(2018);
        assertThat(0.0, closeTo(endingBalance, 0.0001));

        endingBalance = account.getBeginningValue(2021);
        assertThat(0.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkZeroBeginningValueLastItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.zeroBeginningValues(91);

        Double endingBalance = account.getBeginningValue(2017);
        assertThat(100.0, closeTo(endingBalance, 0.0001));

        endingBalance = account.getBeginningValue(2018);
        assertThat(0.0, closeTo(endingBalance, 0.0001));

        endingBalance = account.getBeginningValue(2021);
        assertThat(0.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkZeroEndingValueFirstItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.zeroEndingValues(91);

        Double endingBalance = account.getEndingValue(2017);
        assertThat(100.0, closeTo(endingBalance, 0.0001));

        endingBalance = account.getEndingValue(2018);
        assertThat(0.0, closeTo(endingBalance, 0.0001));

        endingBalance = account.getEndingValue(2021);
        assertThat(0.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkZeroEndingValueLastItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.zeroEndingValues(91);

        Double endingBalance = account.getEndingValue(2017);
        assertThat(100.0, closeTo(endingBalance, 0.0001));

        endingBalance = account.getEndingValue(2018);
        assertThat(0.0, closeTo(endingBalance, 0.0001));

        endingBalance = account.getEndingValue(2021);
        assertThat(0.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkWithdrawFirstItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.withdraw(90, 50.0);
        Double endingBalance = account.getWithdrawals(2017);

        assertThat(50.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkWithdrawLastItemInList() throws Exception {
        account.initialize(100.00, 0.0, 90, 2017, 95);
        account.withdraw(94, 50.0);
        Double endingBalance = account.getWithdrawals(2021);

        assertThat(50.0, closeTo(endingBalance, 0.0001));
    }

    @Test
    public void checkAddInContributions() throws Exception {
        account.initialize(100.00, 0.00, 90, 2017, 95);
        account.addInAnnualContributions(90, 91, 10.0);
        Double endingValue = account.getEndingValue(2017);
        assertThat(110.0, closeTo(endingValue, 0.0001));
    }


    @Test
    public void checkComputePeriodicWithdrawals_CurrentAgeEqualsStartWithdrawalsAge() throws Exception {
//        Integer age;
//        Integer year;

        account.initialize(100.00, 0.00, 90, 2017, 95);
        account.computePeriodicWithdrawals(90, 90, 5);

/*
Integer currentAge = 90;
age = currentAge;
year = 2017;
Integer deathAge = 95;
for (int i=0; i<deathAge-currentAge; i++, age++, year++) {
System.out.println("Index: " + i + " " + "Age: " + age + " " + "Year: " + year + " Withdrawal: " +
account.getList().get(i).getWithdrawal());
}
*/

        Double withdrawal = account.getWithdrawals(2017);
        assertThat(20.0, closeTo(withdrawal, 0.0001));
    }

    @Test
    public void checkPeriodicWithdrawals_CurrentAgeLessThanStartWithdrawalsAge() throws Exception {
        account.initialize(100.00, 0.00, 90, 2017, 95);
        account.computePeriodicWithdrawals(90, 91, 4);

        //Integer currentAge = 90;
        //Integer age = currentAge;
        //Integer year = 2017;
        //Integer deathAge = 95;
        //for (int i=0; i<deathAge-currentAge; i++, age++, year++) {
        //System.out.println("Index: " + i + " " + "Age: " + age + " " + "Year: " + year + " Withdrawal: " +
        //account.getList().get(i).getWithdrawal());
        //}

        Double endingBalance = account.getWithdrawals(2017);
        assertThat(0.0, closeTo(endingBalance, 0.0001));

        Double endingValue = account.getEndingValue(2017);
        assertThat(100.0, closeTo(endingValue, 0.0001));

        Double withdrawal = account.getWithdrawals(2018);
        assertThat(100.0/4, closeTo(withdrawal, 0.0001));
    }

    @Test
    // Started withdrawals 2 years ago
    public void checkPeriodicWithdrawals_CurrentAgeGreaterThanStartWithdrawalsAge() throws Exception {
        Double balance = 60.0;
        Double growthRate = 0.00;
        Integer currentAge = 90;
        Integer deathAge = 95;
        Integer numberOfWithdrawals = 5;

        account.initialize(balance, growthRate, currentAge, 2017, deathAge);
        account.computePeriodicWithdrawals(currentAge, 88, numberOfWithdrawals);

//        Integer age = currentAge;
//        Integer year = 2017;
//        for (int i=0; i<deathAge-currentAge; i++, age++, year++) {
//            System.out.print("Index: " + i + " " + "Age: " + age + " " + "Year: " + year + " ");
//            System.out.print("Begin: " + account.getList().get(i).getBeginningValue() + " ");
//            System.out.print("Withdrawal: " + account.getList().get(i).getWithdrawal() + " ");
//            System.out.println("Ending: " + account.getList().get(i).getEndingValue() + " ");
//        }

        Double beginningValue = account.getBeginningValue(2017);
        assertThat(60.0, closeTo(beginningValue, 0.0001));

        // Each withdrawal is $20.00 @ growth rate of 0%
        Double withdrawal = account.getWithdrawals(2017);
        assertThat(60.0/(numberOfWithdrawals-2), closeTo(withdrawal, 0.0001));

        withdrawal = account.getWithdrawals(2018);
        assertThat(40.0/(numberOfWithdrawals-3), closeTo(withdrawal, 0.0001));

        withdrawal = account.getWithdrawals(2019);
        assertThat(20.0/(numberOfWithdrawals-4), closeTo(withdrawal, 0.0001));
    }
}
