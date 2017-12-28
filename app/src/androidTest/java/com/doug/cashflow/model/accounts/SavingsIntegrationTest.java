package com.doug.cashflow.model.accounts;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.doug.cashflow.model.system.ValidateInputs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Doug on 5/31/2017.
 */

public class SavingsIntegrationTest {
    private static final double EPSILON = 1e-2;
    private Savings savings = null;
    private Context context = InstrumentationRegistry.getTargetContext();
    private double balance = 0;
    private double growthRate;
    private int    currentAge;
    private int    currentYear;
    private int    deathAge;
    private UtilsIntegrationTest utils = new UtilsIntegrationTest();

    @Before
    public void Setup() {
        savings = null;

        balance = 100.0;
        growthRate = 0.05;
        currentAge = 57;
        currentYear = 2016;
        deathAge = 95;
    }

    @Test
    public void testHappyCase_CurrentAge() {
        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = 1;
        assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), savings.getEndingValue(currentYear), EPSILON);
    }

    @Test
    public void testCurrentAgePlusOne() {
        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = 2;
        assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), savings.getEndingValue(currentYear+1), EPSILON);
    }

    @Test
    public void testDeathAge() {
        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = deathAge - currentAge - 1;
        assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), savings.getEndingValue(currentYear+numberOfYears-1), EPSILON);
    }

    @Test
    public void testDeathAgePlusOne() {
        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = deathAge - currentAge + 1;
        assertEquals(0.0, savings.getEndingValue(currentYear+numberOfYears), EPSILON);
    }

    @Test
    public void testWithdraw() {
        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        savings.withdraw(currentAge, 10.0);

        assertEquals(10.0, savings.getWithdrawals(currentYear), EPSILON);
    }

    @Test
    public void testWithdrawInvalidBalance() {
        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        // Withdraw the account balance instead
        savings.withdraw(currentAge, balance+10.0);

        assertEquals(balance, savings.getWithdrawals(currentYear), EPSILON);
    }

    @Test
    public void testDeposit() {
        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        savings.deposit(currentAge, 10.0);

        assertEquals(10.0, savings.getDeposits(currentYear), EPSILON);
    }

    @Test
    public void testIsTaxable() {
        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        assertEquals(false, savings.isTaxable());
    }

    @Test
    public void testVerifyMinAge() {
        ValidateInputs validateInputs = new ValidateInputs(context);
        currentAge = validateInputs.MIN_AGE;

        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = deathAge-currentAge - 1;
        assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), savings.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
    }

    @Test
    public void testVerifyMaxAge() {
        ValidateInputs validateInputs = new ValidateInputs(context);
        currentAge = validateInputs.MAX_AGE-2;
        deathAge = validateInputs.MAX_AGE;

        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = deathAge-currentAge - 1;
        assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), savings.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
    }

    @Test
    public void testVerifyMinGrowthRate() {
        ValidateInputs validateInputs = new ValidateInputs(context);
        growthRate = validateInputs.MIN_GROWTH_RATE;

        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = deathAge-currentAge - 1;
        assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), savings.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
    }

    @Test
    public void testVerifyMaxGrowthRate() {
        ValidateInputs validateInputs = new ValidateInputs(context);
        growthRate = validateInputs.MAX_GROWTH_RATE;

        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = deathAge-currentAge - 1;
        assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), savings.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
    }

    @Test
    public void testVerifyMinDeathAge() {
        ValidateInputs validateInputs = new ValidateInputs(context);
        currentAge = validateInputs.MIN_AGE;
        deathAge = validateInputs.MIN_AGE+2;

        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = deathAge-currentAge - 1;
        assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), savings.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
    }

    @Test
    public void testVerifyMaxDeathAge() {
        ValidateInputs validateInputs = new ValidateInputs(context);
        currentAge = validateInputs.MAX_AGE-2;
        deathAge = validateInputs.MAX_AGE;

        savings = new Savings(balance, growthRate, currentAge, currentYear, deathAge);

        int numberOfYears = deathAge-currentAge - 1;
        assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), savings.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
    }
}
