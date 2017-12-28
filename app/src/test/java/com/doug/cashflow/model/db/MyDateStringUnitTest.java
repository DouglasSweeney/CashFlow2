package com.doug.cashflow.model.db;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created by Doug on 5/31/2017.
 */

public class MyDateStringUnitTest {
    private Integer id;
    private Integer user_id;
    private String  field;
    private String  date;

    MyDateString myDateString;

    @Before
    public void Setup() {
        myDateString = null;

        id = 1;
        user_id = 2;
        field = "field";
        date = "01/01/1970";

    }

    @Test
    public void testConstructorNumberOne() {

        myDateString = new MyDateString();

        assertThat(null, is(not(myDateString)));
    }

    @Test
    public void testConstructorNumberTwo() {

        myDateString = new MyDateString(id, user_id, field, date);

        assertThat(null, is(not(myDateString)));
    }

    @Test
    public void testGetId() {

        myDateString = new MyDateString(id, user_id, field, date);

        assertThat(id, is(myDateString.getId()));
    }

    @Test
    public void testGetUserId() {

        myDateString = new MyDateString(id, user_id, field, date);

        assertThat(user_id, is(myDateString.getUserId()));
    }

    @Test
    public void testGetField() {

        myDateString = new MyDateString(id, user_id, field, date);

        assertThat(field, containsString(myDateString.getField()));
    }

    @Test
    public void testGetDate() {

        myDateString = new MyDateString(id, user_id, field, date);

        assertThat(date, containsString(myDateString.getDate()));
    }

    @Test
    public void getToStringId() {
        id = 1;

        myDateString = new MyDateString(id, user_id, field, date);

        assertThat(myDateString.toString(), containsString(" 1 "));
    }

    @Test
    public void getToStringDate() {
        date = "01/01/1980";

        myDateString = new MyDateString(id, user_id, field, date);

        assertThat(myDateString.toString(), containsString(date));
    }
}
