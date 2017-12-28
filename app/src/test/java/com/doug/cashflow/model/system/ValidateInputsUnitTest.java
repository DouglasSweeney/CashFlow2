package com.doug.cashflow.model.system;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.doug.cashflow.BuildConfig;
import com.doug.cashflow.R;
import com.doug.cashflow.controller.AlertDialogFragment;
import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.LoginLab;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import com.doug.cashflow.TestUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginLab.class, Context.class, MockContext.class, LayoutInflater.class, View.class, TextView.class,
                 CheckBox.class, AppCompatActivity.class, Resources.class, AlertDialogFragment.class})
@Config(constants = BuildConfig.class)
public class ValidateInputsUnitTest {

    private static final Integer WAIT_FOR_APPLICATION = 5000;

    // SUT
    private ValidateInputs validateInputs;

    private static FragmentManager fragmentManager;

    private static Context mockContext = mock(MockContext.class);
    private static LayoutInflater mockInflater = mock(LayoutInflater.class);
    private static LayoutInflater mockActivityInflater = mock(LayoutInflater.class);
    private static View mockView = mock(View.class);
    private static View mockErrorView = mock(View.class);

    private static ViewGroup mockParent = mock(ViewGroup.class);

    private static TextView mock401kTitleView = mock(TextView.class);
    private static TextView mock401kBalanceView = mock(TextView.class);
    private static TextView mock401kGrowthRateView = mock(TextView.class);
    private static TextView mock401kAnnualContributionsView = mock(TextView.class);
    private static TextView mock401kStartWithdrawalsAgeView = mock(TextView.class);
    private static TextView mock401kNumberOfWithdrawalsView = mock(TextView.class);

    private static TextView mock403bTitleView = mock(TextView.class);
    private static TextView mock403bBalanceView = mock(TextView.class);
    private static TextView mock403bGrowthRateView = mock(TextView.class);
    private static TextView mock403bAnnualContributionsView = mock(TextView.class);
    private static TextView mock403bStartWithdrawalsAgeView = mock(TextView.class);
    private static TextView mock403bNumberOfWithdrawalsView = mock(TextView.class);

    private static TextView mockBrokerageTitleView = mock(TextView.class);
    private static TextView mockBrokerageBalanceView = mock(TextView.class);
    private static TextView mockBrokerageGrowthRateView = mock(TextView.class);

    private static TextView mockCashBalanceTitleView = mock(TextView.class);
    private static TextView mockCashBalanceBalanceView = mock(TextView.class);
    private static TextView mockCashBalanceGrowthRateView = mock(TextView.class);
    private static TextView mockCashBalanceAnnualContributionsView = mock(TextView.class);
    private static TextView mockCashBalanceStartWithdrawalsAgeView = mock(TextView.class);
    private static TextView mockCashBalanceNumberOfWithdrawalsView = mock(TextView.class);

    private static TextView mockDeductionsTitleView = mock(TextView.class);
    private static TextView mockDeductionsDeductionView = mock(TextView.class);

    private static TextView mockExpensesTitleView = mock(TextView.class);
    private static TextView mockExpensesExpenseView = mock(TextView.class);

    private static TextView mockRothTitleView = mock(TextView.class);
    private static TextView mockRothBalanceView = mock(TextView.class);
    private static TextView mockRothGrowthRateView = mock(TextView.class);
    private static TextView mockRothAnnualContributionsView = mock(TextView.class);
    private static TextView mockRothStartWithdrawalsAgeView = mock(TextView.class);
    private static TextView mockRothNumberOfWithdrawalsView = mock(TextView.class);

    private static TextView mockIraTitleView = mock(TextView.class);
    private static TextView mockIraBalanceView = mock(TextView.class);
    private static TextView mockIraGrowthRateView = mock(TextView.class);
    private static TextView mockIraAnnualContributionsView = mock(TextView.class);
    private static TextView mockIraStartWithdrawalsAgeView = mock(TextView.class);
    private static TextView mockIraNumberOfWithdrawalsView = mock(TextView.class);

    private static TextView mockPensionTitleView = mock(TextView.class);
    private static TextView mockPensionStartingAgeView = mock(TextView.class);
    private static TextView mockPensionMonthlyAmountView = mock(TextView.class);
    private static CheckBox mockPensionInflationAdjustedView = mock(CheckBox.class);

    private static TextView mockSalaryTitleView = mock(TextView.class);
    private static TextView mockSalaryAnnualSalaryView = mock(TextView.class);
    private static TextView mockSalaryMeritIncreaseView = mock(TextView.class);

    private static TextView mockSavingsTitleView = mock(TextView.class);
    private static TextView mockSavingsBalanceView = mock(TextView.class);
    private static TextView mockSavingsGrowthRateView = mock(TextView.class);

    private static TextView mockSocialSecurityTitleView = mock(TextView.class);
    private static TextView mockSocialSecurityStartingAgeView = mock(TextView.class);
    private static TextView mockSocialSecurityMonthlyAmountView = mock(TextView.class);

    private static TextView mockTaxesTitleView = mock(TextView.class);
    private static TextView mockTaxesFederalTaxRateView = mock(TextView.class);
    private static TextView mockTaxesStateTaxRateView = mock(TextView.class);

    private static AppCompatActivity mockActivity = mock(AppCompatActivity.class);
    private static Resources mockResources = mock(Resources.class);
    private static AlertDialogFragment mockAlertDialogFragment = mock(AlertDialogFragment.class);
    private static Toast toast = mock(Toast.class);

    private static final TestUtil testUtil = new TestUtil();

    @Before
    public void setUp() throws InterruptedException {
        initMocks(this);

        mockStatic(LayoutInflater.class);
        mockStatic(View.class);
        mockStatic(MockContext.class);
        mockStatic(MockContext.class);
        mockStatic(ViewGroup.class);
        mockStatic(LoginLab.class);
        mockStatic(TextView.class);
        mockStatic(CheckBox.class);
        mockStatic(AppCompatActivity.class);
        mockStatic(Resources.class);
        mockStatic(AlertDialogFragment.class);

        testUtil.MockContextGetStringInputNames(mockContext);
        testUtil.MockContextGetStringInputHeaders(mockContext);
        testUtil.MockContextGetStringDatabaseIntegers(mockContext);
        testUtil.MockContextGetStringDatabaseReal(mockContext);

        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(LayoutInflater.from(mockActivity)).thenReturn(mockActivityInflater);
        when(mockInflater.inflate(R.layout.error_string, null)).thenReturn(mockErrorView);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        when(mockView.findViewById(R.id.title)).thenReturn(mock401kTitleView);
        when(mockView.findViewById(R.id.account_401k_balance)).thenReturn(mock401kBalanceView);
        when(mockView.findViewById(R.id.account_401k_growth_rate)).thenReturn(mock401kGrowthRateView);
        when(mockView.findViewById(R.id.account_401k_contributions)).thenReturn(mock401kAnnualContributionsView);
        when(mockView.findViewById(R.id.account_401k_start_withdrawals_age)).thenReturn(mock401kStartWithdrawalsAgeView);
        when(mockView.findViewById(R.id.account_401k_number_of_withdrawals)).thenReturn(mock401kNumberOfWithdrawalsView);

        when(mockView.findViewById(R.id.title)).thenReturn(mock403bTitleView);
        when(mockView.findViewById(R.id.account_403b_balance)).thenReturn(mock403bBalanceView);
        when(mockView.findViewById(R.id.account_403b_growth_rate)).thenReturn(mock403bGrowthRateView);
        when(mockView.findViewById(R.id.account_403b_contributions)).thenReturn(mock403bAnnualContributionsView);
        when(mockView.findViewById(R.id.account_403b_start_withdrawals_age)).thenReturn(mock403bStartWithdrawalsAgeView);
        when(mockView.findViewById(R.id.account_403b_number_of_withdrawals)).thenReturn(mock403bNumberOfWithdrawalsView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockBrokerageTitleView);
        when(mockView.findViewById(R.id.brokerage_balance)).thenReturn(mockBrokerageBalanceView);
        when(mockView.findViewById(R.id.brokerage_growth_rate)).thenReturn(mockBrokerageGrowthRateView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockCashBalanceTitleView);
        when(mockView.findViewById(R.id.cash_balance_balance)).thenReturn(mockCashBalanceBalanceView);
        when(mockView.findViewById(R.id.cash_balance_growth_rate)).thenReturn(mockCashBalanceGrowthRateView);
        when(mockView.findViewById(R.id.cash_balance_contributions)).thenReturn(mockCashBalanceAnnualContributionsView);
        when(mockView.findViewById(R.id.cash_balance_start_withdrawals_age)).thenReturn(mockCashBalanceStartWithdrawalsAgeView);
        when(mockView.findViewById(R.id.cash_balance_number_of_withdrawals)).thenReturn(mockCashBalanceNumberOfWithdrawalsView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockDeductionsTitleView);
        when(mockView.findViewById(R.id.deductions_deductions)).thenReturn(mockDeductionsDeductionView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockExpensesTitleView);
        when(mockView.findViewById(R.id.expenses_expense)).thenReturn(mockExpensesExpenseView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockRothTitleView);
        when(mockView.findViewById(R.id.roth_ira_balance)).thenReturn(mockRothBalanceView);
        when(mockView.findViewById(R.id.roth_ira_growth_rate)).thenReturn(mockRothGrowthRateView);
        when(mockView.findViewById(R.id.roth_ira_contributions)).thenReturn(mockRothAnnualContributionsView);
        when(mockView.findViewById(R.id.roth_ira_start_withdrawals_age)).thenReturn(mockRothStartWithdrawalsAgeView);
        when(mockView.findViewById(R.id.roth_ira_number_of_withdrawals)).thenReturn(mockRothNumberOfWithdrawalsView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockIraTitleView);
        when(mockView.findViewById(R.id.traditional_ira_balance)).thenReturn(mockIraBalanceView);
        when(mockView.findViewById(R.id.traditional_ira_growth_rate)).thenReturn(mockIraGrowthRateView);
        when(mockView.findViewById(R.id.traditional_ira_contributions)).thenReturn(mockIraAnnualContributionsView);
        when(mockView.findViewById(R.id.traditional_ira_start_withdrawals_age)).thenReturn(mockIraStartWithdrawalsAgeView);
        when(mockView.findViewById(R.id.traditional_ira_number_of_withdrawals)).thenReturn(mockIraNumberOfWithdrawalsView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockPensionTitleView);
        when(mockView.findViewById(R.id.pension_starting_age)).thenReturn(mockPensionStartingAgeView);
        when(mockView.findViewById(R.id.pension_monthly_amount)).thenReturn(mockPensionMonthlyAmountView);
        when(mockView.findViewById(R.id.pension_inflation_adjusted)).thenReturn(mockPensionInflationAdjustedView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockSalaryTitleView);
        when(mockView.findViewById(R.id.salary_salary)).thenReturn(mockSalaryAnnualSalaryView);
        when(mockView.findViewById(R.id.salary_merit_increase)).thenReturn(mockSalaryMeritIncreaseView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockSavingsTitleView);
        when(mockView.findViewById(R.id.savings_balance)).thenReturn(mockSavingsBalanceView);
        when(mockView.findViewById(R.id.savings_growth_rate)).thenReturn(mockSavingsGrowthRateView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockSocialSecurityTitleView);
        when(mockView.findViewById(R.id.social_security_starting_age)).thenReturn(mockSocialSecurityStartingAgeView);
        when(mockView.findViewById(R.id.social_security_monthly_amount)).thenReturn(mockSocialSecurityMonthlyAmountView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockTaxesTitleView);
        when(mockView.findViewById(R.id.taxes_federal_tax_rate)).thenReturn(mockTaxesFederalTaxRateView);
        when(mockView.findViewById(R.id.taxes_state_tax_rate)).thenReturn(mockTaxesStateTaxRateView);

        fragmentManager = mockActivity.getSupportFragmentManager();
        when(mockActivity.getSupportFragmentManager()).thenReturn(fragmentManager);

        when(LoginLab.getCurrentActivity()).thenReturn(mockActivity);
        when(AlertDialogFragment.newInstance(anyInt(), anyInt(), anyInt())).thenReturn(mockAlertDialogFragment);

        validateInputs = new ValidateInputs(mockContext);

        LoginLab.getInstance(mockContext, true);
        InputLab.getInstance(mockContext, mockActivity.getSupportFragmentManager(), mockParent);

        Thread.sleep(WAIT_FOR_APPLICATION);
    }

    private void setDataAndTest(String name, String field, String data, boolean expectedAnswer) {
        if (name.equals(mockContext.getString(R.string.input_header_401k))) {
            if (field.equals(mockContext.getString(R.string.field_balance))) {
                InputLab.getAccount401k().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.account401kBalance());
            } else if (field.equals(mockContext.getString(R.string.field_growth_rate))) {
                InputLab.getAccount401k().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.account401kGrowthRate());
            } else if (field.equals(mockContext.getString(R.string.field_contributions))) {
                InputLab.getAccount401k().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.account401kAnnualContribution());
            } else if (field.equals(mockContext.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getAccount401k().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.account401kStartWithdrawalsAge());
            } else if (field.equals(mockContext.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getAccount401k().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.account401kNumberOfWithdrawals());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_403b))) {
            if (field.equals(mockContext.getString(R.string.field_balance))) {
                InputLab.getAccount403b().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.account403bBalance());
            } else if (field.equals(mockContext.getString(R.string.field_growth_rate))) {
                InputLab.getAccount403b().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.account403bGrowthRate());
            } else if (field.equals(mockContext.getString(R.string.field_contributions))) {
                InputLab.getAccount403b().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.account403bAnnualContribution());
            } else if (field.equals(mockContext.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getAccount403b().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.account403bStartWithdrawalsAge());
            } else if (field.equals(mockContext.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getAccount403b().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.account403bNumberOfWithdrawals());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_brokerage))) {
            if (field.equals(mockContext.getString(R.string.field_balance))) {
                InputLab.getBrokerage().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.brokerageBalance());
            } else if (field.equals(mockContext.getString(R.string.field_growth_rate))) {
                InputLab.getBrokerage().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.brokerageGrowthRate());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_cash_balance))) {
            if (field.equals(mockContext.getString(R.string.field_balance))) {
                InputLab.getCashBalance().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceBalance());
            } else if (field.equals(mockContext.getString(R.string.field_growth_rate))) {
                InputLab.getCashBalance().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceGrowthRate());
            } else if (field.equals(mockContext.getString(R.string.field_contributions))) {
                InputLab.getCashBalance().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceAnnualContribution());
            } else if (field.equals(mockContext.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getCashBalance().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceStartWithdrawalsAge());
            } else if (field.equals(mockContext.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getCashBalance().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.accountCashBalanceNumberOfWithdrawals());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_deductions))) {
            if (field.equals(mockContext.getString(R.string.field_yearly_deductions))) {
                InputLab.getDeductions().setDeductions(data);

                assertEquals(expectedAnswer, validateInputs.deductionsDeductions());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_expenses))) {
            if (field.equals(mockContext.getString(R.string.field_annual_expenses))) {
                InputLab.getExpenses().setExpenses(data);

                assertEquals(expectedAnswer, validateInputs.expenseExpenses());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_ira_roth))) {
            if (field.equals(mockContext.getString(R.string.field_balance))) {
                InputLab.getRothIra().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.accountRothBalance());
            } else if (field.equals(mockContext.getString(R.string.field_growth_rate))) {
                InputLab.getRothIra().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.accountRothGrowthRate());
            } else if (field.equals(mockContext.getString(R.string.field_contributions))) {
                InputLab.getRothIra().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.accountRothAnnualContribution());
            } else if (field.equals(mockContext.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getRothIra().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.accountRothStartWithdrawalsAge());
            } else if (field.equals(mockContext.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getRothIra().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.accountRothNumberOfWithdrawals());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_ira_traditional))) {
            if (field.equals(mockContext.getString(R.string.field_balance))) {
                InputLab.getTraditionalIra().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.accountIraBalance());
            } else if (field.equals(mockContext.getString(R.string.field_growth_rate))) {
                InputLab.getTraditionalIra().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.accountIraGrowthRate());
            } else if (field.equals(mockContext.getString(R.string.field_contributions))) {
                InputLab.getTraditionalIra().setContributions(data);

                assertEquals(expectedAnswer, validateInputs.accountIraAnnualContribution());
            } else if (field.equals(mockContext.getString(R.string.field_start_withdrawals_age))) {
                InputLab.getTraditionalIra().setStartWithdrawalsAge(data);

                assertEquals(expectedAnswer, validateInputs.accountIraStartWithdrawalsAge());
            } else if (field.equals(mockContext.getString(R.string.field_number_of_withdrawals))) {
                InputLab.getTraditionalIra().setNumberOfWithdrawals(data);

                assertEquals(expectedAnswer, validateInputs.accountIraNumberOfWithdrawals());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_pension))) {
            if (field.equals(mockContext.getString(R.string.field_starting_age))) {
                InputLab.getPension().setStartingAge(data);

                assertEquals(expectedAnswer, validateInputs.pensionStartingAge());
            } else if (field.equals(mockContext.getString(R.string.field_monthly_amount))) {
                InputLab.getPension().setMonthlyAmount(data);

                assertEquals(expectedAnswer, validateInputs.pensionMonthlyAmount());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_personal))) {
            if (field.equals(mockContext.getString(R.string.field_retirement_age))) {
                InputLab.getPersonal().setRetirementAge(data);

                assertEquals(expectedAnswer, validateInputs.personalRetirementAge());
            } else if (field.equals(mockContext.getString(R.string.field_life_expectancy_age))) {
                InputLab.getPersonal().setLifeExpectancyAge(data);

                assertEquals(expectedAnswer, validateInputs.personalDeathAge());
            } else if (field.equals(mockContext.getString(R.string.field_inflation))) {
                InputLab.getPersonal().setInflation(data);

                assertEquals(expectedAnswer, validateInputs.personalInflationRate());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_salary))) {
            if (field.equals(mockContext.getString(R.string.field_salary))) {
                InputLab.getSalary().setSalary(data);

                assertEquals(expectedAnswer, validateInputs.salarySalary());
            } else if (field.equals(mockContext.getString(R.string.field_merit_increase))) {
                InputLab.getSalary().setMeritIncrease(data);

                assertEquals(expectedAnswer, validateInputs.salaryMeritIncrease());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_savings))) {
            if (field.equals(mockContext.getString(R.string.field_balance))) {
                InputLab.getSavings().setBalance(data);

                assertEquals(expectedAnswer, validateInputs.savingsBalance());
            } else if (field.equals(mockContext.getString(R.string.field_growth_rate))) {
                InputLab.getSavings().setGrowthRate(data);

                assertEquals(expectedAnswer, validateInputs.savingsGrowthRate());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_social_security))) {
            if (field.equals(mockContext.getString(R.string.field_starting_age))) {
                InputLab.getSocialSecurity().setStartingAge(data);

                assertEquals(expectedAnswer, validateInputs.socialSecurityStartingAge());
            } else if (field.equals(mockContext.getString(R.string.field_monthly_amount))) {
                InputLab.getSocialSecurity().setMonthlyAmount(data);

                assertEquals(expectedAnswer, validateInputs.socialSecurityMonthlyAmount());
            }
        } else if (name.equals(mockContext.getString(R.string.input_header_taxes))) {
            if (field.equals(mockContext.getString(R.string.field_federal_tax_rate))) {
                InputLab.getTaxes().setFederalTaxRate(data);

                assertEquals(expectedAnswer, validateInputs.taxesFederal());
            } else if (field.equals(mockContext.getString(R.string.field_state_tax_rate))) {
                InputLab.getTaxes().setStateTaxRate(data);

                assertEquals(expectedAnswer, validateInputs.taxesState());
            }
        }
    }

    private void validIntegerData(final String name, final Integer rStringField, final Integer validData) throws InterruptedException {
        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDataAndTest(name, mockContext.getResources().getString(rStringField), validData.toString(), true);
            }
        });
    }

    private void validDoubleData(final String name, final Integer rStringField, final Double validData) throws InterruptedException {
        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setDataAndTest(name, mockContext.getResources().getString(rStringField), validData.toString(), true);
            }
        });
    }

    // 401k
    @Test
    public void validate401kBalance() throws InterruptedException {
        validDoubleData(InputLab.getAccount401k().getName(mockContext),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validate401kGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getAccount401k().getName(mockContext),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validate401kAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getAccount401k().getName(mockContext),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validate401kStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getAccount401k().getName(mockContext),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validate401kNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getAccount401k().getName(mockContext),
                R.string.field_number_of_withdrawals,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }
    // End of 401k

    // 403b
    @Test
    public void validate403bBalance() throws InterruptedException {
        validDoubleData(InputLab.getAccount403b().getName(mockContext),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validate403bGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getAccount403b().getName(mockContext),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validate403bAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getAccount403b().getName(mockContext),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validate403bStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getAccount403b().getName(mockContext),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validate403bNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getAccount403b().getName(mockContext),
                R.string.field_number_of_withdrawals,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }
    // End of 403b

    // Brokerage
    @Test
    public void validateBrokerageBalance() throws InterruptedException {
        validDoubleData(InputLab.getBrokerage().getName(mockContext),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateBrokerageGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getBrokerage().getName(mockContext),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }
    // End of Brokerage

    // Cash Balance
    @Test
    public void validateCashBalanceBalance() throws InterruptedException {
        validDoubleData(InputLab.getCashBalance().getName(mockContext),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateCashBalanceGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getCashBalance().getName(mockContext),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateCashBalanceAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getCashBalance().getName(mockContext),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validateCashBalanceStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getCashBalance().getName(mockContext),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validateCashBalanceNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getCashBalance().getName(mockContext),
                R.string.field_number_of_withdrawals,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }
    // End of Cash Balance

    // Deductions
    @Test
    public void validateDeductionsDeduction() throws InterruptedException {
        validDoubleData(InputLab.getDeductions().getName(mockContext),
                R.string.field_yearly_deductions,
                20000.0);
    }
    // End of Deductions

    // Expenses
    @Test
    public void validateExpensesExpense() throws InterruptedException {
        validDoubleData(InputLab.getExpenses().getName(mockContext),
                R.string.field_annual_expenses,
                20000.0);
    }
    // End of Expenses

    // Ira (Roth)
    @Test
    public void validateIraRothBalance() throws InterruptedException {
        validDoubleData(InputLab.getRothIra().getName(mockContext),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateIraRothGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getRothIra().getName(mockContext),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateIraRothAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getRothIra().getName(mockContext),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validateIraRothStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getRothIra().getName(mockContext),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validateIraRothNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getRothIra().getName(mockContext),
                R.string.field_number_of_withdrawals,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }
    // End of Ira (Roth)

    // Ira (Traditional)
    @Test
    public void validateIraTraditionalBalance() throws InterruptedException {
        validDoubleData(InputLab.getTraditionalIra().getName(mockContext),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateIraTraditionalGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getTraditionalIra().getName(mockContext),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }

    @Test
    public void validateIraTraditionalAnnualContribution() throws InterruptedException {
        validDoubleData(InputLab.getTraditionalIra().getName(mockContext),
                R.string.field_contributions,
                validateInputs.MAX_ANNUAL_CONTRIBUTION);
    }

    @Test
    public void validateIraTraditionalStartWithdrawalsAge() throws InterruptedException {
        validIntegerData(InputLab.getTraditionalIra().getName(mockContext),
                R.string.field_start_withdrawals_age,
                validateInputs.MIN_AGE);
    }

    @Test
    public void validateIraTraditionalNumberOfWithdrawals() throws InterruptedException {
        validIntegerData(InputLab.getTraditionalIra().getName(mockContext),
                R.string.field_number_of_withdrawals,
                validateInputs.MIN_NUMBER_OF_WITHDRAWALS);
    }
    // End of Ira (Traditional)


    // Pension
    @Test
    public void validatePensionStartingAge() throws InterruptedException {
        validIntegerData(InputLab.getPension().getName(mockContext),
                R.string.field_starting_age,
                validateInputs.MAX_AGE);
    }

    @Test
    public void validatePensionMonthlyAmount() throws InterruptedException {
        validDoubleData(InputLab.getPension().getName(mockContext),
                R.string.field_monthly_amount,
                2500.00);
    }

    @Test
    public void validatePensionInflationAdjustedTrue() throws InterruptedException {
        LoginLab.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputLab.getPension().setInflationAdjusted(true);

                assertEquals(true, validateInputs.pensionInflationAdjusted());
            }
        });
    }

    @Test
    public void validateValidPensionInflationAdjustedFalse() throws InterruptedException {
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
        validIntegerData(InputLab.getPersonal().getName(mockContext),
                R.string.field_retirement_age,
                validateInputs.MAX_AGE);
    }

    @Test
    public void validatePersonalLifeExpectancyAge() throws InterruptedException {
        validIntegerData(InputLab.getPersonal().getName(mockContext),
                R.string.field_life_expectancy_age,
                validateInputs.MAX_AGE);

    }

    @Test
    public void validatePersonalInflation() throws InterruptedException {
        validDoubleData(InputLab.getPersonal().getName(mockContext),
                R.string.field_inflation,
                validateInputs.MAX_INFLATION_RATE);
    }

    @Test
    public void validatePersonalAges() throws InterruptedException {
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
    // End of Personal

    // Salary
    @Test
    public void validateSalarySalary() throws InterruptedException {
        validDoubleData(InputLab.getSalary().getName(mockContext),
                R.string.field_salary,
                200000.00);
    }

    @Test
    public void validateSalaryMeritIncrease() throws InterruptedException {
        validDoubleData(InputLab.getSalary().getName(mockContext),
                R.string.field_merit_increase,
                validateInputs.MAX_MERIT_INCREASE);
    }
    // End of Salary

    // Savings
    @Test
    public void validateSavingsBalance() throws InterruptedException {
        validDoubleData(InputLab.getSavings().getName(mockContext),
                R.string.field_balance,
                20000.0);
    }

    @Test
    public void validateSavingsGrowthRate() throws InterruptedException {
        validDoubleData(InputLab.getSavings().getName(mockContext),
                R.string.field_growth_rate,
                validateInputs.MAX_GROWTH_RATE);
    }
    // End of Savings

    // Social Security
    @Test
    public void validateSocialSecurityStartingAge() throws InterruptedException {
        validIntegerData(InputLab.getSocialSecurity().getName(mockContext),
                R.string.field_starting_age,
                validateInputs.MIN_SOCIAL_SECURITY_AGE);
    }

    @Test
    public void validateSocialSecurityMonthlyAmount() throws InterruptedException {
        validDoubleData(InputLab.getSocialSecurity().getName(mockContext),
                R.string.field_monthly_amount,
                1500.00);
    }
    // End of Social Security

    // Taxes
    @Test
    public void validateTaxesFederalRate() throws InterruptedException {
        validDoubleData(InputLab.getTaxes().getName(mockContext),
                R.string.field_federal_tax_rate,
                validateInputs.MAX_TAX_RATE);
    }

    @Test
    public void validateTaxesStateRate() throws InterruptedException {
        validDoubleData(InputLab.getTaxes().getName(mockContext),
                R.string.field_state_tax_rate,
                validateInputs.MAX_TAX_RATE);
    }
    // End of Taxes}
}

