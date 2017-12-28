package com.doug.cashflow.model.accounts;

/**
 * Implementation of an Cash Balance account.
 *
 * @version release-1.0.0 Initial version
 * @author D.K.Sweeney 5/14/2016
 *
 */
public class AccountCashBalance extends Account implements Accounts {
	double balance;
    double growthRate;
    double annualContribution;
    int    currentAge;
    int    currentYear;
    int    deathAge;
    int    startWithdrawalsAge;
    int    numberOfWithdrawals;
    
    /**
     * This method builds the initial data structure.
     *
     * @param double balance             The current account balance.
     * @param double growthRate          The expected yearly growth rate of the account.
     * @param double annualContribution  The annual contribution to the account
     * @param int    currentAge          The current age of the person
     * @param int    currentYear         The current year of the person owning the account
     * @param int    deathAge            The expected death age of the person
     * @param int    startwithdrawalsAge The age to start taking withdrawals
     * @param int    numberOfWithdrawals The number of withdrawals to make 
     *
     */
   public AccountCashBalance(double balance, double growthRate, double annualContribution, int currentAge, 
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