package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesOverwriteSystemDefault {

    @Before
    public void setUp() throws Exception {
        SystemPropertiesFactory.loadSystemProperties();
    }


    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testSetFirstNotOverWrittenByLoader() {
        String key = "unit.test.value.preloaded";
        String value = "As this is after should overwrite";

        SystemProperties.get(key);
        SystemProperties.setProperty(key, value);

        assertThat(SystemProperties.get(key), is(value));
    }
}