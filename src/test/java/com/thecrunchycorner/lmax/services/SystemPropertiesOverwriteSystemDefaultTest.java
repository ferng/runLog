package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesOverwriteSystemDefaultTest {
    private String key = "threshold.buffer.minimum.size";
    private String value = "8";

    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
        SystemProperties.refreshProperties();
        SystemProperties.set("unit.test.value.systemdefault", "Pre-loaded test data");
    }


    @Test
    public void test() {
        value = "This value overwrites the system default";
        SystemProperties.set(key, value);

        assertThat(SystemProperties.get(key), is(value));
    }
}