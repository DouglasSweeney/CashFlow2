package com.doug.cashflow.model.accounts;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsIntegrationTest {
	
	public double computeEndingValue(double balance, double growthRate, int numberOfYears, double annualContribution) {
		double value = balance;

		for (int i=0; i<numberOfYears; i++) {
			value += annualContribution;
			value += value * growthRate;
		}

		return value;
	}

    public double computeEndingValue(double balance, double growthRate, int numberOfYears) {
        double value = balance;

        for (int i=0; i<numberOfYears; i++) {
            value += value * growthRate;
        }

        return value;
    }
    public double computeEndingValue(double balance, double growthRate, int numberOfYears, int startAge, int currentAge) {
        double value = balance;

        if (startAge < currentAge) {
            for (int i = startAge + 1; i < currentAge; i++) {
                value += (value * growthRate);
            }
        }

        for (int i=0; i<numberOfYears; i++) {
            value += value * growthRate;
        }

        return value;
    }


    public double computeEndingValue(double amount, boolean inflationAdjusted, double inflation, int numberOfYears) {
		double value = amount;

		for (int i=0; i<numberOfYears; i++) {
			if (inflationAdjusted == true) {
				value += value * inflation;
			}
		}

		return value;
	}
	
	public double computeTaxes(double taxableIncome, double federalTaxRate, double stateTaxRate) {
		double totalTaxes = 0.0;
		
		totalTaxes += taxableIncome * federalTaxRate;
		totalTaxes += taxableIncome * stateTaxRate;
		
		return totalTaxes;
	}
	
	@Test
	public void testAddedForXmlError() { 
	    assertEquals(false, false);
	}
	
}