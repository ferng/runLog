package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesRefreshSystemDefaultValueNotResetTest {
    String key = "unit.test.value.systemdefault";
    String originalValue = "Pre-loaded test data";
    String newValue = "This value overwrites the one in the file";


    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
        SystemProperties.refreshProperties();
    }


    @Test
    public void test() {
        assertThat(SystemProperties.get(key), is(originalValue));

        SystemProperties.setProperty(key, newValue);
        assertThat(SystemProperties.get(key), is(newValue));

        SystemProperties.refreshProperties();
        assertThat(SystemProperties.get(key), is(newValue));
    }
}