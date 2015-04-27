package com.thecrunchycorner.runlog.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertiesFactoryMultipleInitNotOverWritingChangesTest {
    String key = "unit.test.value.systemdefault";
    String value = "Pre-loaded test data";

    @Before
    public void setup() {
        SystemPropertiesFactory.loadSystemProperties();
    }

    @After
    public void tearDOWN() {
        SystemProperties.refreshProperties();
    }

    @Test
    public void Test() {

        assertThat(SystemProperties.get(key), is(value));

        SystemPropertiesFactory.loadSystemProperties();
        assertThat(SystemProperties.get(key), is(value));

        value = "This value overwrites the one in the file";
        SystemProperties.setProperty(key, value);
        assertThat(SystemProperties.get(key), is(value));

        SystemPropertiesFactory.loadSystemProperties();
        assertThat(SystemProperties.get(key), is(value));
    }
}
