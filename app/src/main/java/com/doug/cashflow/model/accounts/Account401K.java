package com.doug.cashflow.model.accounts;

/**
 * Implementation of an 401K account.
 *
 * @version release-1.0.0 Initial version
 * @author D.K.Sweeney 1/22/2016
 *
 */
public class Account401K extends Account implements Accounts {
	private double balance;
    private double growthRate;
    private double annualContribution;
    private int    currentAge;
    private int    currentYear;
	private int    retirementAge;
    private int    deathAge;
    private int    startWithdrawalsAge;
    private int    numberOfWithdrawals;

    public Account401K(double balance, double growthRate, double annualContribution, int currentAge,
					  int currentYear, int retirementAge, int deathAge,
					  int startWithdrawalsAge, int numberOfWithdrawals) {

        this.balance = balance;
	    this.growthRate = growthRate;
	    this.annualContribution = annualContribution;
	    this.currentAge = currentAge;
	    this.currentYear = currentYear;
	    this.retirementAge = retirementAge;
	    this.deathAge = deathAge;
	    this.startWithdrawalsAge = startWithdrawalsAge;
	    this.numberOfWithdrawals = numberOfWithdrawals;
        
        initialize(balance, growthRate, currentAge, currentYear, deathAge);

        addInAnnualContributions(currentAge, retirementAge, annualContribution);

        if (numberOfWithdrawals > 0) {
			computePeriodicWithdrawals(currentAge, startWithdrawalsAge, numberOfWithdrawals);
		}
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
	 * @return The growthRate
	 */
	public double getGrowthRate() {
		return growthRate;
	}

	/**
	 * @return The currentAge
	 */
	public int getCurrentAge() {
		return currentAge;
	}

	/**
	 * @return The currentYear
	 */
	public int getCurrentYear() {
		return currentYear;
	}

	/**
	 * @return The deathAge
	 */
	public int getDeathAge() {
		return deathAge;
	}
	
	/**
	 * @return The startWithdrawalsAge
	 */
	public int getStartWithdrawalsAge() {
		return startWithdrawalsAge;
	}
	
	/**
	 * @return The numberOfWithdrawals
	 */
	public int getNumberOfWithdrawals() {
		return numberOfWithdrawals;
	}
	/**
	 * @return The RetirementAge
	 */
	public int getRetirementAge() {
		return retirementAge;
	}

	/**
	 * @return The annual contribution
	 */
	public double getAnnualContribution() {
		return annualContribution;
	}
}
