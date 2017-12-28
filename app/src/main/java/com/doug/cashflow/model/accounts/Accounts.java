package com.doug.cashflow.model.accounts;

public interface Accounts {
    void deposit(int currentAge, double deposit);
    
    double withdraw(int currentAge, double amount);

    boolean isTaxable();
}
