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

public class TaxesIntegrationTest {
	private static final double EPSILON = 1e-2;
	private Taxes taxes = null;
	private Context context = InstrumentationRegistry.getTargetContext();

	private int currentAge;
	private int currentYear;
	private int deathAge;
	private double federalTaxRate;
	private double stateTaxRate;

	private UtilsIntegrationTest utils = new UtilsIntegrationTest();
	private double taxableIncome;
	private double tax;
    
	@Before
	public void Setup() {
	    taxes = null;
	    
	    currentAge = 57;
	    currentYear = 2016;
	    deathAge = 95;
	    federalTaxRate = 0.28;
	    stateTaxRate = 0.05;
	}
	
	@Test
	public void testHappyCase() {
	    taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
	    taxableIncome = 100.0;
	    
	    tax = utils.computeTaxes(taxableIncome, federalTaxRate, stateTaxRate);
	    assertEquals(tax, taxes.compute(currentAge, taxableIncome), EPSILON);
	}
	
	@Test
	public void testClearList() {
	    taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
	    taxableIncome = 100.0;
	    
	    tax = utils.computeTaxes(taxableIncome, federalTaxRate, stateTaxRate);
	    taxes.deposit(currentAge, tax);
	    taxes.clearListOfValues();
	    assertEquals(0.0, taxes.getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMinCurrentAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE + 1;
		deathAge = validateInputs.MIN_AGE + 3;
		
		taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
	
		tax = utils.computeTaxes(taxableIncome, federalTaxRate, stateTaxRate);
	    taxes.deposit(currentAge, tax);
	    
	    assertEquals(tax, taxes.getDeposits(currentYear), EPSILON);
	}
	
	@Test
	public void testVerifyMaxCurrentAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE;
		deathAge = validateInputs.MAX_AGE+2;
		
		taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
		
		tax = utils.computeTaxes(taxableIncome, federalTaxRate, stateTaxRate);
	    taxes.deposit(currentAge, tax);
	    
	    assertEquals(tax, taxes.getDeposits(currentYear), EPSILON);
	}

	@Test
	public void testVerifyCurrentYear() {
		currentYear = 3000;
		
		taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
		
		tax = utils.computeTaxes(taxableIncome, federalTaxRate, stateTaxRate);
	    taxes.deposit(currentAge, tax);
	    
	    assertEquals(tax, taxes.getDeposits(currentYear), EPSILON);
	}	

	@Test
	public void testVerifyMinDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		deathAge = validateInputs.MIN_AGE + 2;
		
		taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
		
		int age = (deathAge-currentAge)-1;
		tax = utils.computeTaxes(taxableIncome, federalTaxRate, stateTaxRate);
	    taxes.deposit(age, tax);
	    
	    assertEquals(tax, taxes.getDeposits(currentYear+age-1), EPSILON);
	}

	@Test
	public void testVerifyMaxDeathAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MAX_AGE;
		deathAge = validateInputs.MAX_AGE+1;
		
		taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
		
		int age = (deathAge-currentAge)-1;
		tax = utils.computeTaxes(taxableIncome, federalTaxRate, stateTaxRate);
	    taxes.deposit(age, tax);
	    
	    assertEquals(tax, taxes.getDeposits(currentYear+age-1), EPSILON);
	}

	@Test
	public void testVerifyInvalidYear() {
		taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
			    
	    assertEquals(0.0, taxes.getEndingValue(currentYear+3000), EPSILON);
	}
	
	@Test
	public void testMinStateRate() {
		stateTaxRate = 0.0;
		
	    taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
	    taxableIncome = 100.0;
	    
	    assertEquals(28.0, taxes.compute(currentAge, taxableIncome), EPSILON);
	}
	
	@Test
	public void testMaxStateRate() {
		federalTaxRate = 0.0;
		stateTaxRate = 1.00; // 100%
		
	    taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
	    taxableIncome = 100.0;
	    
	    assertEquals(100.0, taxes.compute(currentAge, taxableIncome), EPSILON);
	}
	@Test
	public void testMinFederalRate() {
		federalTaxRate = 0.0;
		
	    taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
	    taxableIncome = 100.0;
	    
	    assertEquals(5.0, taxes.compute(currentAge, taxableIncome), EPSILON);
	}

	@Test
	public void testMaxFederalRate() {
		stateTaxRate = 0.0;
		federalTaxRate = 1.00; // 100%

		taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
		taxableIncome = 100.0;

		assertEquals(100.0, taxes.compute(currentAge, taxableIncome), EPSILON);
	}

	@Test
	public void testNegativeFederalRate() {
		federalTaxRate = -federalTaxRate;
        stateTaxRate = 0.05;

		taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
        taxableIncome = 100.0;

		assertThat(5.0, is(closeTo(taxes.compute(currentAge, taxableIncome), EPSILON)));
	}


	@Test
	public void testNegativeStateRate() {
		federalTaxRate = 0.05;
		stateTaxRate = -stateTaxRate;

		taxes = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
		taxableIncome = 100.0;

		assertThat(5.0, is(closeTo(taxes.compute(currentAge, taxableIncome), EPSILON)));
	}
}