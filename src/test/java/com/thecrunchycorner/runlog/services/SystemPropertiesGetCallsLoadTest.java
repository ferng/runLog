package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesGetCallsLoadTest {
    private String defaultKey = "unit.test.value.systemdefault";
    private String defaultValue = "Pre-loaded test data";

    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void test() {
        assertThat(SystemProperties.get(defaultKey), is(defaultValue));
    }
}