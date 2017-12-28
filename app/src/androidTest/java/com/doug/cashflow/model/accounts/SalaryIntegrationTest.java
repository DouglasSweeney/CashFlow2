package com.doug.cashflow.model.accounts;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.doug.cashflow.model.system.ValidateInputs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SalaryIntegrationTest {
	private static final double EPSILON = 1e-2;

	private Salary  salary = null;
	private Context context = InstrumentationRegistry.getTargetContext();
	private double        annualAmount = 0;
	private double        meritIncrease;
	private int           currentAge;
	private int           currentYear;
	private int           deathAge;
	private int           retirementAge;
	private UtilsIntegrationTest utils = new UtilsIntegrationTest();
    
	@Before
	public void Setup() {
	    salary = null;
	    
	    annualAmount = 100.0;
	    meritIncrease = 0.05;
	    currentAge = 57;
	    currentYear = 2016;
	    deathAge = 95;
	    retirementAge = 62;
	} 
	
	@Test
	public void testHappyCase() {
	    salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(annualAmount, salary.getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMinRetirementAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE + 1;
		retirementAge = validateInputs.MIN_AGE + 2;
		deathAge = validateInputs.MIN_AGE + 3;
		
		salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(annualAmount, salary.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxRetirementAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE + 1;
		retirementAge = validateInputs.MAX_AGE + 2 ;
		deathAge = validateInputs.MAX_AGE + 3;
		
	    salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(annualAmount, salary.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyMinMeritIncrease() {
		ValidateInputs validateInputs = new ValidateInputs(context);
	    meritIncrease  = validateInputs.MIN_GROWTH_RATE;
		
	    salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(annualAmount, salary.getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMaxMeritIncrease() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		meritIncrease = validateInputs.MAX_GROWTH_RATE;
		
	    salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(annualAmount, salary.getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMinCurrentAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		
		salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(annualAmount, salary.getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMaxCurrentAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE;
		retirementAge = validateInputs.MAX_AGE+1;
		deathAge = validateInputs.MAX_AGE+2;
		
	    salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = deathAge - currentAge - 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(annualAmount, salary.getEndingValue(currentYear), EPSILON);
	}
	@Test
	public void testVerifyCurrentYear() {
		currentYear = 3000;
		
	    salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(annualAmount, salary.getEndingValue(currentYear), EPSILON);
	}	
	
	@Test
	public void testVerifyMinDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		deathAge = validateInputs.MIN_AGE + 2;
		
		salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(annualAmount, salary.getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMaxDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE;
		deathAge = validateInputs.MAX_AGE+1;
		
	    salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    annualAmount = utils.computeEndingValue(annualAmount, meritIncrease, numberOfYears);
	    assertEquals(0.0, salary.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifySalaryIsZero() {
	    salary = new Salary(annualAmount, meritIncrease, currentAge, currentYear, deathAge, retirementAge); 
	    
	    assertEquals(0.0, salary.getEndingValue(currentYear+(retirementAge-currentAge)), EPSILON);
	}
}
