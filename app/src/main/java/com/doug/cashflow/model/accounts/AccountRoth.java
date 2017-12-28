package com.doug.cashflow.model.accounts;

/**
 * Implementation of a Roth account.
 *
 * @version release-1.0.0 Initial version
 * @author D.K.Sweeney 1/26/2016
 *
 */
public class AccountRoth extends Account implements Accounts {
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
     * @param double balance             The current yearly expenses.
     * @param double growthRate          The expected yearly growth rate of the expenses.
     * @param double annualContribution  The annual contribution to the account
     * @param int    currentAge          The current age of the person
     * @param int    currentYear         The current year of the person
     * @param int    retirementAge       The retirement age of the person
     * @param int    deathAge            The expected death age of the person
     * @param int    startWithdrawalsAge The age to start periodic withdrawals from this account
     * @param int    numberOfWithdrawals The number of years to take the periodic withdrawals
     *
     * @return void
     */
    public AccountRoth(double balance, double growthRate, double annualContribution, int currentAge, 
    		           int currentYear, int retirementAge, int deathAge, int startWithdrawalsAge, 
    		           int numberOfWithdrawals) {
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
    

     /**
     * Get the taxability of this account.
     *
     * @return boolean
     */
    public boolean isTaxable() {
        return false;
    }
    
    /**
     * Get the balance
     * 
	 * @return double
	 */
    
	public double getBalance() {
		return balance;
	}

	/**
	 * Get the growth Rate
	 * 
	 * @return double
	 */
	public double getGrowthRate() {
		return growthRate;
	}

	/**
	 * Get the current age.
	 * 
	 * @return int
	 */
	public int getCurrentAge() {
		return currentAge;
	}

	/**
	 * Get the current year 
	 * 
	 * @return int
	 */
	public int getCurrentYear() {
		return currentYear;
	}
    
	/**
	 * Get the death age
	 * 
	 * @return int
	 */
	public int getDeathAge() {
		return deathAge;
	}
	
	/**
	 * Get the starting age for withdrawal
	 * 
	 * @return int
	 */
	public int getStartWithdrawalsAge() {
		return startWithdrawalsAge;
	}
	
	/**
	 * Get the number of withdrawals
	 * 
	 * @return int
	 */
 	public int getNumberOfWithdrawals() {
		return numberOfWithdrawals;
	}
/*
 	public static void main(String[] args) {

		AccountIraRoth accountRoth = new AccountIraRoth(354000.0, 0.04, 0.0, 57, 2016, 62, 95, 59, 5);
		ResultsDataNode node;

    	for (int i=0; i<accountRoth.getList().size(); i++) {
    		node = accountRoth.getList().get(i);
    		
    		System.out.print(node.getAge() + " ");
    		System.out.print(node.getBeginningValue() + " ");
    		System.out.print(node.getWithdrawal() + " ");
    		System.out.println(node.getEndingValue());
    	}
    }
*/    
}
