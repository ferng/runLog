package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesRefreshSystemDefaultValueNotResetTest {
    private String key = "unit.test.value.systemdefault";
    private String originalValue = "Pre-loaded test data";
    private String newValue = "This value overwrites the system default";


    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
        SystemProperties.refreshProperties();
    }


    @Test
    public void test() {
        SystemProperties.setProperty(key, newValue);

        SystemProperties.refreshProperties();
        assertThat(SystemProperties.get(key), is(newValue));
    }
}