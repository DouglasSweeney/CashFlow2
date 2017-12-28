package com.doug.cashflow.model.system;


/**
 * Implementation of a node in the data structuret. 
 *
 * @version release-1.0.0 Initial version
 * @author D.K.Sweeney 10/30/2014
 *
 */

public class ResultsDataNode {

	/**
	 * A member of a node
	 * 
	 * @var int age 
	 */
	public int age = 0;
    
    /**
     * A member of a node
     * 
     * @var int year 
     */
    public int year = 0;
			    
	/**
	 * A member of a node
	 * 
	 * @var int month 
	 */
	private int month = 0;
			    
	/**
	 * A member of a node
	 * 
	 * @var double pension 
	 */
     private double pension = 0.0;
			    
	/**
	 * A member of a node
	 * 
	 * @var double salary 
	 */
     private double salary = 0.0;
			    
	/**
	 * A member of a node
	 * 
	 * @var double value401K 
	 */
     private double value401K = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double value403B 
     */
     private double value403B = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double valueRoth 
     */
     private double valueRoth = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double valueCashBalance 
     */
     private double valueCashBalance = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double ssDougsValue 
     */
    private double ssDougsValue = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double ssLauriesValue 
     */
    private double ssLauriesValue = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double socialSecurity 
     */
    private double socialSecurity = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double beginningValue 
     */
    private double beginningValue = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double deposit 
     */
    private double deposit = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double withdrawal 
     */
    private double withdrawal = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double endingValue
     */
    private double endingValue = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double dougsIncome 
     */
    private double dougsIncome = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double lauriesIncome 
     */
    private double lauriesIncome = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double taxableWithdrawal 
     */
    private double taxableWithdrawal = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double federalTaxes 
     */
    private double federalTaxes = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double stateTaxes 
     */
    private double stateTaxes = 0.0;
			    
    /**
     * A member of a node
     * 
     * @var double $taxes 
     */
    private double taxes = 0.0;

	 public ResultsDataNode() {

	 }

     public void aSet1(int aYear, int aMonth, int aAge, double aBeginningValue, double aEndingValue) {
	     year = aYear;
	     month = aMonth;
	     age = aAge;
	     beginningValue = aBeginningValue;
	     withdrawal = 0.0;
	     endingValue = aEndingValue;
	 }
			    
     public void aSet2(int aYear, int aAge, double aBeginningValue, double aEndingValue) {
        year = aYear;
        month = 0;
        age = aAge;
        beginningValue = aBeginningValue;
        withdrawal = 0.0;
        endingValue += aEndingValue;
	}
			    
	public void setBeginningValue(double value) {
        beginningValue = value;
	}
			    
	public void setEndingValue(double value) {
        endingValue = value;
	}
			    
	public void addDeposit(double aDeposit) {
        deposit += aDeposit;
	}
			    
	public void setPension(double value) {
        pension = value;
	}
			    
	public void setSalary(double value) {
        salary = value;
	}
			    
	public void addWithdrawal(double aWithdrawal) {
        withdrawal += aWithdrawal;
	}

    public void setTaxableWithdrawal(double aTaxableWithdrawal) {
        taxableWithdrawal = aTaxableWithdrawal;
    }
    public double getTaxableWithdrawal() { return taxableWithdrawal;}

    public void setFederalTaxes(double taxes) {
        federalTaxes = taxes;
	}
	public double getFederalTaxes() { return federalTaxes; }
			    
	public void setStateTaxes(double taxes) {
        stateTaxes = taxes;
	}
	public double getStateTaxes() { return stateTaxes; }
			    
    /**
     * Gets data from the node
     *
     * @return int
     */
	public int getYear() {
		return year;
	}
			    
    /**
     * Gets data from the node
     *
     * @return int
     */
    public int getMonth() {
        return month;
    }
			    
    /**
     * Gets data from the node
     *
     * @return double
     */
	public double getBeginningValue() {
		return beginningValue;
	}
			    
    /**
     * Gets data from the node
     *
     * @return double
     */
	public double getEndingValue() {
		return endingValue;
	}
			    
    /**
     * Gets data from the node
     *
     * @return int
     */
	public int getAge() {
		return age;
	}
			    

    /**
     * Gets data from the node
     *
     * @return double
     */
	public double getDeposit() {
		return deposit;
	}
			    
    /**
     * Gets data from the node
     *
     * @return double
     */
	public double getWithdrawal() {
	    return withdrawal;
	}
			    
    /**
     * Gets data from the node
     *
     * @return double
     */
	public double getPension() {
	    return pension;
	}
			    
    /**
     * Gets data from the node
     *
     * @return double
     */
	public double getSalary() {
	    return salary;
	}
}
