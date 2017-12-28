package com.doug.cashflow.model.accounts;

public class Account403B extends Account implements Accounts {
	double balance;
    double growthRate;
    double annualContribution;
    int    currentAge;
    int    currentYear;
    int    deathAge;
    int    startWithdrawalsAge;
    int    numberOfWithdrawals;
    
    public Account403B(double balance, double growthRate, double annualContribution, int currentAge,
		              int currentYear, int retirementAge, int deathAge,
    		          int startWithdrawalsAge, int numberOfWithdrawals) {
        this.balance = balance;
	    this.growthRate = growthRate;
	    this.annualContribution = annualContribution;
	    this.currentAge = currentAge;
	    this.currentYear = currentYear;
	    this.deathAge = deathAge;
	    this.startWithdrawalsAge = startWithdrawalsAge;
	    this.numberOfWithdrawals = numberOfWithdrawals;
        
        initialize(balance, growthRate, currentAge, currentYear, deathAge);

        addInAnnualContributions(currentAge, retirementAge, annualContribution);

        computePeriodicWithdrawals(currentAge, startWithdrawalsAge, numberOfWithdrawals);
    }

	public boolean isTaxable() {
        return true;
    }

    /**
     * @return the balance
     */
	public double getBalance() {
		return balance;
	}

	/**
	 * @return the growthRate
	 */
	public double getGrowthRate() {
		return growthRate;
	}

	/**
	 * @return the currentAge
	 */
	public int getCurrentAge() {
		return currentAge;
	}

	/**
	 * @return the currentYear
	 */
	public int getCurrentYear() {
		return currentYear;
	}

	/**
	 * @return the deathAge
	 */
	public int getDeathAge() {
		return deathAge;
	}
	
	/**
	 * @return the startWithdrawalsAge
	 */
	public int getStartWithdrawalsAge() {
		return startWithdrawalsAge;
	}
	
	/**
	 * @return the numberOfWithdrawals
	 */
	public int getNumberOfWithdrawals() {
		return numberOfWithdrawals;
	}
}
