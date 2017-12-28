package com.doug.cashflow.model.accounts;

import com.doug.cashflow.model.system.ResultsDataNode;

public class Taxes extends Account {

    private double federalTaxRate;
    private double stateTaxRate;

    private double totalTax;

    public Taxes(int currentAge, int currentYear, int deathAge,
                 double federalTaxRate, double stateTaxRate) {

        this.federalTaxRate = federalTaxRate;
        if (federalTaxRate >= 1) {
            this.federalTaxRate = federalTaxRate/100;
        }

        this.stateTaxRate = stateTaxRate;
        if (stateTaxRate >= 1) {
            this.stateTaxRate = stateTaxRate/100;
        }
        
        initialize(0.0, 0.0, currentAge, currentYear, deathAge);        
    }

    public double compute(int age, double taxableAmount) {
        double federalTaxes;
        double stateTaxes;
        double totalTaxes;
                                      
        federalTaxes = taxableAmount * federalTaxRate;
        if (federalTaxes < 0.00) {
            federalTaxes = 0.00;
        }

        stateTaxes = taxableAmount * stateTaxRate;
        if (stateTaxes < 0.00) {
            stateTaxes = 0.00;
        }

        totalTaxes = federalTaxes + stateTaxes;
        
        set(age, taxableAmount, federalTaxes, stateTaxes, totalTaxes);
        
        return totalTaxes;
    }
    
    private void set(int age, double taxableAmount, double federalTaxes, double stateTaxes,
    		         double totalTaxes) {
    	ResultsDataNode node;
        
        node = getNodeBasedOnAge(age);
        if (node != null) {
            node.setTaxableWithdrawal(taxableAmount);
            node.setFederalTaxes(federalTaxes);
            node.setStateTaxes(stateTaxes);
            node.setEndingValue(totalTaxes);
        }
    }

    public double getFederalTaxRate() { return federalTaxRate; }

    public double getStateTaxRate() { return stateTaxRate; }

    public double getTotalTax(Integer year) {
        ResultsDataNode node;

        node = getNodeBasedOnYear(year);
        if (node != null) {
            return node.getEndingValue();
        }

        return 0.0;
    }
}
