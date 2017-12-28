package com.doug.cashflow.model.system;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class InputUnitTest {
    private Input input;

    @Test
    public void testAddedForConstructor() {
        input = new Input();

        assertNotNull(input);
    }

    @Test
    public void testAddedForSetHeader() {
        String header = "Header";
        input = new Input();

        input.setHeader(header);

        assertThat(header, containsString(input.getHeader()));
    }
}
