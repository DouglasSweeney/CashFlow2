package com.doug.cashflow.model.accounts;

import android.support.annotation.NonNull;

public class Pension extends Account {

	public Pension(double monthlyAmount, int startAge, @NonNull boolean inflationAdjusted, double inflation,
				   int currentAge, int currentYear, int deathAge) {

		if (inflationAdjusted)
			initialize(monthlyAmount * 12, inflation, currentAge, currentYear, deathAge);
		else		
	    	initialize(monthlyAmount * 12, 0.0, currentAge, currentYear, deathAge);

		/*  Son't show pension as income from currentAge to startAge; set to 0.0 for this ages */
		for (int i=currentAge; i<startAge; i++) {
            setZeroBeginningValue(i);
		}        
	}
	
	/**
	 * Get the taxability of this account.
	 *
	 * @return boolean
	 */
	public boolean isTaxable() {
		return true;
	}
}
