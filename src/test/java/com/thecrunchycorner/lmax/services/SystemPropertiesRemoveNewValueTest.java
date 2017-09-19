package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesRemoveNewValueTest {
    private String key = "unit.test.value.newvalue";
    private String value = "New key value pair";
    private String removedValue = "Undefined property";

    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
        SystemProperties.remove(key);
    }


    @Test
    public void test() {
        SystemProperties.setProperty(key, value);
        SystemProperties.remove(key);
        assertThat(SystemProperties.get(key), is(removedValue));
    }
}