package com.doug.cashflow.model.accounts;

import com.doug.cashflow.model.system.ResultsDataNode;

import java.util.ArrayList;

public class Account {

    private double _balance;
    private double _growthRate;
	private int _currentAge;
    private int _currentYear;
    private int _deathAge;
    @SuppressWarnings("CanBeFinal")
    public ArrayList<ResultsDataNode> _values = new ArrayList<>();

    public void initialize(double balance, double growthRate, int currentAge, int currentYear, int deathAge) {
        _balance = balance;
        _growthRate = growthRate;
        _currentAge = currentAge;
        _currentYear = currentYear;
        _deathAge = deathAge;
        
        clearListOfValues();
        computeGrowth(currentYear, currentAge, deathAge-currentAge);
    }
    
    public void clearListOfValues() {
        _values.clear();
    }
    
    public int getSmallestYear() {
    	if (_values.size() > 0)
            return _values.get(0).getYear();
    	else
    		return 0;
    }
    
    public int getLargestYear() {
        if (_values.size() != 0) {
            return _values.get(_values.size()-1).getYear();
        }
        else {
            return 0;
        }
    }
    
    public int convertAgeToYear(int targetAge) {
        int newYear = 0;
        boolean found = false;
        ResultsDataNode node;
        
        for (int i = 0; i<_values.size() && !found; i++) {
            node = _values.get(i);
            if (node.getAge() == targetAge) {
                found = true;
                newYear = node.getYear();
            }
        }
        
        return newYear;
    }
    
    public int convertYearToAge(int currentYear) {
        int newAge = 0;
        boolean found = false;
        ResultsDataNode node;
        
        for (int i = 0; i<_values.size() && !found; i++) {
            node = _values.get(i);
            if (node.getYear() == currentYear) {
                found = true;
                newAge = node.getAge();
            }
        }
        
        return newAge;
    }

     private void computeGrowth(int currentYear, int currentAge, int numberOfYears) {
        double beginningValue = _balance;
        double value = beginningValue;
        int year = currentYear;
        int age = currentAge;
        
        for (int i=0; i<numberOfYears; i++) {
            value += (value * _growthRate); // value at the end of the year
           
            ResultsDataNode node = new ResultsDataNode();
            node.aSet2(year, age, beginningValue, value);
            _values.add(node);
            
            year++;
            age++;
            beginningValue = value; // Set for the following year
       }
    }
   
    public void recomputeGrowthWithDeposit(int currentAge, double deposit) {
        int age = currentAge;
        double value;
        double beginningValue;
        ResultsDataNode node;
        
        node = getNodeBasedOnAge(currentAge);
        if (node != null) {
            beginningValue = node.getBeginningValue();
        }
        else {
        	return;
        }
        value = beginningValue;
        while (node != null) {
            node.addDeposit(deposit);
            deposit += (deposit * (_growthRate)); // assumes deposit at beginning of year
            value += (value * (_growthRate)) + deposit;
            deposit = 0.0; // set for following nodes/years

            node.setBeginningValue(beginningValue);
            beginningValue = value; // set for following nodes/years 
            node.setEndingValue(value);

            age++;
            node = getNodeBasedOnAge(age);
        }
    }

    public void recomputeGrowthWithWithdrawal(int currentAge, double withdrawal) {
        int age = currentAge;
        double value;
        double beginningValue;
        ResultsDataNode node = getNodeBasedOnAge(currentAge);
        if (node == null) {
        	return; 
        }
        beginningValue = node.getBeginningValue();
        value = beginningValue - node.getWithdrawal();
        while (node != null) {
            node.addWithdrawal(withdrawal); 
            
            value += node.getDeposit();
            value -= withdrawal;
            withdrawal = 0.0; // Reset for following nodes
            if (value > 0.0) { 
            	value += (value * _growthRate);
            	}
            else { 
            	value = 0.0; 
            }
            
            node.setBeginningValue(beginningValue);
            beginningValue = value;
            node.setEndingValue(value);
            
            age++; 
            node = getNodeBasedOnAge(age);
            if (node != null && value <= 0.0) {
                node.setBeginningValue(0.0); 
                beginningValue = 0.0; 
            }
        }
    }
    
    public void zeroBeginningValues(int currentAge) {
        int age = currentAge;

        ResultsDataNode node = getNodeBasedOnAge(age);
        while (node != null) {
            node.setBeginningValue(0.0);
            
            age++;
            node = getNodeBasedOnAge(age);
        }
    }
        
    public void zeroEndingValues(int currentAge) {
        int age = currentAge;
        ResultsDataNode node = getNodeBasedOnAge(age);

        while (node != null) {
            node.setEndingValue(0.0);
            
            age++;
            node = getNodeBasedOnAge(age);
        }
    }
        
    public double getBeginningValue(int year) {
        boolean found = false;
        ResultsDataNode node = null;

        for (int i=0; i<_values.size() && !found; i++) {
            node = _values.get(i);
            if (node.getYear() == year) { 
            	found = true;
            }
        }
            
        if (found) {
        	return node.getBeginningValue(); 
        }
        else { 
        	return 0.0;
        }
    }
       
    public double getDeposits(int year) {
        boolean found = false;
        ResultsDataNode node = null;
        
        for (int i = 0; i<_values.size() && !found; i++) {
            node = _values.get(i);
            if (node.getYear() == year) { 
            	found = true; 
            }
        }
        
        if (found) {
            return node.getDeposit();
        }
        else { 
        	return 0.0; 
        }
    }
       
    public double getWithdrawals(int year) {
        boolean found = false;
        ResultsDataNode node = null;
        
        for (int i = 0; i<_values.size() && !found; i++) {
            node = _values.get(i);
            if (node.getYear() == year) { 
            	found = true;
            }
        }
        if (found) {
        	return node.getWithdrawal();
        }
        else { 
        	return 0.0;
        }
    }
       
    public double getEndingValue(int year) {
        boolean found = false;
        ResultsDataNode node = null;
        
        for (int i = 0; i<_values.size() && !found; i++) {
            node = _values.get(i);
            if (node.getYear() == year) { 
                found = true; 
            }
        }
        
        if (found) {
            return node.getEndingValue(); 
        }
        else { 
            return 0.0; 
        }
    }
     
    void setZeroEndingValue(int age) {
        ResultsDataNode node = getNodeBasedOnAge(age);
        
        if (node != null) { 
            node.setEndingValue(0); 
        }
    }
    
    void setZeroBeginningValue(int age) {
        ResultsDataNode node = getNodeBasedOnAge(age);
        
        if (node != null) { 
            node.setBeginningValue(0); 
        }
    }
       
    ResultsDataNode getNodeBasedOnAge(int age) {
        ResultsDataNode node;
        
        for (int i=0; i<_values.size(); i++) {
            node = _values.get(i);
            if (node.getAge() == age) { 
                return node; 
            }
        }
        
        return null;
    }
    
    ResultsDataNode getNodeBasedOnYear(int year) {
    	ResultsDataNode node;
    	
        for (int i=0; i<_values.size(); i++) {
            node = _values.get(i);
            if (node.getYear() == year) {
                return node;
            }
        }
        
        return null;
    }

    public double withdraw(int currentAge, double amount) {
    	double withdrawal;
        int year = convertAgeToYear(currentAge);
        
        double accountValue = getEndingValue(year);
        
        if (accountValue >= amount) {
            withdrawal = amount;
        }
        else {
            withdrawal = accountValue;
        }
        
        recomputeGrowthWithWithdrawal(currentAge, withdrawal);
        
        return withdrawal;        
    }
    
    public void deposit(int currentAge, double deposit) {
        recomputeGrowthWithDeposit(currentAge, deposit);
    }

    public void addInAnnualContributions(Integer currentAge, Integer retirementAge, Double annualContribution) {
        for (int age = currentAge; age < retirementAge; age++) {
            recomputeGrowthWithDeposit(age, annualContribution);
        }
    }

    private void computePeriodicWithdrawalsRegardlessOfAge(int currentAge, int NumberOfWithdrawals, int withdrawalNumber) {
    	int yearsRemaining;	
        double withdrawal;
    	ResultsDataNode node;
    	
		for (; withdrawalNumber<=NumberOfWithdrawals; withdrawalNumber++) {
			yearsRemaining = NumberOfWithdrawals-withdrawalNumber+1;
			node = getNodeBasedOnAge(currentAge);
			if (node != null) {
				withdrawal = node.getBeginningValue() / (yearsRemaining);
				withdraw(currentAge, withdrawal);
			}
		    currentAge++;
		}
    }
    
    public void computePeriodicWithdrawals(Integer currentAge, Integer startWithdrawalsAge, Integer numberOfWithdrawals) {
        Integer withdrawalNumber;

        if (numberOfWithdrawals == 0)
            return;

        if (currentAge == startWithdrawalsAge) {
            withdrawalNumber = 1;
            computePeriodicWithdrawalsRegardlessOfAge(currentAge, numberOfWithdrawals, withdrawalNumber);
        }
        else
        if (currentAge < startWithdrawalsAge) {
            while (currentAge < startWithdrawalsAge) {
                currentAge++;
            }

            withdrawalNumber = 1;
            computePeriodicWithdrawalsRegardlessOfAge(currentAge, numberOfWithdrawals, withdrawalNumber);
        }
        else
        if (currentAge > startWithdrawalsAge) {
            withdrawalNumber = numberOfWithdrawals;
            while (currentAge > startWithdrawalsAge) {
                startWithdrawalsAge++;
                withdrawalNumber--;
            }

            computePeriodicWithdrawalsRegardlessOfAge(currentAge, numberOfWithdrawals, withdrawalNumber);
        }
    }

    public double getBalance() {
        return _balance;
    }

    public void setBalance(double balance) {
        _balance = balance;
    }

    public void setGrowthRate(double growthRate) {
        _growthRate = growthRate;
    }

    public double getGrowthRate() {
        return _growthRate;
    }

    public int getCurrentAge() {
        return _currentAge;
    }

    public int getCurrentYear() {
        return _currentYear;
    }

    public int getDeathAge() {
        return _deathAge;
    }

	public ArrayList<ResultsDataNode> getListOfValues() {
		return _values;
	}

    public ResultsDataNode get(Integer index) {return _values.get(index); }

    public void add(ResultsDataNode node) {_values.add(node); }

    public int getSize() {
        return _values.size();
    }
    
    ArrayList<ResultsDataNode> getList() {
        return _values;        
    }    
}
