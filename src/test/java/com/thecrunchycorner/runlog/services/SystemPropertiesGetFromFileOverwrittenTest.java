package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesGetFromFileOverwrittenTest {

    @Before
    public void setUp() throws Exception {
        SystemPropertiesFactory.loadSystemProperties();
    }


    @Test
    public void testSetter() {
        String key = "unit.test.value.fromfile";
        String value = "Test data from properties file not loaded";

        assertThat(SystemProperties.get(key), is(value));

        value = "This value overwrites the one in the file";
        SystemProperties.setProperty(key, value);

        assertThat(SystemProperties.get(key), is(value));
    }


    @After
    public void tearDown() throws Exception {

    }
}