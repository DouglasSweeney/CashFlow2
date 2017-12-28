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

//import main.ValidateInputs;

public class AccountCashBalanceIntegrationTest {
	private static final double EPSILON = 1e-2;
	private AccountCashBalance accountCashBalance = null;
	private Context context = InstrumentationRegistry.getTargetContext();
	;
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
	    accountCashBalance = null;
	    
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
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear), EPSILON);
	}

	@Test
	public void testNumberOfYearsEqualsTwo() {
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = 2;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear+1), EPSILON);
	}
	
	@Test
	public void testDeathAge() {
		int numberOfWithdrawals = 0;
		double annualContribution = 0.0;
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = deathAge - currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear+numberOfYears-1), EPSILON);
	}

	@Test
	public void testDeathAgePlusOne() {
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = deathAge - currentAge + 1;
	    assertEquals(0.0, accountCashBalance.getEndingValue(currentYear+numberOfYears), EPSILON);
	}

	@Test
	public void testNumberOfWithdrawals() {
		double annualContribution = 0.0;
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 

	    int numberOfYears = startWithdrawalsAge-currentAge-1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear+numberOfYears-1), EPSILON);
	}

	@Test
	public void testWithdraw() {
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    accountCashBalance.withdraw(currentAge, 10.0);
	    
	    assertEquals(10.0, accountCashBalance.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void withdrawInvalidBalance() {
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    // Withdraw the account balance instead
	    accountCashBalance.withdraw(currentAge, balance+40.0);
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testDeposit() {
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    accountCashBalance.deposit(currentAge, 10.0);
	    
	    assertEquals(10.0 + annualContribution, accountCashBalance.getDeposits(currentYear), EPSILON);
	}
		
	@Test
	public void testIsTaxable() {
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    assertEquals(true, accountCashBalance.isTaxable());
	}
	
	@Test
	public void testVerifyMinAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyMinGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MIN_GROWTH_RATE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MAX_GROWTH_RATE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMinAnnualContribution() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		annualContribution = validateInputs.MIN_ANNUAL_CONTRIBUTION;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAnnualContribution() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		annualContribution = validateInputs.MAX_ANNUAL_CONTRIBUTION;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), accountCashBalance.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMinWithdrawalAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		startWithdrawalsAge = validateInputs.MIN_AGE+1;
		
		numberOfWithdrawals = 1;
		
    	accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = 1;	    
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), 
	    			 accountCashBalance.getWithdrawals(currentYear + numberOfWithdrawals), EPSILON);
	}
	
	@Test
	public void testVerifyMaxWithdrawalAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
	    startWithdrawalsAge = validateInputs.MAX_AGE-1;
	    deathAge = validateInputs.MAX_AGE;		
		numberOfWithdrawals = 1;
		annualContribution = 0.0;

	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), 
	    		     accountCashBalance.getWithdrawals(currentYear + numberOfYears), EPSILON);
	}
	
	@Test
	public void testVerifyMinNumberOfWithdrawal() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		numberOfWithdrawals = validateInputs.MIN_NUMBER_OF_WITHDRAWALS;
		
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    
	    assertEquals(0.0,  accountCashBalance.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMaxNumberOfWithdrawal() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		numberOfWithdrawals = validateInputs.MAX_NUMBER_OF_WITHDRAWALS;
		currentAge = 18;
		deathAge = validateInputs.MAX_AGE;
		
	    accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals); 
	    	    
	    assertEquals(357.51, accountCashBalance.getWithdrawals(currentYear + numberOfWithdrawals - 1), EPSILON);
	}

	@Test
	public void testAnnualContributions() {
		currentAge = 55;
		retirementAge = 56;

		// 1 year of contributions
		annualContribution = 1000.0;

		accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(annualContribution, is(closeTo(accountCashBalance.getDeposits(currentYear), EPSILON)));
	}

	@Test
	public void testGetBalance() {
		accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(balance, is(closeTo(accountCashBalance.getBalance(), EPSILON)));
	}

	@Test
	public void testGetGrowthRate() {
		accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(growthRate, is(closeTo(accountCashBalance.getGrowthRate(), EPSILON)));
	}

	@Test
	public void testGetCurrentAge() {
		accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(currentAge, is(accountCashBalance.getCurrentAge()));
	}

	@Test
	public void testGetCurrentYear() {
		accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(currentYear, is(accountCashBalance.getCurrentYear()));
	}

	@Test
	public void testGetDeathAge() {
		accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(deathAge, is(accountCashBalance.getDeathAge()));
	}

	@Test
	public void testGetStartWithdrawalsAge() {
		accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(startWithdrawalsAge, is(accountCashBalance.getStartWithdrawalsAge()));
	}

	@Test
	public void testGetNumberOfWithdrawals() {
		accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

		assertThat(numberOfWithdrawals, is(accountCashBalance.getNumberOfWithdrawals()));
	}
}
