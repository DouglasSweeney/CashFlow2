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

public class HeaderUnitTest {
    private Header  header = null;
    private int    id = 0;
    private String str = "";


    @Before
    public void Setup() {
        header = null;
    }

    @Test
    public void testConstructorNumberOne() {
        id = 1;
        str = "Header";

        header = new Header();

        assertThat(null, is(not(header)));
    }

    @Test
    public void testConstructorNumberTwo() {
        id = 1;
        str = "Header";

        header = new Header(id, str);

        assertThat(null, is(not(header)));
    }

    @Test
    public void setId() {
        id = 1;
        str = "Header";

        header = new Header();
        header.setId(id);

        assertThat(id, is(header.getId()));
    }

    @Test
    public void setHeader() {
        id = 1;
        str = "Header";

        header = new Header();
        header.setHeader(str);

        assertThat(str, containsString(header.getHeader()));
    }

    @Test
    public void getToStringId() {
        id = 1;
        str = "Header";

        header = new Header();
        header.setId(id);

        assertThat(header.toString(), containsString(" 1 "));
    }

    @Test
    public void getToStringHeader() {
        id = 1;
        str = "Header";

        header = new Header();
        header.setHeader(str);

        assertThat(header.toString(), containsString(" Header"));
    }
}
