package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesOverwriteSystemDefault {
    String key = "unit.test.value.systemdefault";
    String value = "Pre-loaded test data";

    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
        SystemProperties.refreshProperties();
    }


    @Test
    public void test() {
        assertThat(SystemProperties.get(key), is(value));

        value = "This value overwrites the system default";
        SystemProperties.setProperty(key, value);

        assertThat(SystemProperties.get(key), is(value));
    }
}