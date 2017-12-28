package com.doug.cashflow.model.accounts;

public class Salary extends Account {
    
    public Salary(double salary, double meritIncrease, int currentAge, int currentYear, int deathAge, int retirementAge) {
    	int age;
    	
        initialize(salary, meritIncrease, currentAge, currentYear, deathAge);        
        
        age = retirementAge;
        while (age < deathAge) {
        	zeroBeginningValues(age);
            zeroEndingValues(age);
           	age++;
        }
    }
}