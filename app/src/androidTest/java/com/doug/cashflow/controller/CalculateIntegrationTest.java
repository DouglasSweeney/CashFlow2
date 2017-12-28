package com.doug.cashflow.controller;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.doug.cashflow.IntegrationTestUtils;
import com.doug.cashflow.R;
import com.doug.cashflow.model.accounts.Taxes;
import com.doug.cashflow.model.system.ResultsDataNode;
import com.doug.cashflow.view.inputs.Deductions;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static android.content.ContentValues.TAG;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class CalculateIntegrationTest {
    private Calculate calculate;

    private Context context;
    private IntegrationTestUtils integrationTestUtils;
    private AppCompatActivity activity;

    @ClassRule
    public static ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(
            LoginActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    public void launchActivity() {
        Intent intent = new Intent();
        activity = activityRule.launchActivity(intent);
    }

    @Before
    public void setUp() {

        context = InstrumentationRegistry.getTargetContext();

        integrationTestUtils = new IntegrationTestUtils(context);

        launchActivity();

        // Enter some data into username/password fields
        onView(withId(R.id.email_username)).perform(replaceText(integrationTestUtils.getUsername()));
        onView(withId(R.id.password)).perform(replaceText(integrationTestUtils.getPassword()));

        onView(withId(R.id.password)).perform(closeSoftKeyboard());

        // Depress the "Login" Button
        onView(withId(R.id.button)).perform(click());

        LoginLab.getInstance(context, false);

        InputLab.getInstance(context, activity.getSupportFragmentManager(), null);

        InputLab.getPersonal().setSimulationDate("12/25/2017");
        InputLab.getPersonal().setBirthDate("12/25/1958");
        InputLab.getPersonal().setLifeExpectancyAge("95");

        calculate = new Calculate();
    }

    private double computeTax(double rate, double amount) {
        return amount * rate;
    }

    private double computeTotalTax(double federalTaxRate, double stateTaxRate, double amount) {
        double totalTax;
        Deductions deductions = InputLab.getDeductions();

        if (amount > deductions.getDeductions()) {
            amount -= deductions.getDeductions();
            totalTax = computeTax(federalTaxRate, amount) +
                       computeTax(stateTaxRate, amount);
        } else {
            totalTax = 0.0;
        }

        return totalTax;
    }

    @Test
    public void taxesPension() throws InterruptedException {
        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPersonal().setInflation("0.0");

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getAccount401k().setBalance("1000.0");
                InputLab.getAccount401k().setNumberOfWithdrawals("0");
                InputLab.getAccount401k().setStartWithdrawalsAge("59");

                InputLab.getAccount403b().setBalance("1000.0");
                InputLab.getAccount403b().setNumberOfWithdrawals("0");
                InputLab.getAccount403b().setStartWithdrawalsAge("59");

                InputLab.getCashBalance().setBalance("1000.0");
                InputLab.getCashBalance().setNumberOfWithdrawals("0");
                InputLab.getCashBalance().setStartWithdrawalsAge("59");

                InputLab.getTraditionalIra().setBalance("1000.0");
                InputLab.getTraditionalIra().setNumberOfWithdrawals("0");
                InputLab.getTraditionalIra().setStartWithdrawalsAge("59");

                InputLab.getSavings().setBalance("1000.0");
                InputLab.getSavings().setGrowthRate("6");

                InputLab.getSalary().setSalary("0.0");
                InputLab.getSalary().setMeritIncrease("0");
            }
        });

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPension().setMonthlyAmount("1000.0");
        InputLab.getPension().setStartingAge("59");

        InputLab.getSocialSecurity().setMonthlyAmount("0.0");
        InputLab.getSocialSecurity().setStartingAge("59");

        ModelLab.getInstance(context);

        for (int i=0; i<ModelLab.getIraTraditional().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getIraTraditional().getListOfValues().get(i);
            Log.d(TAG, "Year: " + node.year);
            Log.d(TAG, "Withdrawals: " + node.getWithdrawal());
        }

        calculate.run();

        Integer year = 2018;
        double taxableAmount = ModelLab.getSalary().getEndingValue(year) +
                ModelLab.getPension().getEndingValue(year) +
                ModelLab.getSocialSecurity().getEndingValue(year) +
                ModelLab.getAccount401k().getWithdrawals(year) +
                ModelLab.getAccount403b().getWithdrawals(year) +
                ModelLab.getCashBalance().getWithdrawals(year) +
                ModelLab.getIraTraditional().getWithdrawals(year);

        double computeTaxes = computeTotalTax(
                InputLab.getTaxes().getFederalTaxRate()/100,
                InputLab.getTaxes().getStateTaxRate()/100,
                taxableAmount);

        Taxes taxes = ModelLab.getTaxes();
        assertThat(computeTaxes, closeTo(taxes.getTotalTax(year), 0.001));
    }

    @Test
    public void taxesSocialSecurity() throws InterruptedException {
        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPersonal().setInflation("0.0");

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getAccount401k().setBalance("1000.0");
                InputLab.getAccount401k().setNumberOfWithdrawals("0");
                InputLab.getAccount401k().setStartWithdrawalsAge("59");

                InputLab.getAccount403b().setBalance("1000.0");
                InputLab.getAccount403b().setNumberOfWithdrawals("0");
                InputLab.getAccount403b().setStartWithdrawalsAge("59");

                InputLab.getCashBalance().setBalance("1000.0");
                InputLab.getCashBalance().setNumberOfWithdrawals("0");
                InputLab.getCashBalance().setStartWithdrawalsAge("59");

                InputLab.getTraditionalIra().setBalance("1000.0");
                InputLab.getTraditionalIra().setNumberOfWithdrawals("0");
                InputLab.getTraditionalIra().setStartWithdrawalsAge("59");

                InputLab.getSavings().setBalance("1000.0");
                InputLab.getSavings().setGrowthRate("6");

                InputLab.getSalary().setSalary("0.0");
                InputLab.getSalary().setMeritIncrease("0");
            }
        });

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPension().setMonthlyAmount("0.0");
        InputLab.getPension().setStartingAge("59");

        InputLab.getSocialSecurity().setMonthlyAmount("1000.0");
        InputLab.getSocialSecurity().setStartingAge("59");

        ModelLab.getInstance(context);

        for (int i=0; i<ModelLab.getIraTraditional().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getIraTraditional().getListOfValues().get(i);
            Log.d(TAG, "Year: " + node.year);
            Log.d(TAG, "Withdrawals: " + node.getWithdrawal());
        }

        calculate.run();

        Integer year = 2018;
        double taxableAmount = ModelLab.getSalary().getEndingValue(year) +
                ModelLab.getPension().getEndingValue(year) +
                ModelLab.getSocialSecurity().getEndingValue(year) +
                ModelLab.getAccount401k().getWithdrawals(year) +
                ModelLab.getAccount403b().getWithdrawals(year) +
                ModelLab.getCashBalance().getWithdrawals(year) +
                ModelLab.getIraTraditional().getWithdrawals(year);

        double computeTaxes = computeTotalTax(
                InputLab.getTaxes().getFederalTaxRate()/100,
                InputLab.getTaxes().getStateTaxRate()/100,
                taxableAmount);

        Taxes taxes = ModelLab.getTaxes();
        assertThat(computeTaxes, closeTo(taxes.getTotalTax(year), 0.001));
    }

    @Test
    public void taxesAccount401k() throws InterruptedException {
        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPersonal().setInflation("0.0");

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getAccount401k().setBalance("1000.0");
                InputLab.getAccount401k().setNumberOfWithdrawals("1");
                InputLab.getAccount401k().setStartWithdrawalsAge("59");

                InputLab.getAccount403b().setBalance("1000.0");
                InputLab.getAccount403b().setNumberOfWithdrawals("0");
                InputLab.getAccount403b().setStartWithdrawalsAge("59");

                InputLab.getCashBalance().setBalance("1000.0");
                InputLab.getCashBalance().setNumberOfWithdrawals("0");
                InputLab.getCashBalance().setStartWithdrawalsAge("59");

                InputLab.getTraditionalIra().setBalance("1000.0");
                InputLab.getTraditionalIra().setNumberOfWithdrawals("0");
                InputLab.getTraditionalIra().setStartWithdrawalsAge("59");

                InputLab.getSavings().setBalance("1000.0");
                InputLab.getSavings().setGrowthRate("6");

                InputLab.getSalary().setSalary("0.0");
                InputLab.getSalary().setMeritIncrease("0");
            }
        });

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPension().setMonthlyAmount("0.0");
        InputLab.getPension().setStartingAge("59");

        InputLab.getSocialSecurity().setMonthlyAmount("0.0");
        InputLab.getSocialSecurity().setStartingAge("59");

        ModelLab.getInstance(context);

        for (int i=0; i<ModelLab.getIraTraditional().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getIraTraditional().getListOfValues().get(i);
            Log.d(TAG, "Year: " + node.year);
            Log.d(TAG, "Withdrawals: " + node.getWithdrawal());
        }

        calculate.run();

        Integer year = 2018;
        double taxableAmount = ModelLab.getSalary().getEndingValue(year) +
                ModelLab.getPension().getEndingValue(year) +
                ModelLab.getSocialSecurity().getEndingValue(year) +
                ModelLab.getAccount401k().getWithdrawals(year) +
                ModelLab.getAccount403b().getWithdrawals(year) +
                ModelLab.getCashBalance().getWithdrawals(year) +
                ModelLab.getIraTraditional().getWithdrawals(year);

        double computeTaxes = computeTotalTax(
                InputLab.getTaxes().getFederalTaxRate()/100,
                InputLab.getTaxes().getStateTaxRate()/100,
                taxableAmount);

        Taxes taxes = ModelLab.getTaxes();
        assertThat(computeTaxes, closeTo(taxes.getTotalTax(year), 0.001));
    }

    @Test
    public void taxesAccount403b() throws InterruptedException {
        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPersonal().setInflation("0.0");

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getAccount401k().setBalance("1000.0");
                InputLab.getAccount401k().setNumberOfWithdrawals("0");
                InputLab.getAccount401k().setStartWithdrawalsAge("59");

                InputLab.getAccount403b().setBalance("1000.0");
                InputLab.getAccount403b().setNumberOfWithdrawals("2");
                InputLab.getAccount403b().setStartWithdrawalsAge("59");

                InputLab.getCashBalance().setBalance("1000.0");
                InputLab.getCashBalance().setNumberOfWithdrawals("0");
                InputLab.getCashBalance().setStartWithdrawalsAge("59");

                InputLab.getTraditionalIra().setBalance("1000.0");
                InputLab.getTraditionalIra().setNumberOfWithdrawals("0");
                InputLab.getTraditionalIra().setStartWithdrawalsAge("59");

                InputLab.getSavings().setBalance("1000.0");
                InputLab.getSavings().setGrowthRate("6");

                InputLab.getSalary().setSalary("0.0");
                InputLab.getSalary().setMeritIncrease("0");
            }
        });

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPension().setMonthlyAmount("0.0");
        InputLab.getPension().setStartingAge("59");

        InputLab.getSocialSecurity().setMonthlyAmount("0.0");
        InputLab.getSocialSecurity().setStartingAge("59");

        ModelLab.getInstance(context);

        for (int i=0; i<ModelLab.getIraTraditional().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getIraTraditional().getListOfValues().get(i);
            Log.d(TAG, "Year: " + node.year);
            Log.d(TAG, "Withdrawals: " + node.getWithdrawal());
        }

        calculate.run();

        Integer year = 2018;
        double taxableAmount = ModelLab.getSalary().getEndingValue(year) +
                ModelLab.getPension().getEndingValue(year) +
                ModelLab.getSocialSecurity().getEndingValue(year) +
                ModelLab.getAccount401k().getWithdrawals(year) +
                ModelLab.getAccount403b().getWithdrawals(year) +
                ModelLab.getCashBalance().getWithdrawals(year) +
                ModelLab.getIraTraditional().getWithdrawals(year);

        double computeTaxes = computeTotalTax(
                InputLab.getTaxes().getFederalTaxRate()/100,
                InputLab.getTaxes().getStateTaxRate()/100,
                taxableAmount);

        Taxes taxes = ModelLab.getTaxes();
        assertThat(computeTaxes, closeTo(taxes.getTotalTax(year), 0.001));
    }

    @Test
    public void taxesAccountCashBalance() throws InterruptedException {
        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPersonal().setInflation("0.0");

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getAccount401k().setBalance("1000.0");
                InputLab.getAccount401k().setNumberOfWithdrawals("0");
                InputLab.getAccount401k().setStartWithdrawalsAge("59");

                InputLab.getAccount403b().setBalance("1000.0");
                InputLab.getAccount403b().setNumberOfWithdrawals("0");
                InputLab.getAccount403b().setStartWithdrawalsAge("59");

                InputLab.getCashBalance().setBalance("1000.0");
                InputLab.getCashBalance().setNumberOfWithdrawals("3");
                InputLab.getCashBalance().setStartWithdrawalsAge("59");

                InputLab.getTraditionalIra().setBalance("1000.0");
                InputLab.getTraditionalIra().setNumberOfWithdrawals("0");
                InputLab.getTraditionalIra().setStartWithdrawalsAge("59");

                InputLab.getSavings().setBalance("1000.0");
                InputLab.getSavings().setGrowthRate("6");

                InputLab.getSalary().setSalary("0.0");
                InputLab.getSalary().setMeritIncrease("0");
            }
        });

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPension().setMonthlyAmount("0.0");
        InputLab.getPension().setStartingAge("59");

        InputLab.getSocialSecurity().setMonthlyAmount("0.0");
        InputLab.getSocialSecurity().setStartingAge("59");

        ModelLab.getInstance(context);

        for (int i=0; i<ModelLab.getIraTraditional().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getIraTraditional().getListOfValues().get(i);
            Log.d(TAG, "Year: " + node.year);
            Log.d(TAG, "Withdrawals: " + node.getWithdrawal());
        }

        calculate.run();

        Integer year = 2018;
        double taxableAmount = ModelLab.getSalary().getEndingValue(year) +
                ModelLab.getPension().getEndingValue(year) +
                ModelLab.getSocialSecurity().getEndingValue(year) +
                ModelLab.getAccount401k().getWithdrawals(year) +
                ModelLab.getAccount403b().getWithdrawals(year) +
                ModelLab.getCashBalance().getWithdrawals(year) +
                ModelLab.getIraTraditional().getWithdrawals(year);

        double computeTaxes = computeTotalTax(
                InputLab.getTaxes().getFederalTaxRate()/100,
                InputLab.getTaxes().getStateTaxRate()/100,
                taxableAmount);

        Taxes taxes = ModelLab.getTaxes();
        assertThat(computeTaxes, closeTo(taxes.getTotalTax(year), 0.001));
    }

    @Test
    public void taxesAccountTraditionalIra() throws InterruptedException {
        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPersonal().setInflation("0.0");

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getAccount401k().setBalance("1000.0");
                InputLab.getAccount401k().setNumberOfWithdrawals("0");
                InputLab.getAccount401k().setStartWithdrawalsAge("59");

                InputLab.getAccount403b().setBalance("1000.0");
                InputLab.getAccount403b().setNumberOfWithdrawals("0");
                InputLab.getAccount403b().setStartWithdrawalsAge("59");

                InputLab.getCashBalance().setBalance("1000.0");
                InputLab.getCashBalance().setNumberOfWithdrawals("0");
                InputLab.getCashBalance().setStartWithdrawalsAge("59");

                InputLab.getTraditionalIra().setBalance("1000.0");
                InputLab.getTraditionalIra().setNumberOfWithdrawals("4");
                InputLab.getTraditionalIra().setStartWithdrawalsAge("59");

                InputLab.getSavings().setBalance("1000.0");
                InputLab.getSavings().setGrowthRate("6");

                InputLab.getSalary().setSalary("0.0");
                InputLab.getSalary().setMeritIncrease("0");
            }
        });

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPension().setMonthlyAmount("0.0");
        InputLab.getPension().setStartingAge("59");

        InputLab.getSocialSecurity().setMonthlyAmount("0.0");
        InputLab.getSocialSecurity().setStartingAge("59");

        ModelLab.getInstance(context);

        for (int i=0; i<ModelLab.getIraTraditional().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getIraTraditional().getListOfValues().get(i);
            Log.d(TAG, "Year: " + node.year);
            Log.d(TAG, "Withdrawals: " + node.getWithdrawal());
        }

        calculate.run();

        Integer year = 2018;
        double taxableAmount = ModelLab.getSalary().getEndingValue(year) +
                ModelLab.getPension().getEndingValue(year) +
                ModelLab.getSocialSecurity().getEndingValue(year) +
                ModelLab.getAccount401k().getWithdrawals(year) +
                ModelLab.getAccount403b().getWithdrawals(year) +
                ModelLab.getCashBalance().getWithdrawals(year) +
                ModelLab.getIraTraditional().getWithdrawals(year);

        double computeTaxes = computeTotalTax(
                InputLab.getTaxes().getFederalTaxRate()/100,
                InputLab.getTaxes().getStateTaxRate()/100,
                taxableAmount);

        Taxes taxes = ModelLab.getTaxes();
        assertThat(computeTaxes, closeTo(taxes.getTotalTax(year), 0.001));
    }

    @Test
    public void taxesSalary() throws InterruptedException {
        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPersonal().setInflation("0.0");

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getAccount401k().setBalance("1000.0");
                InputLab.getAccount401k().setNumberOfWithdrawals("0");
                InputLab.getAccount401k().setStartWithdrawalsAge("59");

                InputLab.getAccount403b().setBalance("1000.0");
                InputLab.getAccount403b().setNumberOfWithdrawals("0");
                InputLab.getAccount403b().setStartWithdrawalsAge("59");

                InputLab.getCashBalance().setBalance("1000.0");
                InputLab.getCashBalance().setNumberOfWithdrawals("0");
                InputLab.getCashBalance().setStartWithdrawalsAge("59");

                InputLab.getTraditionalIra().setBalance("1000.0");
                InputLab.getTraditionalIra().setNumberOfWithdrawals("0");
                InputLab.getTraditionalIra().setStartWithdrawalsAge("59");

                InputLab.getSavings().setBalance("1000.0");
                InputLab.getSavings().setGrowthRate("6");

                InputLab.getSalary().setSalary("1000.0");
                InputLab.getSalary().setMeritIncrease("0");
            }
        });

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPension().setMonthlyAmount("0.0");
        InputLab.getPension().setStartingAge("59");

        InputLab.getSocialSecurity().setMonthlyAmount("0.0");
        InputLab.getSocialSecurity().setStartingAge("59");

        ModelLab.getInstance(context);

        for (int i=0; i<ModelLab.getIraTraditional().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getIraTraditional().getListOfValues().get(i);
            Log.d(TAG, "Year: " + node.year);
            Log.d(TAG, "Withdrawals: " + node.getWithdrawal());
        }

        calculate.run();

        Integer year = 2018;
        double taxableAmount = ModelLab.getSalary().getEndingValue(year) +
                ModelLab.getPension().getEndingValue(year) +
                ModelLab.getSocialSecurity().getEndingValue(year) +
                ModelLab.getAccount401k().getWithdrawals(year) +
                ModelLab.getAccount403b().getWithdrawals(year) +
                ModelLab.getCashBalance().getWithdrawals(year) +
                ModelLab.getIraTraditional().getWithdrawals(year);

        double computeTaxes = computeTotalTax(
                InputLab.getTaxes().getFederalTaxRate()/100,
                InputLab.getTaxes().getStateTaxRate()/100,
                taxableAmount);

        Taxes taxes = ModelLab.getTaxes();
        assertThat(computeTaxes, closeTo(taxes.getTotalTax(year), 0.001));
    }

    @Test
    public void taxesAll() throws InterruptedException {
        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPersonal().setInflation("0.0");

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getAccount401k().setBalance("1000.0");
                InputLab.getAccount401k().setNumberOfWithdrawals("1");
                InputLab.getAccount401k().setStartWithdrawalsAge("59");

                InputLab.getAccount403b().setBalance("1000.0");
                InputLab.getAccount403b().setNumberOfWithdrawals("1");
                InputLab.getAccount403b().setStartWithdrawalsAge("59");

                InputLab.getCashBalance().setBalance("1000.0");
                InputLab.getCashBalance().setNumberOfWithdrawals("2");
                InputLab.getCashBalance().setStartWithdrawalsAge("59");

                InputLab.getTraditionalIra().setBalance("1000.0");
                InputLab.getTraditionalIra().setNumberOfWithdrawals("1");
                InputLab.getTraditionalIra().setStartWithdrawalsAge("59");

                InputLab.getSavings().setBalance("1000.0");
                InputLab.getSavings().setGrowthRate("6");

                InputLab.getSalary().setSalary("1000.0");
                InputLab.getSalary().setMeritIncrease("0");
            }
        });

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPension().setMonthlyAmount("1000.0");
        InputLab.getPension().setStartingAge("59");

        InputLab.getSocialSecurity().setMonthlyAmount("1000.0");
        InputLab.getSocialSecurity().setStartingAge("59");

        ModelLab.getInstance(context);

        calculate.run();

        Integer year = 2018;
        double taxableAmount = ModelLab.getSalary().getEndingValue(year) +
                        ModelLab.getPension().getEndingValue(year) +
                        ModelLab.getSocialSecurity().getEndingValue(year) +
                        ModelLab.getAccount401k().getWithdrawals(year) +
                        ModelLab.getAccount403b().getWithdrawals(year) +
                        ModelLab.getCashBalance().getWithdrawals(year) +
                        ModelLab.getIraTraditional().getWithdrawals(year);

        double computeTaxes = computeTotalTax(
                InputLab.getTaxes().getFederalTaxRate()/100,
                InputLab.getTaxes().getStateTaxRate()/100,
                 taxableAmount);

        Taxes taxes = ModelLab.getTaxes();
        assertThat(computeTaxes, closeTo(taxes.getTotalTax(year), 0.001));
    }

    @Test
    public void withdrawSavingsWithJustSalary() throws InterruptedException {

        LoginLab.getInstance(context, false);
        LoginLab.setCurrentActivity(activity);

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPersonal().setInflation("0.0");
        InputLab.getPersonal().setRetirementAge("65");

        onView(withId(R.id.container_401k)).perform(closeSoftKeyboard());
        onView(withId(R.id.account_401k_balance)).perform(replaceText("0.0"));
        onView(withId(R.id.account_401k_number_of_withdrawals)).perform(replaceText("0"));
        onView(withId(R.id.account_401k_start_withdrawals_age)).perform(replaceText("65"));

        integrationTestUtils.jumpToScreen(R.id.savings_menu_item);
        onView(withId(R.id.container_savings)).perform(closeSoftKeyboard());
        onView(withId(R.id.savings_balance)).perform(replaceText("1000.0"));
        onView(withId(R.id.savings_growth_rate)).perform(replaceText("0.0"));

        integrationTestUtils.jumpToScreen(R.id.salary_menu_item);
        onView(withId(R.id.container_salary)).perform(closeSoftKeyboard());
        onView(withId(R.id.salary_salary)).perform(replaceText("1000.0"));
        onView(withId(R.id.salary_merit_increase)).perform(replaceText("0"));

        integrationTestUtils.jumpToScreen(R.id.deductions_menu_item);
        onView(withId(R.id.container_deductions)).perform(closeSoftKeyboard());
        onView(withId(R.id.deductions_deductions)).perform(replaceText("0.0"));

        integrationTestUtils.jumpToScreen(R.id.expenses_menu_item);
        onView(withId(R.id.container_expenses)).perform(closeSoftKeyboard());
        onView(withId(R.id.expenses_expense)).perform(replaceText("1200.0"));

        InputLab.getTaxes().setFederalTaxRate("28.0");
        InputLab.getTaxes().setStateTaxRate("6.0");

        InputLab.getPension().setMonthlyAmount("0.0");
        InputLab.getPension().setStartingAge("65");

        InputLab.getSocialSecurity().setMonthlyAmount("0.0");
        InputLab.getSocialSecurity().setStartingAge("65");

        ModelLab.getInstance(context);

        // Compute withdrawals from savings
        calculate.run();

        for (int i=0; i<ModelLab.getSavings().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getSavings().getListOfValues().get(i);
            Log.d(TAG, "******************************Year: " + node.year);
            Log.d(TAG, "Savings BeginningValue: " +
                    ModelLab.getSavings().getBeginningValue(2017 + i));
        }

        for (int i=0; i<ModelLab.getSavings().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getSavings().getListOfValues().get(i);
            Log.d(TAG, "******************************Year: " + node.year);
            Log.d(TAG, "Withdrawal: " + node.getWithdrawal());
        }

        for (int i=0; i<ModelLab.getSavings().getListOfValues().size(); i++) {
            ResultsDataNode node;
            node = ModelLab.getSavings().getListOfValues().get(i);
            Log.d(TAG, "******************************Year: " + node.year);
            Log.d(TAG, "EndingValue: " + ModelLab.getSavings().getEndingValue(2017 + i));
        }

        assertThat(ModelLab.getSavings().getEndingValue(2017),
                is(closeTo(ModelLab.getSavings().getBeginningValue(2017) -
                            ModelLab.getTaxes().getTotalTax(2017) -
                            (
                                    ModelLab.getExpenses().getBeginningValue(2017)-
                                    ModelLab.getSalary().getEndingValue(2017)
                            ),
                            0.001)));
    }
}
