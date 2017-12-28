package com.doug.cashflow.model.system;

import com.doug.cashflow.model.db.RealData;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Doug on 6/2/2017.
 */

public class ResultsDataNodeUnitTest {
    private static final Double EPSILON = 0.000001;

    private ResultsDataNode resultsDataNode;

    @Before
    public void Setup() {
        resultsDataNode = null;
    }

    @Test
    public void testConstructor() {
        resultsDataNode = new ResultsDataNode();

        assertNotNull(resultsDataNode);
    }

    @Test
    public void testASet1Method() {
        Integer currentYear = 2017;
        Integer currentMonth = 9;
        Integer currentAge = 59;
        Double  beginningValue = 1000.0;
        Double  endingValue = 1500.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.aSet1(currentYear, currentMonth, currentAge, beginningValue, endingValue);

        assertThat(currentYear, is(resultsDataNode.getYear()));
        assertThat(currentMonth, is(resultsDataNode.getMonth()));
        assertThat(currentAge, is(resultsDataNode.getAge()));
        assertThat(beginningValue, is(closeTo(resultsDataNode.getBeginningValue(), EPSILON)));
        assertThat(endingValue, is(closeTo(resultsDataNode.getEndingValue(), EPSILON)));
    }

    @Test
    public void testASet2Method() {
        Integer currentYear = 2017;
        Integer currentAge = 59;
        Double  beginningValue = 1000.0;
        Double  endingValue = 1500.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.aSet2(currentYear, currentAge, beginningValue, endingValue);

        assertThat(currentYear, is(resultsDataNode.getYear()));
        assertThat(currentAge, is(resultsDataNode.getAge()));
        assertThat(beginningValue, is(closeTo(resultsDataNode.getBeginningValue(), EPSILON)));
        assertThat(endingValue, is(closeTo(resultsDataNode.getEndingValue(), EPSILON)));
    }

    @Test
    public void testSetBeginningMethod() {
        Double  beginningValue = 1000.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.setBeginningValue(beginningValue);

        assertThat(beginningValue, is(closeTo(resultsDataNode.getBeginningValue(), EPSILON)));
    }

    @Test
    public void testSetEndingMethod() {
        Double  endingValue = 1000.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.setEndingValue(endingValue);

        assertThat(endingValue, is(closeTo(resultsDataNode.getEndingValue(), EPSILON)));
    }

    @Test
    public void testAddDepositMethod() {
        Double  deposit = 1000.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.addDeposit(deposit);
        resultsDataNode.addDeposit(deposit);

        assertThat(deposit*2, is(closeTo(resultsDataNode.getDeposit(), EPSILON)));
    }

    @Test
    public void testSetPensionMethod() {
        Double  pensionAmount = 20000.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.setPension(pensionAmount);

        assertThat(pensionAmount, is(closeTo(resultsDataNode.getPension(), EPSILON)));
    }

    @Test
    public void testSetSalaryMethod() {
        Double  salary = 20000.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.setSalary(salary);

        assertThat(salary, is(closeTo(resultsDataNode.getSalary(), EPSILON)));
    }

    @Test
    public void testAddWithdrawalMethod() {
        Double  withdrawal = 2000.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.addWithdrawal(withdrawal);
        resultsDataNode.addWithdrawal(withdrawal);

        assertThat(withdrawal*2, is(closeTo(resultsDataNode.getWithdrawal(), EPSILON)));
    }

    @Test
    public void testSetTaxableWithdrawalMethod() {
        Double  withdrawal = 2000.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.setTaxableWithdrawal(withdrawal);

        assertThat(withdrawal, is(closeTo(resultsDataNode.getTaxableWithdrawal(), EPSILON)));
    }

    @Test
    public void testSetFederalTaxesMethod() {
        Double  federalTax = 2000.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.setFederalTaxes(federalTax);

        assertThat(federalTax, is(closeTo(resultsDataNode.getFederalTaxes(), EPSILON)));
    }

    @Test
    public void testSetStateTaxesMethod() {
        Double  stateTax = 500.0;

        resultsDataNode = new ResultsDataNode();

        resultsDataNode.setStateTaxes(stateTax);

        assertThat(stateTax, is(closeTo(resultsDataNode.getStateTaxes(), EPSILON)));
    }
}
