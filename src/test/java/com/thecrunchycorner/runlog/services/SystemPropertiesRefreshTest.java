package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesRefreshTest {
    String key = "unit.test.value.fromfile";
    String originalValue = "Test data from properties file";
    String newValue = "This value overwrites the one in the file";


    @Before
    public void setUp() throws Exception {
        SystemPropertiesFactory.loadSystemProperties();
    }


    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testSetter() {
        assertThat(SystemProperties.get(key), is(originalValue));

        SystemProperties.setProperty(key, newValue);

        assertThat(SystemProperties.get(key), is(newValue));

        SystemProperties.refreshProperties();

        assertThat(SystemProperties.get(key), is(originalValue));
    }
}