package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesGetFromFileTest {

    @Before
    public void setUp() throws Exception {
        SystemPropertiesFactory.loadSystemProperties();
    }


    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testSetter() {
        String key = "unit.test.value.fromfile";
        String value = "Test data from properties file";

        assertThat(SystemProperties.get(key), is(value));
    }
}