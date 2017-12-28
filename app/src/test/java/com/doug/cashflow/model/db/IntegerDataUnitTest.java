package com.doug.cashflow.model.db;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Doug on 5/31/2017.
 */

public class IntegerDataUnitTest {
    private Integer id = 0;
    private Integer user_id = 0;
    private String header = "";
    private Integer header_id = 0;
    private String field = "";
    private Integer field_id = 0;
    private Integer  value = -1;

    IntegerData integerData ;

    @Before
    public void Setup() {
        integerData = null;
    }

    @Test
    public void testConstructorNumberOne() {
        id = 1;

        integerData = new IntegerData();

        assertThat(null, is(not(integerData)));
    }

    @Test
    public void testConstructorNumberTwo() {

        integerData = new IntegerData(id, user_id, header, header_id, field, field_id, 0);

        assertThat(null, is(not(integerData)));
    }

    @Test
    public void testSetId() {
        id = 1;

        integerData = new IntegerData();
        integerData.setId(id);

        assertThat(id, is(integerData.getId()));
    }

    @Test
    public void testSetIdWithOtherConstructor() {

        integerData = new IntegerData(id, user_id, header, header_id, field, field_id, 0);

        assertThat(id, is(integerData.getId()));
    }

    @Test
    public void testSetUserId() {

        integerData = new IntegerData();
        integerData.setUserId(user_id);

        assertThat(user_id, is(integerData.getUserId()));
    }

    @Test
    public void testGetHeader() {

        integerData = new IntegerData(id, user_id, header, header_id, field, field_id, 0);

        assertThat(header, containsString(integerData.getHeader()));
    }

    @Test
    public void testGetHeaderId() {

        integerData = new IntegerData(id, user_id, header, header_id, field, field_id, 0);

        assertThat(header_id, is(integerData.getHeaderId()));
    }

    @Test
    public void testGetField() {

        integerData = new IntegerData(id, user_id, header, header_id, field, field_id, 0);

        assertThat(field, containsString(integerData.getField()));
    }

    @Test
    public void testGetFieldId() {

        integerData = new IntegerData(id, user_id, header, header_id, field, field_id, 0);

        assertThat(field_id, is(integerData.getFieldId()));
    }

    @Test
    public void testGetValue() {

        integerData = new IntegerData(id, user_id, header, header_id, field, field_id, 1);

        assertThat(1, is(integerData.getValue()));
    }
}
