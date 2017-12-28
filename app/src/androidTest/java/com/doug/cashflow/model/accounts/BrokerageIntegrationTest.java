package com.doug.cashflow.model.accounts;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.doug.cashflow.model.system.ValidateInputs;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BrokerageIntegrationTest {
	private static final double EPSILON = 1e-2;
	private Brokerage brokerage = null;
	private Context context = InstrumentationRegistry.getTargetContext();
	private double balance = 0;
	private double growthRate;
	private int    currentAge;
	private int    currentYear;
	private int    deathAge;
	private UtilsIntegrationTest utils = new UtilsIntegrationTest();
    
	@Before
	public void Setup() {
	    brokerage = null;
	    
	    balance = 100.0;
	    growthRate = 0.05;
	    currentAge = 57;
	    currentYear = 2016;
	    deathAge = 95;
	} 

	@Test
	public void testHappyCase_CurrentAge() {
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = 1;
	    Assert.assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), brokerage.getEndingValue(currentYear), EPSILON);
	}

	@Test
	public void testCurrentAgePlusOne() {
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 

	    int numberOfYears = 2;
	    Assert.assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), brokerage.getEndingValue(currentYear+1), EPSILON);
	}
	
	@Test
	public void testDeathAge() {
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 

	    int numberOfYears = deathAge - currentAge - 1;
	    Assert.assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), brokerage.getEndingValue(currentYear+numberOfYears-1), EPSILON);
	}

	@Test
	public void testDeathAgePlusOne() {
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 

	    int numberOfYears = deathAge - currentAge + 1;
	    assertEquals(0.0, brokerage.getEndingValue(currentYear+numberOfYears), EPSILON);
	}

	@Test
	public void testWithdraw() {
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge);
	    
	    brokerage.withdraw(currentAge, 10.0);
	    
	    assertEquals(10.0, brokerage.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testWithdrawInvalidBalance() {
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    // Withdraw the account balance instead
	    brokerage.withdraw(currentAge, balance+10.0);
	    
	    assertEquals(balance, brokerage.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testDeposit() {
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    brokerage.deposit(currentAge, 10.0);
	    
	    assertEquals(10.0, brokerage.getDeposits(currentYear), EPSILON);
	}
		
	@Test
	public void testIsTaxable() {
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    assertEquals(false, brokerage.isTaxable());
	}
	
	@Test
	public void testVerifyMinAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		
		brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    Assert.assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), brokerage.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    Assert.assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), brokerage.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyMinGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MIN_GROWTH_RATE;
		
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    Assert.assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), brokerage.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MAX_GROWTH_RATE;
		
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    Assert.assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), brokerage.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}	
	
	@Test
	public void testVerifyMinDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		deathAge = validateInputs.MIN_AGE+2;
		
		brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    Assert.assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), brokerage.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
	    brokerage = new Brokerage(balance, growthRate, currentAge, currentYear, deathAge); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    Assert.assertEquals(utils.computeEndingValue(balance, growthRate, numberOfYears), brokerage.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
}
