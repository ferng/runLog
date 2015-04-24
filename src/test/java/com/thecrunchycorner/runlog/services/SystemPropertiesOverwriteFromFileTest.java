package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesOverwriteFromFileTest {
    String key = "unit.test.value.fromfile";
    String value = "Test data from properties file";

    @Before
    public void setUp() throws Exception {
        SystemPropertiesFactory.loadSystemProperties();
    }


    @After
    public void tearDown() throws Exception {
        value = "Test data from properties file";
        SystemProperties.setProperty(key, value);
    }


    @Test
    public void testSetter() {
        assertThat(SystemProperties.get(key), is(value));

        value = "This value overwrites the one in the file";
        SystemProperties.setProperty(key, value);

        assertThat(SystemProperties.get(key), is(value));
    }
}