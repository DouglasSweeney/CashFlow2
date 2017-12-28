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

public class AccountIraIntegrationTest {
	private static final double EPSILON = 1e-2;
	private AccountIra accountIra = null;
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
	    accountIra = null;
	    
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
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear), EPSILON);
	}

	@Test
	public void testNumberOfYearsEqualsTwo() {
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = 2;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear+1), EPSILON);
	}
	
	@Test
	public void testDeathAge() {
		int numberOfWithdrawals = 0;
		double annualContribution = 0.0;
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = deathAge - currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear+numberOfYears-1), EPSILON);
	}

	@Test
	public void testDeathAgePlusOne() {
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = deathAge - currentAge + 1;
	    assertEquals(0.0, accountIra.getEndingValue(currentYear+numberOfYears), EPSILON);
	}

	@Test
	public void testNumberOfWithdrawals() {
		double annualContribution = 0.0;
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = startWithdrawalsAge-currentAge-1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear+numberOfYears-1), EPSILON);
	}

	@Test
	public void testWithdraw() {
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    accountIra.withdraw(currentAge, 10.0);
	    
	    assertEquals(10.0, accountIra.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void withdrawInvalidBalance() {
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    // Withdraw the account balance instead
	    accountIra.withdraw(currentAge, balance+40.0);
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testDeposit() {
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    accountIra.deposit(currentAge, 10.0);
	    
	    assertEquals(10.0 + annualContribution, accountIra.getDeposits(currentYear), EPSILON);
	}
		
	@Test
	public void testIsTaxable() {
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    assertEquals(true, accountIra.isTaxable());
	}
	
	@Test
	public void testVerifyMinAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyMinGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MIN_GROWTH_RATE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MAX_GROWTH_RATE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMinAnnualContribution() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		annualContribution = validateInputs.MIN_ANNUAL_CONTRIBUTION;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAnnualContribution() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		annualContribution = validateInputs.MAX_ANNUAL_CONTRIBUTION;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountIra.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMinWithdrawalAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		startWithdrawalsAge = validateInputs.MIN_AGE+1;
		
		numberOfWithdrawals = 1;
		
    	accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = 1;	    
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), 
	    			 accountIra.getWithdrawals(currentYear + numberOfWithdrawals), EPSILON);
	}
	
	@Test
	public void testVerifyMaxWithdrawalAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
	    startWithdrawalsAge = validateInputs.MAX_AGE-1;
	    deathAge = validateInputs.MAX_AGE;		
		numberOfWithdrawals = 1;
		annualContribution = 0.0;

	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), 
	    		     accountIra.getWithdrawals(currentYear + numberOfYears), EPSILON);
	}
	
	@Test
	public void testVerifyMinNumberOfWithdrawal() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		numberOfWithdrawals = validateInputs.MIN_NUMBER_OF_WITHDRAWALS;
		
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    assertEquals(0.0,  accountIra.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMaxNumberOfWithdrawal() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		numberOfWithdrawals = validateInputs.MAX_NUMBER_OF_WITHDRAWALS;
		currentAge = 18;
		deathAge = validateInputs.MAX_AGE;
		
	    accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    	    
	    assertEquals(357.51, accountIra.getWithdrawals(currentYear + numberOfWithdrawals - 1), EPSILON);
	}

	@Test
	public void testAnnualContributions() {
		currentAge = 55;
		retirementAge = 56;

		// 1 year of contributions
		annualContribution = 1000.0;

		accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(annualContribution, is(closeTo(accountIra.getDeposits(currentYear), EPSILON)));
	}

	@Test
	public void testGetBalance() {
		accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(balance, is(closeTo(accountIra.getBalance(), EPSILON)));
	}

	@Test
	public void testGetGrowthRate() {
		accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(growthRate, is(closeTo(accountIra.getGrowthRate(), EPSILON)));
	}

	@Test
	public void testGetCurrentAge() {
		accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(currentAge, is(accountIra.getCurrentAge()));
	}

	@Test
	public void testGetCurrentYear() {
		accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(currentYear, is(accountIra.getCurrentYear()));
	}

	@Test
	public void testGetDeathAge() {
		accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(deathAge, is(accountIra.getDeathAge()));
	}

	@Test
	public void testGetStartWithdrawalsAge() {
		accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(startWithdrawalsAge, is(accountIra.getStartWithdrawalsAge()));
	}

	@Test
	public void testGetNumberOfWithdrawals() {
		accountIra = new AccountIra(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(numberOfWithdrawals, is(accountIra.getNumberOfWithdrawals()));
	}
}
