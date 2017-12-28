package com.doug.cashflow.model.accounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class TaxesUnitTest {
    private Taxes account;

    @Before
    public void setUp() throws Exception {

        account = null;
    }

    @After
    public void tearDown() throws Exception {
        account = null;
    }

    @Test
    public void checkConstructor() throws Exception {
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Double federalTaxRate = 0.28;
        Double stateTaxRate = 0.05;
        Double listValue;

        account = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);

        for (int i = 0; i < account.getSize(); i++) {
            listValue = account.getList().get(i).getBeginningValue();
            assertThat(0.0, closeTo(listValue, 0.0001));

            listValue = account.getList().get(i).getEndingValue();
            assertThat(0.0, closeTo(listValue, 0.0001));
        }
    }

    @Test
    public void checkCompute() throws Exception {
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Double federalTaxRate = 0.28;
        Double stateTaxRate = 0.05;

        account = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);

        double totalTaxes = account.compute(currentAge, 100.0);
        assertThat(100.0*account.getFederalTaxRate() + 100.0*account.getStateTaxRate(), closeTo(totalTaxes, 0.0001));
    }

    @Test
    public void checkListFirstNode() throws Exception {
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Double federalTaxRate = 0.28;
        Double stateTaxRate = 0.05;

        account = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);
        account.compute(currentAge, 100.0);

        // check withdrawal
        double withdrawal = account.getList().get(0).getTaxableWithdrawal();
        assertThat(100.0, closeTo(withdrawal, 0.0001));

        // check federal tax rate
        double taxes = account.getList().get(0).getFederalTaxes();
        assertThat(100.0*federalTaxRate, closeTo(taxes, 0.0001));

        // check state tax rate
        taxes = account.getList().get(0).getStateTaxes();
        assertThat(100.0*stateTaxRate, closeTo(taxes, 0.0001));
    }

    @Test
    public void checkListLastNode() throws Exception {
        Integer currentAge = 62;
        Integer currentYear = 2017;
        Integer deathAge = 95;
        Double federalTaxRate = 0.28;
        Double stateTaxRate = 0.05;

        account = new Taxes(currentAge, currentYear, deathAge, federalTaxRate, stateTaxRate);

        // compute taxes on last node of list
        account.compute(deathAge-1, 100.0);
        Integer index = account.getSize()-1;

        // check withdrawal
        Double withdrawal = account.getList().get(index).getTaxableWithdrawal();
        assertThat(100.0, closeTo(withdrawal, 0.0001));

        // check federal tax rate
        Double taxes = account.getList().get(index).getFederalTaxes();
        assertThat(100.0*federalTaxRate, closeTo(taxes, 0.0001));

        // check state tax rate
        taxes = account.getList().get(index).getStateTaxes();
        assertThat(100.0*stateTaxRate, closeTo(taxes, 0.0001));
    }
}
