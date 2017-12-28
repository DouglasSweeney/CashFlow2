package com.doug.cashflow.view.inputs;

import android.content.Context;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.doug.cashflow.BuildConfig;
import com.doug.cashflow.R;
import com.doug.cashflow.TestRoboActivity;
import com.doug.cashflow.TestUtil;
import com.doug.cashflow.controller.InputLab;
import com.doug.cashflow.controller.LoginLab;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

// original
@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginLab.class, Context.class, MockContext.class, LayoutInflater.class, View.class, TextView.class})
@Config(constants = BuildConfig.class)
public class Account403bUnitTest {

    private Account403B account403B;

    private Context    mockContext = mock(MockContext.class);
    private LayoutInflater mockInflater = mock(LayoutInflater.class);

    private View mockView = mock(View.class);

    private ViewGroup mockParent = mock(ViewGroup.class);

    private TextView mock401kTitleView = mock(TextView.class);
    private TextView mock401kBalanceView = mock(TextView.class);
    private TextView mock401kGrowthRateView = mock(TextView.class);
    private TextView mock401kAnnualContributionsView = mock(TextView.class);
    private TextView mock401kStartWithdrawalsAgeView = mock(TextView.class);
    private TextView mock401kNumberOfWithdrawalsView = mock(TextView.class);

    private TextView mock403bTitleView = mock(TextView.class);
    private TextView mock403bBalanceView = mock(TextView.class);
    private TextView mock403bGrowthRateView = mock(TextView.class);
    private TextView mock403bAnnualContributionsView = mock(TextView.class);
    private TextView mock403bStartWithdrawalsAgeView = mock(TextView.class);
    private TextView mock403bNumberOfWithdrawalsView = mock(TextView.class);

    private TextView mockBrokerageTitleView = mock(TextView.class);
    private TextView mockBrokerageBalanceView = mock(TextView.class);
    private TextView mockBrokerageGrowthRateView = mock(TextView.class);

    private TextView mockCashBalanceTitleView = mock(TextView.class);
    private TextView mockCashBalanceBalanceView = mock(TextView.class);
    private TextView mockCashBalanceGrowthRateView = mock(TextView.class);
    private TextView mockCashBalanceAnnualContributionsView = mock(TextView.class);
    private TextView mockCashBalanceStartWithdrawalsAgeView = mock(TextView.class);
    private TextView mockCashBalanceNumberOfWithdrawalsView = mock(TextView.class);

    private TextView mockDeductionsTitleView = mock(TextView.class);
    private TextView mockDeductionsDeductionsView = mock(TextView.class);

    private TextView mockExpensesTitleView = mock(TextView.class);
    private TextView mockExpensesExpenseView = mock(TextView.class);

    private TextView mockIraRothTitleView = mock(TextView.class);
    private TextView mockIraRothBalanceView = mock(TextView.class);
    private TextView mockIraRothGrowthRateView = mock(TextView.class);
    private TextView mockIraRothAnnualContributionsView = mock(TextView.class);
    private TextView mockIraRothStartWithdrawalsAgeView = mock(TextView.class);
    private TextView mockIraRothNumberOfWithdrawalsView = mock(TextView.class);

    private TextView mockIraTraditionalTitleView = mock(TextView.class);
    private TextView mockIraTraditionalBalanceView = mock(TextView.class);
    private TextView mockIraTraditionalGrowthRateView = mock(TextView.class);
    private TextView mockIraTraditionalAnnualContributionsView = mock(TextView.class);
    private TextView mockIraTraditionalStartWithdrawalsAgeView = mock(TextView.class);
    private TextView mockIraTraditionalNumberOfWithdrawalsView = mock(TextView.class);

    private TextView mockPensionTitleView = mock(TextView.class);
    private TextView mockPensionStartingAgeView = mock(TextView.class);
    private TextView mockPensionMonthlyAmountView = mock(TextView.class);
    private TextView mockPensionInflationAdjustCheckBox = mock(CheckBox.class);

    private TextView mockPersonalTitleView = mock(TextView.class);
    private TextView mockPersonalSimulationDateView = mock(TextView.class);
    private TextView mockPersonalBirthDateView = mock(TextView.class);
    private TextView mockPersonalRetirementAgeView = mock(TextView.class);
    private TextView mockPersonalLifeExpectancyAgeView = mock(TextView.class);
    private TextView mockPersonalInflationView = mock(TextView.class);

    private TextView mockSalaryTitleView = mock(TextView.class);
    private TextView mockSalarySalaryView = mock(TextView.class);
    private TextView mockSalaryMeritIncreaseView = mock(TextView.class);

    private TextView mockSavingsTitleView = mock(TextView.class);
    private TextView mockSavingsBalanceView = mock(TextView.class);
    private TextView mockSavingsGrowthRateView = mock(TextView.class);

    private TextView mockSocialSecurityTitleView = mock(TextView.class);
    private TextView mockSocialSecurityStartingAgeView = mock(TextView.class);
    private TextView mockSocialSecurityMonthlyAmountView = mock(TextView.class);

    private TextView mockTaxesTitleView = mock(TextView.class);
    private TextView mockTaxesFederalTaxRateView = mock(TextView.class);
    private TextView mockTaxesStateTaxRateView = mock(TextView.class);

    private TestRoboActivity activity = new TestRoboActivity();
    private final TestUtil testUtil = new TestUtil();

    @Before
    public void setUp() throws Exception {

        initMocks(this);

        mockStatic(LayoutInflater.class);
        mockStatic(View.class);
        mockStatic(Context.class);
        mockStatic(MockContext.class);
        mockStatic(ViewGroup.class);
        mockStatic(LoginLab.class);
        mockStatic(TextView.class);

        testUtil.MockContextGetStringInputNames(mockContext);
        testUtil.MockContextGetStringInputHeaders(mockContext);
        testUtil.MockContextGetStringDatabaseIntegers(mockContext);
        testUtil.MockContextGetStringDatabaseReal(mockContext);

        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        when(LoginLab.getRealData(anyInt(), anyString(), anyString())).thenReturn("2.0");
        when(LoginLab.getIntegerData(anyInt(), anyString(), anyString())).thenReturn("2");

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
        when(mockView.findViewById(R.id.deductions_deductions)).thenReturn(mockDeductionsDeductionsView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockExpensesTitleView);
        when(mockView.findViewById(R.id.expenses_expense)).thenReturn(mockExpensesExpenseView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockIraRothTitleView);
        when(mockView.findViewById(R.id.roth_ira_balance)).thenReturn(mockIraRothBalanceView);
        when(mockView.findViewById(R.id.roth_ira_growth_rate)).thenReturn(mockIraRothGrowthRateView);
        when(mockView.findViewById(R.id.roth_ira_contributions)).thenReturn(mockIraRothAnnualContributionsView);
        when(mockView.findViewById(R.id.roth_ira_start_withdrawals_age)).thenReturn(mockIraRothStartWithdrawalsAgeView);
        when(mockView.findViewById(R.id.roth_ira_number_of_withdrawals)).thenReturn(mockIraRothNumberOfWithdrawalsView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockIraTraditionalTitleView);
        when(mockView.findViewById(R.id.traditional_ira_balance)).thenReturn(mockIraTraditionalBalanceView);
        when(mockView.findViewById(R.id.traditional_ira_growth_rate)).thenReturn(mockIraTraditionalGrowthRateView);
        when(mockView.findViewById(R.id.traditional_ira_contributions)).thenReturn(mockIraTraditionalAnnualContributionsView);
        when(mockView.findViewById(R.id.traditional_ira_start_withdrawals_age)).thenReturn(mockIraTraditionalStartWithdrawalsAgeView);
        when(mockView.findViewById(R.id.traditional_ira_number_of_withdrawals)).thenReturn(mockIraTraditionalNumberOfWithdrawalsView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockPensionTitleView);
        when(mockView.findViewById(R.id.pension_starting_age)).thenReturn(mockPensionStartingAgeView);
        when(mockView.findViewById(R.id.pension_monthly_amount)).thenReturn(mockPensionMonthlyAmountView);
        when(mockView.findViewById(R.id.pension_inflation_adjusted)).thenReturn(mockPensionInflationAdjustCheckBox);

        when(mockView.findViewById(R.id.title)).thenReturn(mockPersonalTitleView);
        when(mockView.findViewById(R.id.personal_simulation_date)).thenReturn(mockPersonalSimulationDateView);
        when(mockView.findViewById(R.id.personal_birth_date)).thenReturn(mockPersonalBirthDateView);
        when(mockView.findViewById(R.id.personal_retirement_age)).thenReturn(mockPersonalRetirementAgeView);
        when(mockView.findViewById(R.id.personal_life_expectancy_age)).thenReturn(mockPersonalLifeExpectancyAgeView);
        when(mockView.findViewById(R.id.personal_inflation)).thenReturn(mockPersonalInflationView);

        when(mockView.findViewById(R.id.title)).thenReturn(mockSalaryTitleView);
        when(mockView.findViewById(R.id.salary_salary)).thenReturn(mockSalarySalaryView);
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

        when(mock401kBalanceView.getText()).thenReturn("0.0");
        when(mock401kGrowthRateView.getText()).thenReturn("0.0");
        when(mock401kAnnualContributionsView.getText()).thenReturn("0.0");
        when(mock401kStartWithdrawalsAgeView.getText()).thenReturn("0");
        when(mock401kNumberOfWithdrawalsView.getText()).thenReturn("0");

        when(mock403bBalanceView.getText()).thenReturn("2.0");
        when(mock403bGrowthRateView.getText()).thenReturn("2.0");
        when(mock403bAnnualContributionsView.getText()).thenReturn("2.0");
        when(mock403bStartWithdrawalsAgeView.getText()).thenReturn("2");
        when(mock403bNumberOfWithdrawalsView.getText()).thenReturn("2");

        InputLab.getInstance(mockContext, activity.getSupportFragmentManager(), mockParent);
        account403B = InputLab.getAccount403b();
    }

    @After
    public void tearDown() throws Exception {
        mock403bBalanceView.setText("0.0");
        mock403bGrowthRateView.setText("0.0");
        mock403bAnnualContributionsView.setText("0.0");
        mock403bStartWithdrawalsAgeView.setText("0");
        mock403bNumberOfWithdrawalsView.setText("0");
    }

    @Test
    public void preCondition() throws Exception {

        assertNotNull(account403B);
    }

    @Test
    public void getBalance() throws Exception {

        Float balance = account403B.getBalance();

        assertThat(2.0f, is(balance));
    }

    @Test
    public void getGrowthRate() throws Exception {

        Float growthRate = account403B.getGrowthRate();

        assertThat(2.0, is(Double.valueOf(growthRate)));
    }

    @Test
    public void getContributions() throws Exception {

        Float contribution = account403B.getContributions();

        assertThat(2.0, is(Double.valueOf(contribution)));
    }

    @Test
    public void getStartWithdrawalsAge() throws Exception {

        Integer age = account403B.getStartWithdrawalsAge();

        assertThat(2, is(age));
    }

    @Test
    public void getNumberOfWithdrawals() throws Exception {

        Integer number = account403B.getNumberOfWithdrawals();

        assertThat(2, is(number));
    }

    @Test
    public void setBalance() throws Exception {

        account403B.setBalance("4.0");

        Float balance = account403B.getBalance();

        assertThat(2.0f, is(balance));
    }

    @Test
    public void setGrowthRate() throws Exception {

        account403B.setGrowthRate("4.0");

        Float growthRate = account403B.getGrowthRate();

        assertThat(2.0, is(Double.valueOf(growthRate)));
    }

    @Test
    public void setContributions() throws Exception {

        account403B.setContributions("4.0");

        Float contribution = account403B.getContributions();

        assertThat(2.0, is(Double.valueOf(contribution)));
    }

    @Test
    public void setStartWithdrawalsAge() throws Exception {

        account403B.setStartWithdrawalsAge("4");

        Integer age = account403B.getStartWithdrawalsAge();

        assertThat(2, is(age));
    }

    @Test
    public void setNumberOfWithdrawals() throws Exception {

        account403B.setNumberOfWithdrawals("4");

        Integer number = account403B.getNumberOfWithdrawals();

        assertThat(2, is(number));
    }

    @Test
    public void getView() throws Exception {

        View view = account403B.getView();

        assertNotNull(view);
    }
}
