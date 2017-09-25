package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesRemoveTest {
    private String newKey = "unit.test.value.newvalue";
    private String newValue = "New newKey value pair";
    private String undefValue = "Undefined property";


    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void test() {
        SystemProperties.set(newKey, newValue);
        SystemProperties.remove(newKey);

        assertThat(SystemProperties.get(newKey), is(undefValue));
    }
}