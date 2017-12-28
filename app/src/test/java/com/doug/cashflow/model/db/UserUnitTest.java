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

public class UserUnitTest {
    private int    id = 1;
    private String email = "username";
    private String password = "password";

    User user;

    @Before
    public void Setup() {
        user = null;
    }

    @Test
    public void testConstructorNumberOne() {
        user = new User();

        assertThat(null, is(not(user)));
    }

    @Test
    public void testConstructorNumberTwo() {
        user = new User(id, email, password);

        assertThat(null, is(not(user)));
    }

    @Test
    public void testGetId() {
        user = new User(id, email, password);

        assertThat(id, is(user.getId()));
    }

    @Test
    public void testGetEmail() {
        user = new User(id, email, password);

        assertThat(email, containsString(user.getEmail()));
    }

    @Test
    public void testGetPassword() {
        user = new User(id, email, password);

        assertThat(password, containsString(user.getPassword()));
    }
    @Test
    public void getToStringId() {
        id = 1;
        user = new User(id, email, password);

        assertThat(user.toString(), containsString(" 1"));
    }

    @Test
    public void getToStringGetEmail() {
        user = new User(id, email, password);

        assertThat(user.toString(), containsString(email));
    }

    @Test
    public void getToStringGetPassword() {
        user = new User(id, email, password);

        assertThat(user.toString(), containsString(password));
    }}
