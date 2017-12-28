package com.doug.cashflow.model.accounts;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.doug.cashflow.model.system.ValidateInputs;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AccountRothIntegrationTest extends Account {
	private static final double EPSILON = 1e-2;
	private AccountRoth accountRoth = null;
	private Context context = InstrumentationRegistry.getTargetContext();
	private double balance = 0;
	private double growthRate;
	private double annualContribution;
	private int    currentAge;
	private int    currentYear;
	private int    retirementAge;
	private int    deathAge;
	private int    startWithdrawalsAge;
	private int    numberOfWithdrawals;
    
	@Before
	public void Setup() {
	    accountRoth = null;
	    
	    balance = 100.0;
	    growthRate = 0.05;
	    annualContribution = 10.0;
	    currentAge = 57;
	    currentYear = 2016;
	    retirementAge = 62;
	    deathAge = 95;
	    startWithdrawalsAge = 62;
	    numberOfWithdrawals = 10;
	 } 
	
	private double computeEndingValue(double balance, double growthRate, int numberOfYears,
			                          double annualContribution) {
		double value = balance;
		
		for (int i=0; i<numberOfYears; i++) {
			value += annualContribution;
			value += value * growthRate;
		}
		
		return value;
	}
	
	@Test
	public void testHappyCase() {
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear), EPSILON);
	}

	@Test
	public void testNumberOfYearsEqualsTwo() {
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = 2;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear+1), EPSILON);
	}
	
	@Test
	public void testDeathAge() {
		int numberOfWithdrawals = 0;
		double annualContribution = 0.0;
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = deathAge - currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear+numberOfYears-1), EPSILON);
	}

	@Test
	public void testDeathAgePlusOne() {
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = deathAge - currentAge + 1;
	    assertEquals(0.0, accountRoth.getEndingValue(currentYear+numberOfYears), EPSILON);
	}

	@Test
	public void testNumberOfWithdrawals() {
		double annualContribution = 0.0;
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = startWithdrawalsAge-currentAge-1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear+numberOfYears-1), EPSILON);
	}

	@Test
	public void testWithdraw() {
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    accountRoth.withdraw(currentAge, 10.0);
	    
	    assertEquals(10.0, accountRoth.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void withdrawInvalidBalance() {
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    // Withdraw the account balance instead
	    accountRoth.withdraw(currentAge, balance+40.0);
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testDeposit() {
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    accountRoth.deposit(currentAge, 10.0);
	    
	    assertEquals(10.0 + annualContribution, accountRoth.getDeposits(currentYear), EPSILON);
	}
		
	@Test
	public void testIsTaxable() {
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    assertEquals(false, accountRoth.isTaxable());
	}
	
	@Test
	public void testVerifyMinAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyMinGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MIN_GROWTH_RATE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MAX_GROWTH_RATE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMinAnnualContribution() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		annualContribution = validateInputs.MIN_ANNUAL_CONTRIBUTION;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAnnualContribution() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		annualContribution = validateInputs.MAX_ANNUAL_CONTRIBUTION;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountRoth.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMinWithdrawalAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		startWithdrawalsAge = validateInputs.MIN_AGE+1;
		
		numberOfWithdrawals = 1;
		
    	accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = 1;	    
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), 
	    			 accountRoth.getWithdrawals(currentYear + numberOfWithdrawals), EPSILON);
	}
	
	@Test
	public void testVerifyMaxWithdrawalAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
	    startWithdrawalsAge = validateInputs.MAX_AGE-1;
	    deathAge = validateInputs.MAX_AGE;		
		numberOfWithdrawals = 1;
		annualContribution = 0.0;

	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), 
	    		     accountRoth.getWithdrawals(currentYear + numberOfYears), EPSILON);
	}
	
	@Test
	public void testVerifyMinNumberOfWithdrawal() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		numberOfWithdrawals = validateInputs.MIN_NUMBER_OF_WITHDRAWALS;
		
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    assertEquals(0.0,  accountRoth.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMaxNumberOfWithdrawal() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		numberOfWithdrawals = validateInputs.MAX_NUMBER_OF_WITHDRAWALS;
		currentAge = 18;
		deathAge = validateInputs.MAX_AGE;
		
	    accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    	    
	    assertEquals(357.51, accountRoth.getWithdrawals(currentYear + numberOfWithdrawals - 1), EPSILON);
	}

	@Test
	public void testAnnualContributions() {
		currentAge = 55;
		retirementAge = 56;

		// 1 year of contributions
		annualContribution = 1000.0;

		accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(annualContribution, is(closeTo(accountRoth.getDeposits(currentYear), EPSILON)));
	}

	@Test
	public void testGetBalance() {
		accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(balance, is(closeTo(accountRoth.getBalance(), EPSILON)));
	}

	@Test
	public void testGetGrowthRate() {
		accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(growthRate, is(closeTo(accountRoth.getGrowthRate(), EPSILON)));
	}

	@Test
	public void testGetCurrentAge() {
		accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(currentAge, is(accountRoth.getCurrentAge()));
	}

	@Test
	public void testGetCurrentYear() {
		accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(currentYear, is(accountRoth.getCurrentYear()));
	}

	@Test
	public void testGetDeathAge() {
		accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(deathAge, is(accountRoth.getDeathAge()));
	}

	@Test
	public void testGetStartWithdrawalsAge() {
		accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(startWithdrawalsAge, is(accountRoth.getStartWithdrawalsAge()));
	}

	@Test
	public void testGetNumberOfWithdrawals() {
		accountRoth = new AccountRoth(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(numberOfWithdrawals, is(accountRoth.getNumberOfWithdrawals()));
	}
}