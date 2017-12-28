package com.doug.cashflow.model.accounts;

public class Deductions extends Account {

	public Deductions(double deductions, double growthRate, int currentAge, int currentYear, int deathAge) {
		initialize(deductions, growthRate, currentAge, currentYear, deathAge);	
	}
}
