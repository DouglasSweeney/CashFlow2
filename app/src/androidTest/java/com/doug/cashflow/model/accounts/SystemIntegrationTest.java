package com.doug.cashflow.model.accounts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SystemIntegrationTest {
	private static final double EPSILON = 1e-2;

	private Expenses expenses = null;
	private Brokerage brokerage = null;
	private Salary salary = null;
	private Account401K account401k = null;
	private Account403B account403b = null;
	private AccountCashBalance accountCashBalance = null;
	private AccountRoth accountRoth = null;
	private AccountIra accountIra = null;
	private Pension pension = null;
	private SocialSecurity socialSecurity = null;
	private Deductions deductions = null;
	private Taxes taxes = null;

	private UtilsIntegrationTest utils = new UtilsIntegrationTest();

	private double  balance;
	private double  inflation;
	private double  growthRate;
	private double  annualContribution;
	private int     currentAge;
	private int     currentYear;
	private int     retirementAge;
	private int     deathAge;
	private int     startWithdrawalsAge;
	private int     numberOfWithdrawals;
	private double  expense;
	private double  annualSalary;
	private double  deduction;
	private double  monthlyAmount = 0;
	private boolean inflationAdjusted;
	private double  annualAmount = 0;
	private double  meritIncrease;
	private double  federalTaxRate;
	private double  stateTaxRate;
   
	@Before
	public void Setup() {
		expenses = null;
		brokerage = null;
		salary = null;
		account401k = null;
		account403b = null;
		accountCashBalance = null;
		accountRoth = null;
		accountIra = null;
		pension = null;
		socialSecurity = null;
		deductions = null;
	    taxes = null;
	    
	    balance = 100.00;
	    inflation = 0.03;
		growthRate = 0.05;
		annualContribution = 10.0;
		currentAge = 57;
		currentYear = 2016;
		retirementAge = 62;
	    deathAge = 95;
	    startWithdrawalsAge = 59;
	    numberOfWithdrawals = 0;
		expense = 10.0;
		deduction = 0;
		monthlyAmount = 1550.0;
		inflationAdjusted = true;
		annualAmount = 0;
		meritIncrease = 0.02;
	    federalTaxRate = 0.28;
	    stateTaxRate = 0.05;
	 } 
	

	@Test
	public void testHappyCase() {
		int    year;
		double totalIncome;
		double tax;
		
	    expenses = new Expenses(expense, inflation, currentAge,
                                currentYear, deathAge); 
	    brokerage = new Brokerage(balance, growthRate, currentAge,
	    		                currentYear, deathAge);
        salary = new Salary(annualSalary, meritIncrease, currentAge,
        		                currentYear, deathAge, retirementAge);
        account401k = new Account401K(balance, growthRate, annualContribution,
        						currentAge, currentYear, retirementAge,
        						deathAge, startWithdrawalsAge, numberOfWithdrawals);
        account403b = new Account403B(balance, growthRate, annualContribution,
	            				currentAge, currentYear, retirementAge,
	            				deathAge, startWithdrawalsAge, numberOfWithdrawals);
        accountCashBalance = new AccountCashBalance(balance, growthRate, annualContribution,
	            				currentAge, currentYear, retirementAge,
	            				deathAge, startWithdrawalsAge, numberOfWithdrawals);
        accountIra = new AccountIra(balance, growthRate, annualContribution,
	            				currentAge, currentYear, retirementAge,
	            				deathAge, startWithdrawalsAge, numberOfWithdrawals);
        accountRoth = new AccountRoth(balance, growthRate, annualContribution,
	            				currentAge, currentYear, retirementAge,
	            				deathAge, startWithdrawalsAge, numberOfWithdrawals);
        pension = new Pension(monthlyAmount, startWithdrawalsAge, inflationAdjusted, 
        		                inflation, currentAge, currentYear, deathAge);
        socialSecurity = new SocialSecurity(monthlyAmount, startWithdrawalsAge, inflation, 
        		                currentAge, currentYear, retirementAge, deathAge);
        deductions = new Deductions(deduction, inflation, currentAge, 
        		                currentYear, deathAge);
        taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate,
        						stateTaxRate);
        
        year = currentYear; // get to startWithdrawslsAge
        totalIncome = account401k.getEndingValue(year) + account403b.getEndingValue(year) +
        		      accountCashBalance.getEndingValue(year) + accountIra.getEndingValue(year) +
        		      pension.getEndingValue(year) + socialSecurity.getEndingValue(year);
        tax = taxes.compute(currentAge, totalIncome);

        assertEquals(tax, taxes.getEndingValue(currentYear), EPSILON);
	}
}
