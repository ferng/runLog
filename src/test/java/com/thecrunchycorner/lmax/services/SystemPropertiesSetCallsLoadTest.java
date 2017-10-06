package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import org.junit.After;
import org.junit.Test;

public class SystemPropertiesSetCallsLoadTest {
    private final static String NEW_KEY = "unit.test.value.newvalue";
    private final static String DEFAULT_VALUE = "16";

    @After
    public void tearDown() throws Exception {
        SystemProperties.remove(NEW_KEY);
    }


    @Test
    public void test() {
        final String newValue = "New NEW_KEY value pair";
        SystemProperties.set(NEW_KEY, newValue);
        final String defaultKey = "threshold.buffer.minimum.size";
        assertThat(SystemProperties.get(defaultKey), is(Optional.of(DEFAULT_VALUE)));
    }
}