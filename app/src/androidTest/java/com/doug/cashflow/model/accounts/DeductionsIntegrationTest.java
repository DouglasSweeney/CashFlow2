package com.doug.cashflow.model.accounts;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.doug.cashflow.model.system.ValidateInputs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeductionsIntegrationTest {
	private static final double EPSILON = 1e-2;
	private Deductions deductions = null;
	private Context context = InstrumentationRegistry.getTargetContext();
	private double deduction = 0;
	private double growthRate;
	private int    currentAge;
	private int    currentYear;
	private int    deathAge;
	private UtilsIntegrationTest utils = new UtilsIntegrationTest();
    
	@Before
	public void Setup() {
	    deductions = null;
	    
	    deduction = 100.0;
	    growthRate = 0.05;
	    currentAge = 57;
	    currentYear = 2016;
	    deathAge = 95;
	} 
	
	@Test
	public void testHappyCase() {
	    deductions = new Deductions(deduction, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = 1;
	    assertEquals(utils.computeEndingValue(deduction, growthRate, numberOfYears), deductions.getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMinAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		
		deductions = new Deductions(deduction, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(deduction, growthRate, numberOfYears), deductions.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
	    deductions = new Deductions(deduction, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(deduction, growthRate, numberOfYears), deductions.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyMinGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MIN_GROWTH_RATE;
		
	    deductions = new Deductions(deduction, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(deduction, growthRate, numberOfYears), deductions.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MAX_GROWTH_RATE;
		
	    deductions = new Deductions(deduction, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(deduction, growthRate, numberOfYears), deductions.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyCurrentYear() {
		currentYear = 3000;
		
	    deductions = new Deductions(deduction, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(deduction, growthRate, numberOfYears), deductions.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}	
	@Test
	public void testVerifyMinDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		currentAge = validateInputs.MIN_AGE+2;
		
		deductions = new Deductions(deduction, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(deduction, growthRate, numberOfYears), deductions.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
	    deductions = new Deductions(deduction, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(deduction, growthRate, numberOfYears), deductions.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
}
