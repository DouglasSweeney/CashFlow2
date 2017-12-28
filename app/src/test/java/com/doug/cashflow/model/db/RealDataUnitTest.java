package com.doug.cashflow.model.db;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Doug on 5/31/2017.
 */

public class RealDataUnitTest {
    private static final Double EPSILON = 0.0001;

    private Integer id = 0;
    private Integer user_id = 0;
    private String header = "";
    private Integer header_id = 0;
    private String field = "";
    private Integer field_id = 0;
    private Integer  value = -1;

    RealData realData ;

    @Before
    public void Setup() {
        realData = null;
    }

    @Test
    public void testConstructorNumberOne() {
        id = 1;

        realData = new RealData();

        assertThat(null, is(not(realData)));
    }

    @Test
    public void testConstructorNumberTwo() {

        realData = new RealData(id, user_id, header, header_id, field, field_id, 0.0);

        assertThat(null, is(not(realData)));
    }

    @Test
    public void testSetId() {
        id = 1;

        realData = new RealData();
        realData.setId(id);

        assertThat(id, is(realData.getId()));
    }

    @Test
    public void testSetIdWithOtherConstructor() {

        realData = new RealData(id, user_id, header, header_id, field, field_id, 0.0);

        assertThat(id, is(realData.getId()));
    }

    @Test
    public void testSetUserId() {

        realData = new RealData();
        realData.setUserId(user_id);

        assertThat(user_id, is(realData.getUserId()));
    }

    @Test
    public void testGetHeader() {

        realData = new RealData(id, user_id, header, header_id, field, field_id, 0.0);

        assertThat(header, containsString(realData.getHeader()));
    }

    @Test
    public void testGetHeaderId() {

        realData = new RealData(id, user_id, header, header_id, field, field_id, 0.0);

        assertThat(header_id, is(realData.getHeaderId()));
    }

    @Test
    public void testGetField() {

        realData = new RealData(id, user_id, header, header_id, field, field_id, 0.0);

        assertThat(field, containsString(realData.getField()));
    }

    @Test
    public void testGetFieldId() {

        realData = new RealData(id, user_id, header, header_id, field, field_id, 0.0);

        assertThat(field_id, is(realData.getFieldId()));
    }

    @Test
    public void testGetValue() {

        realData = new RealData(id, user_id, header, header_id, field, field_id, 1.0);

        assertThat(1.0, is(closeTo(realData.getValue(), EPSILON)));
    }
}
