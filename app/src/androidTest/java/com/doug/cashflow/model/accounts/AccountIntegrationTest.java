package com.doug.cashflow.model.accounts;

import com.doug.cashflow.model.system.ResultsDataNode;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AccountIntegrationTest extends Account {
	private static final double EPSILON = 1e-5;

	@Test
	public void initialize_BalanceSet() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(balance, getBalance(), EPSILON);
	}
	
	@Test
	public void initialize_SetGrowthRate() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(growthRate, getGrowthRate(), EPSILON);
	}
	
	@Test
	public void initialize_SetCurrentAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(currentAge, getCurrentAge(), EPSILON);
	}

	@Test
	public void initialize_SetCurrentYear() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(currentYear, getCurrentYear(), EPSILON);
	}

	@Test
	public void initialize_getDeathAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(deathAge, getDeathAge(), EPSILON);
	}
	
	@Test
	public void initialize_DataSize() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(deathAge-currentAge, getSize(), EPSILON);
	}
	
	@Test
	public void initialize_ListFirstNodeCurrentAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
		// Check first element
	    assertEquals(currentAge, getListOfValues().get(0).getAge(), EPSILON);
	}
		
	@Test
	public void initialize_ListLastNodeDeathAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
		// Check last element
	    assertEquals(deathAge-1, getListOfValues().get(deathAge-currentAge-1).getAge(), EPSILON);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void initialize_ListClearException() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
		clearListOfValues();
		
		// Check first element
	    getListOfValues().get(0);
	}
		
	@Test
	public void initialize_GetSmallestYear() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
		// Check last element
	    assertEquals(currentYear, getSmallestYear());
	}
	
	
	@Test
	public void initialize_GetSmallestYearWithoutList() {
		// Check no elements
	    assertEquals(0, getSmallestYear());
	}
	
	@Test
	public void initialize_GetLargestYear() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
		// Check last element
	    assertEquals(currentYear+(deathAge-currentAge-1), getLargestYear());
	}
	
	@Test
	public void initialize_GetLargestYearWithoutList() {
		// Check no elements
	    assertEquals(0, getLargestYear());
	}
	
	@Test
	public void initialize_convertAgeToYear_zeroAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(0, convertAgeToYear(0));
	}
	
	@Test
	public void initialize_convertAgeToYear_currentAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(currentYear, convertAgeToYear(currentAge));
	}
	
	@Test
	public void initialize_convertAgeToYear_DeathAgeMinusOne() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(currentYear+(deathAge-currentAge-1), convertAgeToYear(deathAge-1));
	}
	
	@Test
	public void initialize_convertAgeToYear_DeathAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(0, convertAgeToYear(deathAge));
	}
	
	@Test
	public void initialize_convertYearToAge_CurrentYear() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(currentAge, convertYearToAge(currentYear));
	}
	
	@Test
	public void initialize_convertYearToAge_currentYearMinusOne() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(0, convertYearToAge(currentYear-1));
	}
	
	@Test
	public void initialize_convertYearToAge_deathYearMinusOne() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals((deathAge-1), convertYearToAge(currentYear+(deathAge-currentAge-1)));
	}
	
	@Test
	public void initialize_convertYearToAge_invalidYear() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);
		
	    assertEquals(0, convertYearToAge(0));
	}
	
	@Test
	public void initialize_recomputeGrowthWithDeposit() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithDeposit(currentAge, 10.00);
		
	    assertEquals(10.0, getDeposits(currentYear), EPSILON);
	}
	
	@Test
	public void initialize_recomputeGrowthWithDeposit_getEndingValue() {
		double balance = 100.0;
		double growthRate = 0.10;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithDeposit(currentAge, 10.00);
		
	    assertEquals(121.0, getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void initialize_recomputeGrowthWithDeposit_getBeginningValue() {
		double balance = 100.0;
		double growthRate = 0.10;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithDeposit(currentAge, 10.00);
		
	    assertEquals(100.0, getBeginningValue(currentYear), EPSILON);
	}
	
	// Valid Year to getBeginningValue
	@Test
	public void initialize_recomputeGrowthWithDeposit_DeathYear() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithDeposit(currentAge, 10.00);
		
	    assertEquals(110.0, getBeginningValue(currentYear+(deathAge-currentAge-1)), EPSILON);
	}
	
	// Invalid year to getBeginningValue()
	@Test
	public void initialize_recomputeGrowthWithDeposit_DeathYearPlusOne() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithDeposit(currentAge, 10.00);
		
	    assertEquals(0.0, getBeginningValue(currentYear+(deathAge-currentAge)), EPSILON);
	}
//
	
	@Test
	public void initialize_recomputeGrowthWithWithdrawal() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(currentAge, 10.00);
		
	    assertEquals(10.0, getWithdrawals(currentYear), EPSILON);
	}
	
	@Test
	public void initialize_recomputeGrowthWithWithdrawal_getEndingValue() {
		double balance = 100.0;
		double growthRate = 0.10;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(currentAge, 10.00);

//		assertEquals(99.0, getEndingValue(currentYear), EPSILON);
		assertEquals((balance-10.0) + (balance-10.0)*growthRate, getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void initialize_recomputeGrowthWithWithdrawal_getBeginningValue() {
		double balance = 100.0;
		double growthRate = 0.10;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(currentAge, 10.00);
		
	    assertEquals(100.0, getBeginningValue(currentYear), EPSILON);
	}
	
	// Valid Year to getBeginningValue
	@Test
	public void initialize_recomputeGrowthWithWithdrawal_DeathYear() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(currentAge, 10.00);
		
	    assertEquals(90.0, getBeginningValue(currentYear+(deathAge-currentAge-1)), EPSILON);
	}
	
	// Invalid year to getBeginningValue()
	@Test
	public void initialize_recomputeGrowthWithWithdrawal_DeathYearPlusOne() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(currentAge, 10.00);
		
	    assertEquals(0.0, getBeginningValue(currentYear+(deathAge-currentAge)), EPSILON);
	}
	
	// Valid Year to getBeginningValue
	@Test
	public void initialize_recomputeGrowthWithWithdrawal_ZeroBalance() {
		double balance = 0.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(currentAge, 10.00);
		
	    assertEquals(0.0, getBeginningValue(currentYear+(deathAge-currentAge-1)), EPSILON);
	}
	
	// Valid Year to getBeginningValue
	@Test
	public void initialize_recomputeGrowthWithWithdrawal_InvalidYear() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(currentAge-1, 10.00);
		
	    assertEquals(0.0, getBeginningValue(currentYear-1), EPSILON);
	}
	
	// Valid Age to getBeginningValue
	@Test
	public void initialize_zeroBeginningValues_ValidAge() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		zeroBeginningValues(currentAge);
		
	    assertEquals(0.0, getBeginningValue(currentYear), EPSILON);
	}
	
	// Invalid Age to getBeginningValue (Below current age)
	@Test
	public void initialize_zeroBeginningValues_BelowCurrentAge() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		zeroBeginningValues(currentAge-1);
		
	    assertEquals(0.0, getBeginningValue(currentAge), EPSILON);
	}
	
	// Invalid Age to getBeginningValue (Above death age)
	@Test
	public void initialize_zeroBeginningValues_AboveDeathAge() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		zeroBeginningValues(currentAge-1);
		
	    assertEquals(0.0, getBeginningValue(currentYear-1), EPSILON);
	}
//
	// Valid Age to getEndingValue
	@Test
	public void initialize_zeroEndingValues_ValidAge() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		zeroEndingValues(currentAge);
		
	    assertEquals(0.0, getEndingValue(currentYear), EPSILON);
	}
	
	// Invalid Year to getEndingValue (Below current year)
	@Test
	public void initialize_zeroEndingValues_BelowCurrentAge() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		zeroEndingValues(currentAge-1);
		
	    assertEquals(0.0, getEndingValue(currentYear-1), EPSILON);
	}
	
	// Invalid Age to getBeginningValue (Above death age)
	@Test
	public void initialize_zeroEndingValues_AboveDeathAge() {
		double balance = 100.0;
		double growthRate = 0.0;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		zeroEndingValues(currentAge-1);
		
	    assertEquals(0.0, getEndingValue(currentYear-1), EPSILON);
	}
	
	@Test
	public void initialize_getDeposits() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithDeposit(currentAge+1, 10.00);
		
	    assertEquals(10.0, getDeposits(currentYear+1), EPSILON);
	}
	
	@Test
	public void initialize_getDeposits_DeathAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithDeposit(deathAge, 10.00);
		
	    assertEquals(0.0, getDeposits(currentYear+(deathAge-currentAge-1)), EPSILON);
	}
	
	@Test
	public void initialize_getDeposits_AboveDeathAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithDeposit(deathAge, 10.00);
		
	    assertEquals(0.0, getDeposits(currentYear+(deathAge-currentAge)), EPSILON);
	}
	@Test
	public void initialize_getWithdrawals() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(currentAge+1, 10.00);
		
	    assertEquals(10.0, getWithdrawals(currentYear+1), EPSILON);
	}
	
	@Test
	public void initialize_getWithdrawals_DeathAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(deathAge, 10.00);
		
	    assertEquals(0.0, getWithdrawals(currentYear+(deathAge-currentAge-1)), EPSILON);
	}
	
	@Test
	public void initialize_getWithdrawals_AboveDeathAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		recomputeGrowthWithWithdrawal(deathAge, 10.00);
		
	    assertEquals(0.0, getWithdrawals(currentYear+(deathAge-currentAge)), EPSILON);
	}
	
	@Test
	public void initialize_setZeroEndingValue_CurrentAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		setZeroEndingValue(currentAge);
		
	    assertEquals(0.0, getEndingValue(currentYear), EPSILON);
	}
	
	@Test
	public void initialize_setZeroEndingValue_CurrentAgePlusOne() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		setZeroEndingValue(currentAge+1);
		
	    assertEquals(0.0, getEndingValue(currentYear+1), EPSILON);
	}
	
	@Test
	public void initialize_setZeroEndingValue_DeathAgeMinusOne() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		setZeroEndingValue(deathAge-1);
		
	    assertEquals(0.0, getEndingValue(currentYear+(deathAge-currentAge-1)), EPSILON);
	}
	
	@Test
	public void initialize_setZeroEndingValue_DeathAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
		
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		setZeroEndingValue(deathAge);
		
	    assertEquals(0.0, getEndingValue(currentYear+(deathAge-currentAge)), EPSILON);
	}
	
	@Test
	public void initialize_getNodeBasedOnYear_CurrentAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
        ResultsDataNode node;
        
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

	    node = getNodeBasedOnYear(currentYear);
		
	    assertEquals(currentYear, node.getYear());
	}
	
	@Test
	public void initialize_getNodeBasedOnYear_CurrentAgePlusOne() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
        ResultsDataNode node;
        
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

	    node = getNodeBasedOnYear(currentYear+1);
		
	    assertEquals(currentYear+1, node.getYear());
	}
	
	@Test
	public void initialize_getNodeBasedOnYear_DeathAgeMinusOne() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
        ResultsDataNode node;
        
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

	    node = getNodeBasedOnYear(currentYear+(deathAge-currentAge-1));
		
	    assertEquals(currentYear+(deathAge-currentAge-1), node.getYear());
	}
	
	@Test
	public void initialize_getNodeBasedOnYear_DeathAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
        ResultsDataNode node;
        
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

	    node = getNodeBasedOnYear(currentYear+(deathAge-currentAge));
		
	    assertEquals(null, node);
	}
	
	@Test
	public void initialize_zeroAllEndingValues_CurrentAge() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;
        
		initialize(balance, growthRate, currentAge, currentYear, deathAge);

	    zeroEndingValues(currentAge);
		
	    assertEquals(0.0, getEndingValue(currentYear), EPSILON);
	}

	@Test
	public void initialize_zeroAllEndingValues() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;

		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		assertEquals(105.0, getEndingValue(currentYear), EPSILON);

		zeroEndingValues(currentAge);

		assertEquals(0.0, getEndingValue(currentYear), EPSILON);
	}

	@Test
	public void initialize_setZeroBeginningValue() {
		double balance = 100.0;
		double growthRate = 0.05;
		int currentAge = 57;
		int currentYear = 2016;
		int deathAge = 95;

		initialize(balance, growthRate, currentAge, currentYear, deathAge);

		assertEquals(100.0, getBeginningValue(currentYear), EPSILON);

		setZeroBeginningValue(currentAge);

		assertEquals(0.0, getBeginningValue(currentYear), EPSILON);
	}

    @Test
    public void initialize_withdraw() {
        double balance = 100.0;
        double growthRate = 0.05;
        int currentAge = 57;
        int currentYear = 2016;
        int deathAge = 95;

        initialize(balance, growthRate, currentAge, currentYear, deathAge);

        withdraw(currentAge, 10.0);

        assertEquals(94.50, getEndingValue(currentYear), EPSILON);
    }

    @Test
    public void initialize_withdraw_MoreThanInTheAccount() {
        double balance = 100.0;
        double growthRate = 0.05;
        int currentAge = 57;
        int currentYear = 2016;
        int deathAge = 95;

        initialize(balance, growthRate, currentAge, currentYear, deathAge);

        withdraw(currentAge, 110.0);

        assertThat(0.00, is(closeTo(getEndingValue(currentYear), EPSILON)));
    }

    @Test
    public void initialize_deposit() {
        double balance = 100.0;
        double growthRate = 0.05;
        int currentAge = 57;
        int currentYear = 2016;
        int deathAge = 95;

        initialize(balance, growthRate, currentAge, currentYear, deathAge);

        deposit(currentAge, 10.0);

        assertEquals(115.50, getEndingValue(currentYear), EPSILON);
    }

    @Test
    public void initialize_setBalance() {
        double balance = 100.0;
        double growthRate = 0.05;
        int currentAge = 57;
        int currentYear = 2016;
        int deathAge = 95;

        initialize(balance, growthRate, currentAge, currentYear, deathAge);

        setBalance(10.0);

        assertEquals(10.00, getBalance(), EPSILON);
    }

    @Test
    public void initialize_setGrowthRate() {
        double balance = 100.0;
        double growthRate = 0.05;
        int currentAge = 57;
        int currentYear = 2016;
        int deathAge = 95;

        initialize(balance, growthRate, currentAge, currentYear, deathAge);

        setGrowthRate(0.06);

        assertEquals(0.06, getGrowthRate(), EPSILON);
    }

    @Test
    public void initialize_PeriodicWithdrawals_CurrentAgeGreaterThanStartAge() {
        double balance = 100.0;
        double growthRate = 0.05;
        double annualContribution = 0.0;
        int currentAge = 57;
        int currentYear = 2016;
        int retirementAge = 53;
        int deathAge = 95;
        int startWithdrawalsAge = 55;
        int numberOfWithdrawals = 10;
        List<ResultsDataNode> values;
        Account401K account401K;

        account401K = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge,
                deathAge, startWithdrawalsAge, numberOfWithdrawals);

        values = account401K.getList();

//        assertThat(0.0, is(closeTo(values.get(0).getWithdrawal(), EPSILON)));
        assertThat(0.0, is(not(values.get(0).getWithdrawal())));
    }

    @Test
    public void initialize_PeriodicWithdrawals_CurrentAgeLessThanStartAge() {
        double balance = 100.0;
        double growthRate = 0.05;
        double annualContribution = 0.0;
        int currentAge = 57;
        int currentYear = 2016;
        int retirementAge = 53;
        int deathAge = 95;
        int startWithdrawalsAge = 59;
        int numberOfWithdrawals = 10;
        List<ResultsDataNode> values;
        Account401K account401K;

        account401K = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge,
                deathAge, startWithdrawalsAge, numberOfWithdrawals);

        values = account401K.getList();

        assertThat(0.0, is(closeTo(values.get(0).getWithdrawal(), EPSILON)));      // age 57
        assertThat(0.0, is(closeTo(values.get(1).getWithdrawal(), EPSILON)));      // age 58
        for (int i=0; i<numberOfWithdrawals; i++) {
            assertThat(0.0, is(not(closeTo(values.get(2+i).getWithdrawal(), EPSILON)))); // age 59 - age 68
        }
        assertThat(0.0, is(closeTo(values.get(2 + numberOfWithdrawals).getWithdrawal(), EPSILON)));      // age 69
    }

    @Test
    public void initialize_PeriodicWithdrawals_CurrentAgeEqualsStartAge() {
        double balance = 100.0;
        double growthRate = 0.05;
        double annualContribution = 0.0;
        int currentAge = 59;
        int currentYear = 2016;
        int retirementAge = 53;
        int deathAge = 95;
        int startWithdrawalsAge = 59;
        int numberOfWithdrawals = 10;
        List<ResultsDataNode> values;
        Account401K account401K;

        account401K = new Account401K(balance, growthRate, annualContribution, currentAge, currentYear, retirementAge,
                deathAge, startWithdrawalsAge, numberOfWithdrawals);

        values = account401K.getList();

        for (int i=0; i<numberOfWithdrawals; i++) {
            assertThat(0.0, is(not(closeTo(values.get(i).getWithdrawal(), EPSILON)))); // age 59 - age 68
        }
        assertThat(0.0, is(closeTo(values.get(numberOfWithdrawals).getWithdrawal(), EPSILON)));      // age 69
    }
}