package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesRefreshFileValueResetTest {
    private String key = "unit.test.value.fromfile";
    private String originalValue = "Test data from properties file";
    private String newValue = "This value overwrites the one in the file";


    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
        SystemProperties.refreshProperties();
        SystemProperties.setProperty("unit.test.value.systemdefault", "Pre-loaded test data");
    }


    @Test
    public void test() {
        SystemProperties.setProperty(key, newValue);

        SystemProperties.refreshProperties();
        assertThat(SystemProperties.get(key), is(originalValue));
    }
}