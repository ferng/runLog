package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesSetCallsLoadTest {
    private String newKey = "unit.test.value.newvalue";
    private String newValue = "New newKey value pair";
    private String defaultKey = "unit.test.value.systemdefault";
    private String defaultValue = "Pre-loaded test data";

    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
        SystemProperties.remove(newKey);
    }


    @Test
    public void test() {
        SystemProperties.set(newKey, newValue);
        assertThat(SystemProperties.get(defaultKey), is(defaultValue));
    }
}