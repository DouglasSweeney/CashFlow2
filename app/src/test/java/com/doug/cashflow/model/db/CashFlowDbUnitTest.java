/*
    Good example of mocking a database
 */

package com.doug.cashflow.model.db;

//import android.test.mock.MockContext;

import com.doug.cashflow.BuildConfig;

//import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CashFlowDb.class})
@Config(constants = BuildConfig.class)
public class CashFlowDbUnitTest {
    private final CashFlowDb cashFlowDb = mock(CashFlowDb.class);

//    private final MockContext mockContext = new MockContext();

    @Before
    public void setUp() throws Exception {

        initMocks(this);

        mockStatic(CashFlowDb.class);

        when(cashFlowDb.getNumberOfRecordsInHeadersTable()).thenReturn(28);
    }

/*
    @After
    public void tearDown() throws Exception {
    }
*/

    @Test
    public void preCondition() throws Exception {

        assertNotNull(cashFlowDb);
    }

    @Test
    public void checkFillInDataBase() throws Exception {
        Integer numRecords;

        cashFlowDb.fillInDataBase(CashFlowDb.MY_2017_doug_USER_ID);
        numRecords = cashFlowDb.getNumberOfRecordsInHeadersTable();

        assertThat(28, is(numRecords));
    }

}
