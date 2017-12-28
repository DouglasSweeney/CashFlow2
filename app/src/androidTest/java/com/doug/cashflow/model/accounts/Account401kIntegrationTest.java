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

public class Account401kIntegrationTest extends Account {
	private static final double EPSILON = 1e-2;
	private Account401K account401k = null;
	private final Context context = InstrumentationRegistry.getTargetContext();
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
	    account401k = null;

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
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear), EPSILON);
	}

	@Test
	public void testNumberOfYearsEqualsTwo() {
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

	    int numberOfYears = 2;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear+1), EPSILON);
	}
	
	@Test
	public void testDeathAge() {
		int numberOfWithdrawals = 0;
		double annualContribution = 0.0;
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

	    int numberOfYears = deathAge - currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear+numberOfYears-1), EPSILON);
	}

	@Test
	public void testDeathAgePlusOne() {
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

	    int numberOfYears = deathAge - currentAge + 1;
	    assertEquals(0.0, account401k.getEndingValue(currentYear+numberOfYears), EPSILON);
	}

	@Test
	public void testNumberOfWithdrawals() {
		double annualContribution = 0.0;
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

	    int numberOfYears = startWithdrawalsAge-currentAge-1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear+numberOfYears-1), EPSILON);
	}

	@Test
	public void testWithdraw() {
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    account401k.withdraw(currentAge, 10.0);
	    
	    assertEquals(10.0, account401k.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void withdrawInvalidBalance() {
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    // Withdraw the account balance instead
	    account401k.withdraw(currentAge, balance+40.0);
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testDeposit() {
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    account401k.deposit(currentAge, 10.0);
	    
	    assertEquals(10.0 + annualContribution, account401k.getDeposits(currentYear), EPSILON);
	}
		
	@Test
	public void testIsTaxable() {
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    assertEquals(true, account401k.isTaxable());
	}
	
	@Test
	public void testVerifyMinAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);;
		currentAge = validateInputs.MIN_AGE;
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);;
		currentAge = validateInputs.MAX_AGE-2;
		deathAge = validateInputs.MAX_AGE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}

	@Test
	public void testVerifyMinGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);;
		growthRate = validateInputs.MIN_GROWTH_RATE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxGrowthRate() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		growthRate = validateInputs.MAX_GROWTH_RATE;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMinAnnualContribution() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		annualContribution = validateInputs.MIN_ANNUAL_CONTRIBUTION;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    int numberOfYears = 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxAnnualContribution() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		annualContribution = validateInputs.MAX_ANNUAL_CONTRIBUTION;
		
		numberOfWithdrawals = 0;
		annualContribution = 0.0;
		
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), account401k.getEndingValue(currentYear + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMinWithdrawalAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		startWithdrawalsAge = validateInputs.MIN_AGE+1;
		
		numberOfWithdrawals = 1;
		
    	account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    int numberOfYears = 1;	    
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), 
	    			 account401k.getWithdrawals(currentYear + numberOfWithdrawals), EPSILON);
	}
	
	@Test
	public void testVerifyMaxWithdrawalAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
	    startWithdrawalsAge = validateInputs.MAX_AGE-1;
	    deathAge = validateInputs.MAX_AGE;		
		numberOfWithdrawals = 1;
		annualContribution = 0.0;

	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    int numberOfYears = deathAge-currentAge - 1;
	    assertEquals(computeEndingValue(balance, growthRate, numberOfYears, annualContribution), 
	    		     account401k.getWithdrawals(currentYear + numberOfYears), EPSILON);
	}
	
	@Test
	public void testVerifyMinNumberOfWithdrawal() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		numberOfWithdrawals = validateInputs.MIN_NUMBER_OF_WITHDRAWALS;
		
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    
	    assertEquals(0.0,  account401k.getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMaxNumberOfWithdrawal() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		numberOfWithdrawals = validateInputs.MAX_NUMBER_OF_WITHDRAWALS;
		currentAge = 18;
		deathAge = validateInputs.MAX_AGE;
		
	    account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);
	    	    
	    assertEquals(357.51, account401k.getWithdrawals(currentYear + numberOfWithdrawals - 1), EPSILON);
	}

    @Test
    public void testAnnualContributions() {
        currentAge = 55;
        retirementAge = 56;

        // 1 year of contributions
        annualContribution = 1000.0;

        account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        assertThat(annualContribution, is(closeTo(account401k.getDeposits(currentYear), EPSILON)));
    }

    @Test
    public void testGetBalance() {
        account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        assertThat(balance, is(closeTo(account401k.getBalance(), EPSILON)));
    }

    @Test
    public void testGetGrowthRate() {
        account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        assertThat(growthRate, is(closeTo(account401k.getGrowthRate(), EPSILON)));
    }

    @Test
    public void testGetCurrentAge() {
        account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        assertThat(currentAge, is(account401k.getCurrentAge()));
    }

    @Test
    public void testGetCurrentYear() {
        account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        assertThat(currentYear, is(account401k.getCurrentYear()));
    }

    @Test
    public void testGetDeathAge() {
        account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        assertThat(deathAge, is(account401k.getDeathAge()));
    }

    @Test
    public void testGetStartWithdrawalsAge() {
        account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        assertThat(startWithdrawalsAge, is(account401k.getStartWithdrawalsAge()));
    }

    @Test
    public void testGetNumberOfWithdrawals() {
        account401k = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge, deathAge, startWithdrawalsAge, numberOfWithdrawals);

        assertThat(numberOfWithdrawals, is(account401k.getNumberOfWithdrawals()));
    }
}