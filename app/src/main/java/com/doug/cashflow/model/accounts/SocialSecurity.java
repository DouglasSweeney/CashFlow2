package com.doug.cashflow.model.accounts;

import com.doug.cashflow.model.system.ResultsDataNode;

public class SocialSecurity extends Account {

    private final static String TAG = "model/SocialSecurity()";
	public SocialSecurity(double monthlyAmount, int startAge, double inflation, int currentAge, int currentYear, int retirementAge, int deathAge) {
        Double value = monthlyAmount * 12;
        Double beginningValue = value;
        Integer age;
        Double growthRate = inflation;
        Integer year;
        Integer yearToCollectSocialSecurity;

        initialize(monthlyAmount * 12, inflation, currentAge, currentYear, deathAge);

        if (currentAge < startAge) {
            for (int i = currentAge; i < startAge; i++) {
                setZeroEndingValue(i);
            }
        }

		yearToCollectSocialSecurity = currentYear + (startAge - currentAge);
        year = yearToCollectSocialSecurity;

        for (age=startAge; age < deathAge; age++) {
            value += (value * growthRate); // value at the end of the year

            ResultsDataNode node=getNodeBasedOnAge(age);
			if (node != null) {
				node.aSet1(year, 0, age, beginningValue, beginningValue);
			}

            year++;
            beginningValue=value; // Set for the following year
        }
    }

	public boolean isTaxable() {
		return true;
	}
}
