package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesGetUndefinedTest {

    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void test() {
        String key = "unit.test.value.undefined";
        String value = "Undefined property";

        assertThat(SystemProperties.get(key), is(value));
    }
}