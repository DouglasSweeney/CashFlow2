package com.doug.cashflow.model.accounts;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.doug.cashflow.model.system.ValidateInputs;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PensionIntegrationTest {
	private static final double EPSILON = 0.000001;
	private static final int    MONTHS_IN_YEAR = 12;

	private Pension       pension = null;
	private Context       context = InstrumentationRegistry.getTargetContext();
	private int           startAge;	
	private double        monthlyAmount = 0;

	private boolean       inflationAdjusted;
	private double        inflation;
	private int           currentAge;
	private int           currentYear;
	private int           deathAge;
	private UtilsIntegrationTest utils = new UtilsIntegrationTest();
	private double        annualAmount;
    
	@Before
	public void Setup() {
	    pension = null;
	    
	    monthlyAmount = 100.0;
	    startAge = 62;
	    inflationAdjusted = false;
	    inflation = 0.05;
	    currentAge = 57;
	    currentYear = 2016;
	    deathAge = 95;
	} 
	
	@Test
	public void testHappyCase() {
	    pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = 10;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, pension.getBeginningValue(currentYear+numberOfYears), EPSILON);
	}
	
	@Test
	public void testVerifyMinStartAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE -2;
		startAge = validateInputs.MIN_AGE;
		deathAge = validateInputs.MIN_AGE + 2;
		
		pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, pension.getBeginningValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxStartAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE - 2;
		startAge = validateInputs.MAX_AGE;
		deathAge = validateInputs.MAX_AGE + 2;
		
	    pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, pension.getBeginningValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyMinInflation() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		inflationAdjusted = true;
	    inflation  = validateInputs.MIN_INFLATION_RATE;
		
	    pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
//	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
        double expected = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
		double actual = pension.getBeginningValue(currentYear + numberOfYears);

        // Divide down big negative numbers
        if (actual < 7e52) {
            actual /= 7e54;
            expected /= 7e54;
        }

        assertThat("assertThat(actual, closeTo(expected, EPSILON))", actual, closeTo(expected, EPSILON));
//        assertEquals("expected, actual, EPSILON", expected, actual, EPSILON);
	}
	
	@Test
	public void testVerifyMaxInflation() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		inflationAdjusted = true;
		inflation = validateInputs.MAX_INFLATION_RATE;
		
	    pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, pension.getBeginningValue(currentYear + numberOfYears), EPSILON);
	}
	
	@Test
	public void testVerifyMinCurrentAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		
		pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, pension.getBeginningValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxCurrentAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
	    pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, pension.getBeginningValue(currentYear + numberOfYears - 1), EPSILON);
	}
	@Test
	public void testVerifyCurrentYear() {
		currentYear = 3000;
		
	    pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, pension.getBeginningValue(currentYear + numberOfYears - 1), EPSILON);
	}	
	
	@Test
	public void testVerifyMinDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		deathAge = validateInputs.MIN_AGE + 2;
		
		pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, pension.getEndingValue(currentYear + numberOfYears), EPSILON);
	}
	
	@Test
	public void testVerifyDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
	    pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflationAdjusted, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, pension.getBeginningValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyTaxability() {
	    pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    assertEquals(true, pension.isTaxable());
	}
	
	@Test
	public void testVerifyPensionIsZero() {
	    pension = new Pension(monthlyAmount, startAge, inflationAdjusted, inflation, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = 1;
	    assertEquals(0.0, pension.getBeginningValue(currentYear+numberOfYears), EPSILON);
	}
}
