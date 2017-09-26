package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesGetSystemDefaultTest {

    @Before
    public void setUp() throws Exception {
    }


    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void test() {
        String key = "threshold.buffer.minimum.size";
        String value = "8";

        assertThat(SystemProperties.get(key), is(value));
    }
}