package com.doug.cashflow.model.system;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.doug.cashflow.IntegrationTestUtils;
import com.doug.cashflow.R;
import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.LoginActivity;
import com.doug.cashflow.controller.LoginLab;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;

public class ValidateInputsIntegrationTest {
    private static final Integer TOAST_START_MILLISECONDS = 300;     // Wait for toast to start
    private static final Integer WAIT_FOR_TOAST_TO_FINISH = 1500;    // Wait for last toast to be complete
    private static final Integer WAIT_TOASTS_FOR_COMPLETION = 5000;  // Wait for possibly toast to complete

    // SUT
    private ValidateInputs validateInputs;

    private Context context = InstrumentationRegistry.getTargetContext();
    private IntegrationTestUtils integrationTestUtils;

    @ClassRule
    public static ActivityTestRule<LoginActivity> activityRule
            = new ActivityTestRule<>(
            LoginActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent

    private void setDataAndTest(String name, String field, String data, boolean expectedAnswer) {
        if (name.equals(context.getString(R.string.input_header_401k))) {
            if (field.equals(context.getString(R.string.field_balance))) {
                InputLab.getAccount401k().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.account401kBalance());
            }
            else
            if (field.equals(context.getString(R.string.field_growth_rate))) {
                InputLab.getAccount401k().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.account401kGrowthRate());
            }
            else
            if (field.equals(context.getString(R.string.field_contributions))) {
                InputLab.getAccount401k().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.account401kAnnualContribution());
            }
            else
            if (field.equals(context.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getAccount401k().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.account401kStartWithdrawalsAge());
            }
            else
            if (field.equals(context.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getAccount401k().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.account401kNumberOfWithdrawals());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_403b))) {
            if (field.equals(context.getString(R.string.field_balance))) {
                InputLab.getAccount403b().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.account403bBalance());
            }
            else
            if (field.equals(context.getString(R.string.field_growth_rate))) {
                InputLab.getAccount403b().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.account403bGrowthRate());
            }
            else
            if (field.equals(context.getString(R.string.field_contributions))) {
                InputLab.getAccount403b().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.account403bAnnualContribution());
            }
            else
            if (field.equals(context.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getAccount403b().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.account403bStartWithdrawalsAge());
            }
            else
            if (field.equals(context.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getAccount403b().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.account403bNumberOfWithdrawals());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_brokerage))) {
            if (field.equals(context.getString(R.string.field_balance))) {
                InputLab.getBrokerage().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.brokerageBalance());
            }
            else
            if (field.equals(context.getString(R.string.field_growth_rate))) {
                InputLab.getBrokerage().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.brokerageGrowthRate());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_cash_balance))) {
            if (field.equals(context.getString(R.string.field_balance))) {
                InputLab.getCashBalance().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceBalance());
            }
            else
            if (field.equals(context.getString(R.string.field_growth_rate))) {
                InputLab.getCashBalance().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceGrowthRate());
            }
            else
            if (field.equals(context.getString(R.string.field_contributions))) {
                InputLab.getCashBalance().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceAnnualContribution());
            }
            else
            if (field.equals(context.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getCashBalance().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceStartWithdrawalsAge());
            }
            else
            if (field.equals(context.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getCashBalance().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceNumberOfWithdrawals());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_deductions))) {
            if (field.equals(context.getString(R.string.field_yearly_deductions))) {
                InputLab.getDeductions().setDeductions(data);

                assertEquals(expectedAnswer, validateInputs.deductionsDeductions());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_expenses))) {
            if (field.equals(context.getString(R.string.field_annual_expenses))) {
                InputLab.getExpenses().setExpenses(data);

                assertEquals(expectedAnswer, validateInputs.expenseExpenses());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_ira_roth))) {
            if (field.equals(context.getString(R.string.field_balance))) {
                InputLab.getRothIra().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.accountRothBalance());
            }
            else
            if (field.equals(context.getString(R.string.field_growth_rate))) {
                InputLab.getRothIra().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.accountRothGrowthRate());
            }
            else
            if (field.equals(context.getString(R.string.field_contributions))) {
                InputLab.getRothIra().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.accountRothAnnualContribution());
            }
            else
            if (field.equals(context.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getRothIra().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.accountRothStartWithdrawalsAge());
            }
            else
            if (field.equals(context.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getRothIra().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.accountRothNumberOfWithdrawals());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_ira_traditional))) {
            if (field.equals(context.getString(R.string.field_balance))) {
                InputLab.getTraditionalIra().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.accountIraBalance());
            }
            else
            if (field.equals(context.getString(R.string.field_growth_rate))) {
                InputLab.getTraditionalIra().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.accountIraGrowthRate());
            }
            else
            if (field.equals(context.getString(R.string.field_contributions))) {
                InputLab.getTraditionalIra().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.accountIraAnnualContribution());
            }
            else
            if (field.equals(context.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getTraditionalIra().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.accountIraStartWithdrawalsAge());
            }
            else
            if (field.equals(context.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getTraditionalIra().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.accountIraNumberOfWithdrawals());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_pension))) {
            if (field.equals(context.getString(R.string.field_starting_age))) {
                InputLab.getPension().setStartingAge(data);

                assertEquals(expectedAnswer, validateInputs.pensionStartingAge());
            }
            else
            if (field.equals(context.getString(R.string.field_monthly_amount))) {
                InputLab.getPension().setMonthlyAmount(data);

                assertEquals(expectedAnswer, validateInputs.pensionMonthlyAmount());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_personal))) {
            if (field.equals(context.getString(R.string.field_retirement_age))) {
                InputLab.getPersonal().setRetirementAge(data);

                assertEquals(expectedAnswer, validateInputs.personalRetirementAge());
            }
            else
            if (field.equals(context.getString(R.string.field_life_expectancy_age))) {
                InputLab.getPersonal().setLifeExpectancyAge(data);

                assertEquals(expectedAnswer, validateInputs.personalDeathAge());
            }
            else
            if (field.equals(context.getString(R.string.field_inflation))) {
                InputLab.getPersonal().setInflation(data);

                assertEquals(expectedAnswer, validateInputs.personalInflationRate());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_salary))) {
            if (field.equals(context.getString(R.string.field_salary))) {
                InputLab.getSalary().setSalary(data);

                assertEquals(expectedAnswer, validateInputs.salarySalary());
            }
            else
            if (field.equals(context.getString(R.string.field_merit_increase))) {
                InputLab.getSalary().setMeritIncrease(data);

                assertEquals(expectedAnswer, validateInputs.salaryMeritIncrease());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_savings))) {
            if (field.equals(context.getString(R.string.field_balance))) {
                InputLab.getSavings().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.savingsBalance());
            }
            else
            if (field.equals(context.getString(R.string.field_growth_rate))) {
                InputLab.getSavings().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.savingsGrowthRate());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_social_security))) {
            if (field.equals(context.getString(R.string.field_starting_age))) {
                InputLab.getSocialSecurity().setStartingAge(data);

                assertEquals(expectedAnswer, validateInputs.socialSecurityStartingAge());
            }
            else
            if (field.equals(context.getString(R.string.field_monthly_amount))) {
                InputLab.getSocialSecurity().setMonthlyAmount(data);

                assertEquals(expectedAnswer, validateInputs.socialSecurityMonthlyAmount());
            }
        }
        else
        if (name.equals(context.getString(R.string.input_header_taxes))) {
            if (field.equals(context.getString(R.string.field_federal_tax_rate))) {
                InputLab.getTaxes().setFederalTaxRate(data);

                assertEquals(expectedAnswer, validateInputs.taxesFederal());
            }
            else
            if (field.equals(context.getString(R.string.field_state_tax_rate))) {
                InputLab.getTaxes().setStateTaxRate(data);

                assertEquals(expectedAnswer, validateInputs.taxesState());
            }
        }
    }

    private void validDoubleData(final String name, final Integer rStringField, final Double validData) {
        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDataAndTest(name, context.getResources().getString(rStringField), validData.toString(), true);
            }
        });
    }

    private void invalidDoubleData(
            final String name, final Integer rStringField, final Integer rIdContainer, Integer rIdJump,
            final Double validData, final Double invalidData, final String toastStartString
    ) throws InterruptedException {
        integrationTestUtils.jumpToScreen(rIdJump);

        onView(withId(rIdContainer)).perform(closeSoftKeyboard());

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDataAndTest(name, context.getResources().getString(rStringField), invalidData.toString(), false);
            }
        });

        Thread.sleep(TOAST_START_MILLISECONDS); // Wait for toast to start

        // Look at last Toast
        onView(withText(startsWith(toastStartString))).inRoot(withDecorView(not(is(LoginLab.getCurrentActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        Thread.sleep(WAIT_FOR_TOAST_TO_FINISH); // Wait for toast to finish

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDataAndTest(name, context.getResources().getString(rStringField), validData.toString(), true);
            }
        });
    }

    private void validIntegerData(final String name, final Integer rStringField, final Integer validData) {
        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDataAndTest(name, context.getResources().getString(rStringField), validData.toString(), true);
            }
        });
    }

    private void invalidIntegerData(
            final String name, final Integer rStringField, final Integer rIdContainer, Integer rIdJump,
            final Integer validData, final Integer invalidData, final String toastStartString
                               ) throws InterruptedException {
        integrationTestUtils.jumpToScreen(rIdJump);

        onView(withId(rIdContainer)).perform(closeSoftKeyboard());

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDataAndTest(name, context.getResources().getString(rStringField), invalidData.toString(), false);
            }
        });

        Thread.sleep(TOAST_START_MILLISECONDS); // Wait for toast to start

        // Look at last Toast
        onView(withText(startsWith(toastStartString))).inRoot(withDecorView(not(is(LoginLab.getCurrentActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));


        Thread.sleep(WAIT_FOR_TOAST_TO_FINISH); // Wait for toast to finish

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDataAndTest(name, context.getResources().getString(rStringField), validData.toString(), true);
            }
        });
    }

    private void launchActivity() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
    }

    @Before
    public void setUp() throws InterruptedException {
        context = InstrumentationRegistry.getTargetContext();

        validateInputs = new ValidateInputs(context);

        integrationTestUtils = new IntegrationTestUtils(context);

        launchActivity();

        // Enter some data into username/password fields
        onView(withId(R.id.email_username)).perform(replaceText(integrationTestUtils.getUsername()));
        onView(withId(R.id.password)).perform(replaceText(integrationTestUtils.getPassword()));

        onView(withId(R.id.password)).perform(closeSoftKeyboard());

        // Depress the "Login" Button
        onView(withId(R.id.button)).perform(click());

        Thread.sleep(WAIT_FOR_TOAST_TO_FINISH);
    }

    // 401k
    @Test
    public void validate401kBalance() throws InterruptedException {
        validDoubleData(InputLab.getAccount401k().getName(context),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateInvalid401kBalance() throws InterruptedException {
        invalidDoubleData( InputLab.getAccount401k().getName(context),
                R.string.field_balance,
                R.id.container_401k,
                R.id.account_401k_menu_item,
                0.0,
                -1.0,
                "401K Balance");
    }

    @Test
    public void validate401kGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getAccount401k().getName(context),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateInvalid401kGrowthRate() throws InterruptedException {
        invalidDoubleData( InputLab.getAccount401k().getName(context),
                R.string.field_growth_rate,
                R.id.container_401k,
                R.id.account_401k_menu_item,
                validateInputs.MIN_GROWTH_RATE,
                validateInputs.MIN_GROWTH_RATE-1,
                "401K Growth Rate");
    }

    @Test
    public void validate401kAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getAccount401k().getName(context),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validateInvalid401kAnnualContribution() throws InterruptedException {
        invalidDoubleData( InputLab.getAccount401k().getName(context),
                R.string.field_contributions,
                R.id.container_401k,
                R.id.account_401k_menu_item,
                validateInputs.MAX_ANNUAL_CONTRIBUTION,
                validateInputs.MAX_ANNUAL_CONTRIBUTION+1,
                "401K Annual Contribution");
    }

    @Test
    public void validate401kStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getAccount401k().getName(context),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validateInvalid401kStartWithdrawalsAge() throws InterruptedException {
        invalidIntegerData( InputLab.getAccount401k().getName(context),
                R.string.field_start_withdrawals_age,
                R.id.container_401k,
                R.id.account_401k_menu_item,
                validateInputs.MIN_AGE,
                validateInputs.MIN_AGE-1,
                "401K Start Withdrawals Age");
    }

    @Test
    public void validate401kNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getAccount401k().getName(context),
                        R.string.field_number_of_withdrawals,
                        validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }

    @Test
    public void validateInvalid401kNumberOfWithdrawals() throws InterruptedException {
        invalidIntegerData( InputLab.getAccount401k().getName(context),
                R.string.field_number_of_withdrawals,
                R.id.container_401k,
                R.id.account_401k_menu_item,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS-1,
                "401K Number Of Withdrawals");
    }
    // End of 401k

    // 403b
    @Test
    public void validate403bBalance() throws InterruptedException {
        validDoubleData(InputLab.getAccount403b().getName(context),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateInvalid403bBalance() throws InterruptedException {
        invalidDoubleData( InputLab.getAccount403b().getName(context),
                R.string.field_balance,
                R.id.container_403b,
                R.id.account_403b_menu_item,
                0.0,
                -1.0,
                "403B Balance");
    }

    @Test
    public void validate403bGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getAccount403b().getName(context),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateInvalid403bGrowthRate() throws InterruptedException {
        invalidDoubleData( InputLab.getAccount403b().getName(context),
                R.string.field_growth_rate,
                R.id.container_403b,
                R.id.account_403b_menu_item,
                validateInputs.MIN_GROWTH_RATE,
                validateInputs.MIN_GROWTH_RATE-1,
                "403B Growth Rate");
    }

    @Test
    public void validate403bAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getAccount403b().getName(context),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validateInvalid403bAnnualContribution() throws InterruptedException {
        invalidDoubleData( InputLab.getAccount403b().getName(context),
                R.string.field_contributions,
                R.id.container_403b,
                R.id.account_403b_menu_item,
                validateInputs.MAX_ANNUAL_CONTRIBUTION,
                validateInputs.MAX_ANNUAL_CONTRIBUTION+1,
                "403B Annual Contribution");
    }

    @Test
    public void validate403bStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getAccount403b().getName(context),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validateInvalid403bStartWithdrawalsAge() throws InterruptedException {
        invalidIntegerData( InputLab.getAccount403b().getName(context),
                R.string.field_start_withdrawals_age,
                R.id.container_403b,
                R.id.account_403b_menu_item,
                validateInputs.MIN_AGE,
                validateInputs.MIN_AGE-1,
                "403B Start Withdrawals Age");
    }

    @Test
    public void validate403bNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getAccount403b().getName(context),
                R.string.field_number_of_withdrawals,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }

    @Test
    public void validateInvalid403bNumberOfWithdrawals() throws InterruptedException {
        invalidIntegerData( InputLab.getAccount403b().getName(context),
                R.string.field_number_of_withdrawals,
                R.id.container_403b,
                R.id.account_403b_menu_item,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS-1,
                "403B Number Of Withdrawals");
    }
    // End of 403b

    // Brokerage
    @Test
    public void validateBrokerageBalance() throws InterruptedException {
        validDoubleData(InputLab.getBrokerage().getName(context),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateInvalidBrokerageBalance() throws InterruptedException {
        invalidDoubleData( InputLab.getBrokerage().getName(context),
                R.string.field_balance,
                R.id.container_brokerage,
                R.id.brokerage_menu_item,
                0.0,
                -1.0,
                "Brokerage Balance");
    }

    @Test
    public void validateBrokerageGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getBrokerage().getName(context),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateInvalidBrokerageGrowthRate() throws InterruptedException {
        invalidDoubleData( InputLab.getBrokerage().getName(context),
                R.string.field_growth_rate,
                R.id.container_brokerage,
                R.id.brokerage_menu_item,
                validateInputs.MIN_GROWTH_RATE,
                validateInputs.MIN_GROWTH_RATE-1,
                "Brokerage Growth Rate");
    }
    // End of Brokerage

    // Cash Balance
    @Test
    public void validateCashBalanceBalance() throws InterruptedException {
        validDoubleData(InputLab.getCashBalance().getName(context),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateInvalidCashBalanceBalance() throws InterruptedException {
        invalidDoubleData( InputLab.getCashBalance().getName(context),
                R.string.field_balance,
                R.id.container_cash_balance,
                R.id.account_cash_balance_menu_item,
                0.0,
                -1.0,
                "Cash Balance Balance");
    }

    @Test
    public void validateCashBalanceGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getCashBalance().getName(context),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateInvalidCashBalanceGrowthRate() throws InterruptedException {
        invalidDoubleData( InputLab.getCashBalance().getName(context),
                R.string.field_growth_rate,
                R.id.container_cash_balance,
                R.id.account_cash_balance_menu_item,
                validateInputs.MIN_GROWTH_RATE,
                validateInputs.MIN_GROWTH_RATE-1,
                "Cash Balance Growth Rate");
    }

    @Test
    public void validateCashBalanceAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getCashBalance().getName(context),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validateInvalidCashBalanceAnnualContribution() throws InterruptedException {
        invalidDoubleData( InputLab.getCashBalance().getName(context),
                R.string.field_contributions,
                R.id.container_cash_balance,
                R.id.account_cash_balance_menu_item,
                validateInputs.MAX_ANNUAL_CONTRIBUTION,
                validateInputs.MAX_ANNUAL_CONTRIBUTION+1,
                "Cash Balance Annual Contribution");
    }

    @Test
    public void validateCashBalanceStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getCashBalance().getName(context),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validateInvalidCashBalanceStartWithdrawalsAge() throws InterruptedException {
        invalidIntegerData( InputLab.getCashBalance().getName(context),
                R.string.field_start_withdrawals_age,
                R.id.container_cash_balance,
                R.id.account_cash_balance_menu_item,
                validateInputs.MIN_AGE,
                validateInputs.MIN_AGE-1,
                "Cash Balance Start Withdrawals Age");
    }

    @Test
    public void validateCashBalanceNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getCashBalance().getName(context),
                R.string.field_number_of_withdrawals,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }

    @Test
    public void validateInvalidCashBalanceNumberOfWithdrawals() throws InterruptedException {
        invalidIntegerData( InputLab.getCashBalance().getName(context),
                R.string.field_number_of_withdrawals,
                R.id.container_cash_balance,
                R.id.account_cash_balance_menu_item,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS-1,
                "Cash Balance Number Of Withdrawals");
    }
    // End of Cash Balance

    // Deductions
    @Test
    public void validateDeductionsDeduction() throws InterruptedException {
        validDoubleData(InputLab.getDeductions().getName(context),
                R.string.field_yearly_deductions,
                20000.0);
    }

    @Test
    public void validateInvalidDeductionsDeduction() throws InterruptedException {
        invalidDoubleData( InputLab.getDeductions().getName(context),
                R.string.field_yearly_deductions,
                R.id.container_deductions,
                R.id.deductions_menu_item,
                0.0,
                -1.0,
                "Deductions");
    }
    // End of Deductions

    // Expenses
    @Test
    public void validateExpensesExpense() throws InterruptedException {
        validDoubleData(InputLab.getExpenses().getName(context),
                R.string.field_annual_expenses,
                20000.0);
    }

    @Test
    public void validateInvalidExpensesExpense() throws InterruptedException {
        invalidDoubleData( InputLab.getExpenses().getName(context),
                R.string.field_annual_expenses,
                R.id.container_expenses,
                R.id.expenses_menu_item,
                0.0,
                -1.0,
                "Expenses");
    }
    // End of Expenses

    // Ira (Roth)
    @Test
    public void validateIraRothBalance() throws InterruptedException {
        validDoubleData(InputLab.getRothIra().getName(context),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateInvalidIraRothBalance() throws InterruptedException {
        invalidDoubleData( InputLab.getRothIra().getName(context),
                R.string.field_balance,
                R.id.container_ira_roth,
                R.id.account_roth_ira_menu_item,
                0.0,
                -1.0,
                "IRA (Roth) Balance");
    }

    @Test
    public void validateIraRothGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getRothIra().getName(context),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateInvalidIraRothGrowthRate() throws InterruptedException {
        invalidDoubleData( InputLab.getRothIra().getName(context),
                R.string.field_growth_rate,
                R.id.container_ira_roth,
                R.id.account_roth_ira_menu_item,
                validateInputs.MIN_GROWTH_RATE,
                validateInputs.MIN_GROWTH_RATE-1,
                "IRA (Roth) Growth Rate");
    }

    @Test
    public void validateIraRothAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getRothIra().getName(context),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validateInvalidIraRothAnnualContribution() throws InterruptedException {
        invalidDoubleData( InputLab.getRothIra().getName(context),
                R.string.field_contributions,
                R.id.container_ira_roth,
                R.id.account_roth_ira_menu_item,
                validateInputs.MAX_ANNUAL_CONTRIBUTION,
                validateInputs.MAX_ANNUAL_CONTRIBUTION+1,
                "IRA (Roth) Annual Contribution");
    }

    @Test
    public void validateIraRothStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getRothIra().getName(context),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validateInvalidIraRothStartWithdrawalsAge() throws InterruptedException {
        invalidIntegerData( InputLab.getRothIra().getName(context),
                R.string.field_start_withdrawals_age,
                R.id.container_ira_roth,
                R.id.account_roth_ira_menu_item,
                validateInputs.MIN_AGE,
                validateInputs.MIN_AGE-1,
                "IRA (Roth) Start Withdrawals Age");
    }

    @Test
    public void validateIraRothNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getRothIra().getName(context),
                R.string.field_number_of_withdrawals,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }

    @Test
    public void validateInvalidIraRothNumberOfWithdrawals() throws InterruptedException {
        invalidIntegerData( InputLab.getRothIra().getName(context),
                R.string.field_number_of_withdrawals,
                R.id.container_ira_roth,
                R.id.account_roth_ira_menu_item,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS-1,
                "IRA (Roth) Number Of Withdrawals");
    }
    // End of Ira (Roth)

    // Ira (Traditional)
    @Test
    public void validateIraTraditionalBalance() throws InterruptedException {
        validDoubleData(InputLab.getTraditionalIra().getName(context),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateInvalidIraTraditionalBalance() throws InterruptedException {
        invalidDoubleData( InputLab.getTraditionalIra().getName(context),
                R.string.field_balance,
                R.id.container_ira_traditional,
                R.id.account_traditional_ira_menu_item,
                0.0,
                -1.0,
                "IRA (Traditional) Balance");
    }

    @Test
    public void validateIraTraditionalGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getTraditionalIra().getName(context),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateInvalidIraTraditionalGrowthRate() throws InterruptedException {
        invalidDoubleData( InputLab.getTraditionalIra().getName(context),
                R.string.field_growth_rate,
                R.id.container_ira_traditional,
                R.id.account_traditional_ira_menu_item,
                validateInputs.MIN_GROWTH_RATE,
                validateInputs.MIN_GROWTH_RATE-1,
                "IRA (Traditional) Growth Rate");
    }

    @Test
    public void validateIraTraditionalAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getTraditionalIra().getName(context),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validateInvalidIraTraditionalAnnualContribution() throws InterruptedException {
        invalidDoubleData( InputLab.getTraditionalIra().getName(context),
                R.string.field_contributions,
                R.id.container_ira_traditional,
                R.id.account_traditional_ira_menu_item,
                validateInputs.MAX_ANNUAL_CONTRIBUTION,
                validateInputs.MAX_ANNUAL_CONTRIBUTION+1,
                "IRA (Traditional) Annual Contribution");
    }

    @Test
    public void validateIraTraditionalStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getTraditionalIra().getName(context),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validateInvalidIraTraditionalStartWithdrawalsAge() throws InterruptedException {
        invalidIntegerData( InputLab.getTraditionalIra().getName(context),
                R.string.field_start_withdrawals_age,
                R.id.container_ira_traditional,
                R.id.account_traditional_ira_menu_item,
                validateInputs.MIN_AGE,
                validateInputs.MIN_AGE-1,
                "IRA (Traditional) Start Withdrawals Age");
    }

    @Test
    public void validateIraTraditionalNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getTraditionalIra().getName(context),
                R.string.field_number_of_withdrawals,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }

    @Test
    public void validateInvalidIraTraditionalNumberOfWithdrawals() throws InterruptedException {
        invalidIntegerData( InputLab.getTraditionalIra().getName(context),
                R.string.field_number_of_withdrawals,
                R.id.container_ira_traditional,
                R.id.account_traditional_ira_menu_item,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS-1,
                "IRA (Traditional) Number Of Withdrawals");
    }
    // End of Ira (Traditional)


    // Pension
    @Test
    public void validatePensionStartingAge() throws InterruptedException{
        validIntegerData( InputLab.getPension().getName(context),
                R.string.field_starting_age,
                validateInputs.MAX_AGE);
    }

    @Test
    public void validateInvalidPensionStartingAge() throws InterruptedException {
        invalidIntegerData(InputLab.getPension().getName(context),
                R.string.field_starting_age,
                R.id.container_pension,
                R.id.pension_menu_item,
                validateInputs.MAX_AGE,
                validateInputs.MAX_AGE + 1,
                "Pension Starting Age");
    }

    @Test
    public void validatePensionMonthlyAmount() throws InterruptedException{
        validDoubleData( InputLab.getPension().getName(context),
                R.string.field_monthly_amount,
                2500.00);
    }

    @Test
    public void validateInvalidPensionMonthlyAmount() throws InterruptedException {
        invalidDoubleData(InputLab.getPension().getName(context),
                R.string.field_monthly_amount,
                R.id.container_pension,
                R.id.pension_menu_item,
                1500.00,
                -1500.00,
                "Pension Monthly Amount");
    }

    @Test
    public void validatePensionInflationAdjustedTrue() throws InterruptedException{
        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getPension().setInflationAdjusted(true);

                assertEquals(true, validateInputs.pensionInflationAdjusted());
            }
        });
    }

    @Test
    public void validateValidPensionInflationAdjustedFalse() throws InterruptedException{
        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getPension().setInflationAdjusted(false);

                assertEquals(true, validateInputs.pensionInflationAdjusted());
            }
        });
    }
    // End of Pension

    // Personal
    @Test
    public void validatePersonalRetirementAge() throws InterruptedException {
        validIntegerData( InputLab.getPersonal().getName(context),
                R.string.field_retirement_age,
                validateInputs.MAX_AGE);
    }

    @Test
    public void validateInvalidPersonalRetirementAge() throws InterruptedException {
        invalidIntegerData(InputLab.getPersonal().getName(context),
                R.string.field_retirement_age,
                R.id.container_personal,
                R.id.personal_menu_item,
                validateInputs.MAX_AGE,
                validateInputs.MAX_AGE + 1,
                "Personal Retirement Age");
    }

    @Test
    public void validatePersonalLifeExpectancyAge() throws InterruptedException {
        validIntegerData( InputLab.getPersonal().getName(context),
                R.string.field_life_expectancy_age,
                validateInputs.MAX_AGE);

    }

    @Test
    public void validateInvalidPersonalLifeExpectancyAge() throws InterruptedException {
        invalidIntegerData(InputLab.getPersonal().getName(context),
                R.string.field_life_expectancy_age,
                R.id.container_personal,
                R.id.personal_menu_item,
                validateInputs.MAX_AGE,
                validateInputs.MAX_AGE + 1,
                "Personal Life Expectancy Age");
    }

    @Test
    public void validatePersonalInflation() throws InterruptedException {
        validDoubleData(InputLab.getPersonal().getName(context),
                R.string.field_inflation,
                validateInputs.MAX_INFLATION_RATE);
    }

    @Test
    public void validateInvalidPersonalInflation() throws InterruptedException {
        invalidDoubleData(InputLab.getPersonal().getName(context),
                R.string.field_inflation,
                R.id.container_personal,
                R.id.personal_menu_item,
                0.0, // validateInputs.MAX_INFLATION_RATE,
                validateInputs.MAX_INFLATION_RATE + 1,
                "Personal Inflation Rate");
    }

    @Test
    public void validatePersonalAges()  throws InterruptedException {
         LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String simulationDate = "01/01/2017";
                String birthDate = "01/01/1958";
                Integer deathAge = validateInputs.MAX_AGE;
                Integer account401kStartingWithdrawalsAge = validateInputs.MAX_AGE - validateInputs.MAX_NUMBER_OF_WITHDRAWALS;
                Integer account401kNumberOfWithdrawals = validateInputs.MAX_NUMBER_OF_WITHDRAWALS;

                InputLab.getPersonal().setSimulationDate(simulationDate);
                InputLab.getPersonal().setBirthDate(birthDate);
                InputLab.getPersonal().setLifeExpectancyAge(deathAge.toString());
                InputLab.getAccount401k().setStartWithdrawalsAge(account401kStartingWithdrawalsAge.toString());
                InputLab.getAccount401k().setNumberOfWithdrawals(account401kNumberOfWithdrawals.toString());

                assertEquals(false, validateInputs.ages());
            }
        });
    }

    @Test
    public void validateInvalidPersonalAges() throws InterruptedException {
        integrationTestUtils.jumpToScreen(R.id.personal_menu_item);

        onView(withId(R.id.container_personal)).perform(closeSoftKeyboard());

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String simulationDate = "01/01/1970";
                String birthDate = "01/01/1810";
                Integer deathAge = validateInputs.MIN_AGE;
                Integer account401kStartingWithdrawalsAge = validateInputs.MIN_AGE;
                Integer account401kNumberOfWithdrawals = validateInputs.MAX_NUMBER_OF_WITHDRAWALS;

                InputLab.getPersonal().setSimulationDate(simulationDate);
                InputLab.getPersonal().setBirthDate(birthDate);
                InputLab.getPersonal().setLifeExpectancyAge(deathAge.toString());

                InputLab.getAccount401k().setStartWithdrawalsAge(account401kStartingWithdrawalsAge.toString());
                InputLab.getAccount401k().setNumberOfWithdrawals(account401kNumberOfWithdrawals.toString());

                assertEquals(false, validateInputs.ages());
            }
        });

        Thread.sleep(TOAST_START_MILLISECONDS); // Wait for toast start

        onView(withText(startsWith("Current Age"))).inRoot(withDecorView(not(is(LoginLab.getCurrentActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        Thread.sleep(WAIT_TOASTS_FOR_COMPLETION); // Wait for toasts to finish

        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String simulationDate = "01/01/1970";
                String birthDate = "01/01/1958";
                Integer deathAge = 95;
                Integer account401kStartingWithdrawalsAge = 65;
                Integer account401kNumberOfWithdrawals = 10;

                InputLab.getPersonal().setSimulationDate(simulationDate);
                InputLab.getPersonal().setBirthDate(birthDate);
                InputLab.getPersonal().setLifeExpectancyAge(deathAge.toString());

                InputLab.getAccount401k().setStartWithdrawalsAge(account401kStartingWithdrawalsAge.toString());
                InputLab.getAccount401k().setNumberOfWithdrawals(account401kNumberOfWithdrawals.toString());

                assertEquals(true, validateInputs.ages());
            }
        });
    }
    // End of Personal

    // Salary
    @Test
    public void validateSalarySalary() throws InterruptedException {
        validDoubleData(InputLab.getSalary().getName(context),
                R.string.field_salary,
                200000.00);
    }

    @Test
    public void validateInvalidSalarySalary() throws InterruptedException {
        invalidDoubleData(InputLab.getSalary().getName(context),
                     R.string.field_salary,
                     R.id.container_salary,
                     R.id.salary_menu_item,
                    0.00,
                    -1.00,
                    "Salary");
    }

    @Test
    public void validateSalaryMeritIncrease() throws InterruptedException {
        validDoubleData(InputLab.getSalary().getName(context),
                R.string.field_merit_increase,
                validateInputs.MAX_MERIT_INCREASE);
    }

    @Test
    public void validateInvalidSalaryMeritIncrease() throws InterruptedException {
        invalidDoubleData(InputLab.getSalary().getName(context),
                R.string.field_merit_increase,
                R.id.container_salary,
                R.id.salary_menu_item,
                validateInputs.MAX_MERIT_INCREASE,
                validateInputs.MAX_MERIT_INCREASE + 1.0,
                "Salary Merit Increase");
    }
    // End of Salary

    // Savings
    @Test
    public void validateSavingsBalance() throws InterruptedException {
        validDoubleData(InputLab.getSavings().getName(context),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateInvalidSavingsBalance() throws InterruptedException {
        invalidDoubleData( InputLab.getSavings().getName(context),
                R.string.field_balance,
                R.id.container_savings,
                R.id.savings_menu_item,
                0.0,
                -1.0,
                "Savings Balance");
    }

    @Test
    public void validateSavingsGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getSavings().getName(context),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateInvalidSavingsGrowthRate() throws InterruptedException {
        invalidDoubleData( InputLab.getSavings().getName(context),
                R.string.field_growth_rate,
                R.id.container_savings,
                R.id.savings_menu_item,
                validateInputs.MIN_GROWTH_RATE,
                validateInputs.MIN_GROWTH_RATE-1,
                "Savings Growth Rate");
    }
    // End of Savings

    // Social Security
    @Test
    public void validateSocialSecurityStartingAge() throws InterruptedException {
        validIntegerData( InputLab.getSocialSecurity().getName(context),
                R.string.field_starting_age,
                validateInputs.MIN_SOCIAL_SECURITY_AGE);
    }

    @Test
    public void validateInvalidSocialSecurityStartingAge() throws InterruptedException {
        invalidIntegerData( InputLab.getSocialSecurity().getName(context),
                R.string.field_starting_age,
                R.id.container_social_security,
                R.id.social_security_menu_item,
                validateInputs.MIN_SOCIAL_SECURITY_AGE,
                validateInputs.MIN_SOCIAL_SECURITY_AGE-1,
                "Social Security Starting Age");
    }

    @Test
    public void validateSocialSecurityMonthlyAmount() throws InterruptedException {
        validDoubleData( InputLab.getSocialSecurity().getName(context),
                R.string.field_monthly_amount,
                1500.00);
    }

    @Test
    public void validateInvalidSocialSecurityMonthlyAmount() throws InterruptedException {
        invalidDoubleData( InputLab.getSocialSecurity().getName(context),
                R.string.field_monthly_amount,
                R.id.container_social_security,
                R.id.social_security_menu_item,
                1500.00,
                -1500.00,
                "Social Security Monthly Amount");
    }
    // End of Social Security

    // Taxes
    @Test
    public void validateTaxesFederalRate() throws InterruptedException {
        validDoubleData(InputLab.getTaxes().getName(context),
                R.string.field_federal_tax_rate,
                validateInputs.MAX_TAX_RATE);
    }

    @Test
    public void validateInvalidTaxesFederalRate() throws InterruptedException {
        invalidDoubleData(InputLab.getTaxes().getName(context),
                R.string.field_federal_tax_rate,
                R.id.container_taxes,
                R.id.taxes_menu_item,
                validateInputs.MAX_TAX_RATE,
                validateInputs.MAX_TAX_RATE + 1,
                "Federal Tax Rate");
    }

    @Test
    public void validateTaxesStateRate() throws InterruptedException {
        validDoubleData(InputLab.getTaxes().getName(context),
                R.string.field_state_tax_rate,
                validateInputs.MAX_TAX_RATE);
    }

    @Test
    public void validateInvalidTaxesStateRate() throws InterruptedException {
        invalidDoubleData(InputLab.getTaxes().getName(context),
                R.string.field_state_tax_rate,
                R.id.container_taxes,
                R.id.taxes_menu_item,
                validateInputs.MAX_TAX_RATE,
                validateInputs.MAX_TAX_RATE + 1,
                "State Tax Rate");
    }
    // End of Taxes
}
