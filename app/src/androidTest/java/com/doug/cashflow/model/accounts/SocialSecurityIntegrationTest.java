package com.doug.cashflow.model.accounts;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.doug.cashflow.model.system.ValidateInputs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SocialSecurityIntegrationTest {
	private static final double EPSILON = 1e-2;
	private static final int    MONTHS_IN_YEAR = 12;

	private SocialSecurity socialSecurity = null;
	private Context        context = InstrumentationRegistry.getTargetContext();
	private double         monthlyAmount = 0;
	private int            startAge;
	private double         inflation;
	private int            currentAge;
	private int            currentYear;
	private int            deathAge;
	private int            retirementAge;
	private UtilsIntegrationTest utils = new UtilsIntegrationTest();
	private double         annualAmount;
    
	@Before
	public void Setup() {
	    socialSecurity = null;
	    
	    monthlyAmount = 100.0;
	    startAge = 62;
	    inflation = 0.05;
	    currentAge = 57;
	    currentYear = 2016;
	    deathAge = 95;
		retirementAge = 53;
	} 
	
	@Test
	public void testHappyCase() {
	    socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    int numberOfYears = 10;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, socialSecurity.getEndingValue(currentYear+(startAge-currentAge)+numberOfYears), EPSILON);
	}
	
	@Test
	public void testVerifyMinStartAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
        int numberOfYears = 10;
		startAge = validateInputs.MIN_SOCIAL_SECURITY_AGE;
		deathAge = validateInputs.MIN_SOCIAL_SECURITY_AGE + numberOfYears + 1;
		
		socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);

        annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, socialSecurity.getEndingValue(currentYear + (startAge - currentAge + 1) + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxStartAge() {
        int numberOfYears = 10;
		ValidateInputs validateInputs = new ValidateInputs(context);
		startAge = validateInputs.MAX_SOCIAL_SECURITY_AGE;
		
	    socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears) * MONTHS_IN_YEAR;
        double expected = socialSecurity.getEndingValue(currentYear + (startAge - currentAge + 1) + numberOfYears - 1);
	    assertEquals(annualAmount, expected, EPSILON);
	}

	@Test
	public void testVerifyMinInflation() {
        int numberOfYears = 10;
		ValidateInputs validateInputs = new ValidateInputs(context);
	    inflation  = validateInputs.MIN_GROWTH_RATE;
		
	    socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears) * MONTHS_IN_YEAR;
        double actual = socialSecurity.getEndingValue(currentYear + (startAge - currentAge + 1) + numberOfYears - 1);

	    assertEquals(annualAmount, actual, EPSILON);
	}
	
	@Test
	public void testVerifyMaxInflation() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		inflation = validateInputs.MAX_GROWTH_RATE;
		
	    socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    int numberOfYears = 10;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears) * MONTHS_IN_YEAR;
        double actual = socialSecurity.getEndingValue(currentYear + (startAge - currentAge + 1) + numberOfYears - 1);
        if (annualAmount > 1e17) {
            annualAmount /= 1e17;
            actual /= 1e17;
        }
	    assertEquals(annualAmount, actual, EPSILON);
	}
	
	@Test
	public void testVerifyMinCurrentAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		currentAge = validateInputs.MIN_AGE;
		
		socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    int numberOfYears = 10;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, socialSecurity.getEndingValue(currentYear + (startAge - currentAge + 1) + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxCurrentAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		int numberOfYears = 10;
		currentAge = validateInputs.MAX_AGE-numberOfYears;
		deathAge = validateInputs.MAX_AGE;
		
	    socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
//	    int numberOfYears = deathAge-startAge - 1;

	    annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears, startAge, currentAge) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, socialSecurity.getEndingValue(currentYear+(deathAge-currentAge-1)), EPSILON);
	}

	@Test
	public void testVerifyCurrentYear() {
		currentYear = 3000;
		
	    socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    int numberOfYears = deathAge-startAge - 1;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears) * MONTHS_IN_YEAR;
        double actual = socialSecurity.getEndingValue(currentYear + (startAge - currentAge + 1) + numberOfYears - 1);

	    assertEquals(annualAmount, actual, EPSILON);
	}	
	
	@Test
	public void testVerifyMinSocialSecurityAge() {
		ValidateInputs validateInputs = new ValidateInputs(context);
		startAge = validateInputs.MIN_SOCIAL_SECURITY_AGE;
		
		socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    int numberOfYears = 10;
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears) * MONTHS_IN_YEAR;
	    assertEquals(annualAmount, socialSecurity.getEndingValue(currentYear + (startAge - currentAge + 1) + numberOfYears - 1), EPSILON);
	}
	
	@Test
	public void testVerifyMaxSocialSecurityAge() {
        int numberOfYears = 10;
		ValidateInputs validateInputs = new ValidateInputs(context);
		startAge = validateInputs.MAX_SOCIAL_SECURITY_AGE;
		
	    socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    annualAmount = utils.computeEndingValue(monthlyAmount, inflation, numberOfYears, startAge, currentAge) * MONTHS_IN_YEAR;
        double actual = socialSecurity.getEndingValue(currentYear + (startAge - currentAge + 1) + numberOfYears - 1);

	    assertEquals(annualAmount, actual, EPSILON);
	}
	
	@Test
	public void testVerifyTaxability() {
	    socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    assertEquals(true, socialSecurity.isTaxable());
	}
	
	@Test
	public void testVerifySocialSecurityIsZero() {
	    socialSecurity = new SocialSecurity(monthlyAmount, startAge, inflation, currentAge, currentYear, retirementAge, deathAge);
	    
	    int numberOfYears = 1;
	    assertEquals(0.0, socialSecurity.getEndingValue(currentYear+numberOfYears), EPSILON);
	}
}
