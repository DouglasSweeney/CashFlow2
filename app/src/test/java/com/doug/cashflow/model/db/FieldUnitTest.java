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

public class FieldUnitTest {
    private Field  field = null;
    private int    id = 0;
    private String str = "";


    @Before
    public void Setup() {
        field = null;
    }

    @Test
    public void testConstructorNumberOne() {
        id = 1;
        str = "Field";

        field = new Field();

        assertThat(null, is(not(field)));
    }

    @Test
    public void testConstructorNumberTwo() {
        id = 1;
        str = "Field";

        field = new Field(id, str);

        assertThat(null, is(not(field)));
    }

    @Test
    public void setId() {
        id = 1;
        str = "Field";

        field = new Field();
        field.setId(id);

        assertThat(id, is(field.getId()));
    }

    @Test
    public void setField() {
        id = 1;
        str = "Field";

        field = new Field();
        field.setField(str);

        assertThat(str, containsString(field.getField()));
    }

    @Test
    public void getToStringId() {
        id = 1;
        str = "Field";

        field = new Field();
        field.setId(id);

        assertThat(field.toString(), containsString(" 1 "));
    }

    @Test
    public void getToStringField() {
        id = 1;
        str = "Field";

        field = new Field();
        field.setField(str);

        assertThat(field.toString(), containsString(" Field"));
    }
}
