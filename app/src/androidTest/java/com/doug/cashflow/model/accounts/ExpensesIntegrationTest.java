package com.doug.cashflow.model.accounts;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.doug.cashflow.model.system.ValidateInputs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpensesIntegrationTest {
	private static final double EPSILON = 1e-2;
	private Expenses expenses = null;
	private Context context = InstrumentationRegistry.getTargetContext();
	private double expense = 0;
	private double growthRate;
	private int    currentAge;
	private int    currentYear;
	private int    deathAge;
	private UtilsIntegrationTest utils = new UtilsIntegrationTest();
    
	@Before
	public void Setup() {
	    expenses = null;
	    
	    expense = 100.0;
	    growthRate = 0.05;
	    currentAge = 57;
	    currentYear = 2016;
	    deathAge = 95;
	} 
	
	@Test
	public void testHappyCase() {
	    expenses = new Expenses(expense, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = 1;
	    assertEquals(utils.computeEndingValue(expense, growthRate, numberOfYears), expenses.getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMinAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		
		expenses = new Expenses(expense, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(expense, growthRate, numberOfYears), expenses.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
	    expenses = new Expenses(expense, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(expense, growthRate, numberOfYears), expenses.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyMinGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MIN_GROWTH_RATE;
		
	    expenses = new Expenses(expense, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(expense, growthRate, numberOfYears), expenses.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MAX_GROWTH_RATE;
		
	    expenses = new Expenses(expense, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(expense, growthRate, numberOfYears), expenses.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyCurrentYear() {
		currentYear = 3000;
		
	    expenses = new Expenses(expense, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(expense, growthRate, numberOfYears), expenses.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}	
	@Test
	public void testVerifyMinDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		currentAge = validateInputs.MIN_AGE+2;
		
		expenses = new Expenses(expense, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(utils.computeEndingValue(expense, growthRate, numberOfYears), expenses.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;

		expenses = new Expenses(expense, growthRate, currentAge, currentYear, deathAge);

		int numberOfYears = deathAge-currentAge - 1;
		assertEquals(utils.computeEndingValue(expense, growthRate, numberOfYears), expenses.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
}
