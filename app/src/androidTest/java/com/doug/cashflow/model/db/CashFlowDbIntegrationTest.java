package com.doug.cashflow.model.db;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.doug.cashflow.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CashFlowDbIntegrationTest {
//    private static final String TAG = "CashFlowDbIntegrationTest";

    private static final String USER_USERNAME = "cashFlowDbTest";
    private static final String USER_PASSWORD = "cashFlowDbTest";

    private CashFlowDb cashFlowDb;

    private void insertUserForTheTest() {
        User user = new User();
        user.setEmail(USER_USERNAME);
        user.setPassword(USER_PASSWORD);

        cashFlowDb.insertUser(user);
    }

    private void deleteUserForTheTest() {
        User user;

        user = cashFlowDb.getUser(USER_USERNAME);

        cashFlowDb.deleteDates(user.getId());
        cashFlowDb.deleteRealData(user.getId());
        cashFlowDb.deleteIntegerData(user.getId());

        cashFlowDb.deleteUser(user);
    }

    @Before
    public void setUp(){
        User user;

        cashFlowDb = new CashFlowDb(InstrumentationRegistry.getTargetContext());
        cashFlowDb.setDatabasePointers();

        // insert the user into the database
        insertUserForTheTest();

        user = cashFlowDb.getUser(USER_USERNAME);
        cashFlowDb.fillInDataBase(user.getId());
        cashFlowDb.setDatabasePointers();
    }

    @After
    public void tearDown() {
        deleteUserForTheTest();

        cashFlowDb.closeDatabasePointers();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(cashFlowDb);
    }

    @Test
    public void userInTable() {
        String username = USER_USERNAME;

        assertThat(username, containsString(cashFlowDb.getUser(username).getEmail()));
    }

    @Test
    public void numberOfRecordsInHeadersTable() {
        Integer numRecords = cashFlowDb.getNumberOfRecordsInHeadersTable();

        assertThat(14, is(numRecords));
    }

    @Test
    public void numberOfRecordsInFieldsTable() {
        Integer numRecords = cashFlowDb.getNumberOfRecordsInFieldsTable();

        assertThat(17, is(numRecords)); // 17 = 19 total - minus the two dates
    }

    @Test
    public void numberOfRecordsInRealDataTable() {
        User user = cashFlowDb.getUser(USER_USERNAME);

        Integer numRecords = cashFlowDb.getNumberOfRecordsInRealDataTable(user.getId());

        Integer numberOfUsers = cashFlowDb.getNumberOfRecordsInUserTable();
        Integer numberOfRecordsInRealDataTable = cashFlowDb.getNumberOfRecordsInRealDataTable();
        Integer expected = numberOfRecordsInRealDataTable/numberOfUsers;

        assertThat(expected, is(numRecords));
    }

    @Test
    public void numberOfRecordsInIntegerDataTable() {
        User user = cashFlowDb.getUser(USER_USERNAME);

        Integer numRecords = cashFlowDb.getNumberOfRecordsInIntegerDataTable(user.getId());

        Integer numberOfUsers = cashFlowDb.getNumberOfRecordsInUserTable();
        Integer numberOfRecordsInIntegerDataTable = cashFlowDb.getNumberOfRecordsInIntegerDataTable();
        Integer expected = numberOfRecordsInIntegerDataTable/numberOfUsers;

        assertThat(expected, is(numRecords));
    }

    @Test
    public void numberOfRecordsInDatesTable() {
        User user = cashFlowDb.getUser(USER_USERNAME);

        Integer numRecords = cashFlowDb.getNumberOfRecordsInDatesTable(user.getId());

        Integer numberOfUsers = cashFlowDb.getNumberOfRecordsInUserTable();
        Integer numberOfRecordsInDatesTable = cashFlowDb.getNumberOfRecordsInDatesTable();
        Integer expected = numberOfRecordsInDatesTable/numberOfUsers;

        assertThat(expected, is(numRecords));
    }

    @Test
    public void getSimulationDateFromDatesTable() {
        User user = cashFlowDb.getUser(USER_USERNAME);

        // Get date
        Date expected = new Date();

        // Get the date just added to the database
        Date date = cashFlowDb.getDate(user.getId(), R.string.field_simulation_date);

        assertThat(expected.toString(), containsString(date.toString().substring(0, 10)));
    }

    @Test
    public void getBirthDateFromDatesTable() {
        User user = cashFlowDb.getUser(USER_USERNAME);

        // Get the date just added to the database
        Date date = cashFlowDb.getDate(user.getId(), R.string.field_birth_date);

        assertThat(date.toString(), containsString("1958"));
    }

    @Test
    public void get401kBalanceFromRealDataTable() {
        User user = cashFlowDb.getUser(USER_USERNAME);
        String header = InstrumentationRegistry.getTargetContext().getResources().getString(R.string.input_header_401k);
        String field = InstrumentationRegistry.getTargetContext().getResources().getString(R.string.field_balance);

        // Get the data just added to the database
        String data = cashFlowDb.getRealData(user.getId(), header, field);

        assertThat(data, containsString("370,000.0"));
    }

    @Test
    public void getPersonalRetirementAgeFromIntegerDataTable() {
        User user = cashFlowDb.getUser(USER_USERNAME);
        String header = InstrumentationRegistry.getTargetContext().getResources().getString(R.string.input_header_personal);
        String field = InstrumentationRegistry.getTargetContext().getResources().getString(R.string.field_retirement_age);

        // Get the date just added to the database
        String data = cashFlowDb.getIntegerData(user.getId(), header, field);

        assertThat(data, containsString("53"));
    }
}