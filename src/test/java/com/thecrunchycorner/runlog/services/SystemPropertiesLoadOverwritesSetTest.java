package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesLoadOverwritesSetTest {

    @Before
    public void setUp() throws Exception {
        SystemPropertiesFactory.loadSystemProperties();
    }

    @Test
    public void testSetFirstNotOverWrittenByLoader() {
        String key = "unit.test.value.preloaded";
        String value = "Pre-loaded test data";
        String newValue = "We change data here";

        SystemProperties.setProperty(key, newValue);

        assertThat(SystemProperties.get(key), is(value));
    }


    @After
    public void tearDown() throws Exception {

    }
}