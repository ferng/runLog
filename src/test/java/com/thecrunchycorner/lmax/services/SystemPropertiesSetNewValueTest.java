package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import org.junit.After;
import org.junit.Test;

public class SystemPropertiesSetNewValueTest {
    private final static String KEY = "unit.test.value.newvalue";


    @After
    public void tearDown() throws Exception {
        SystemProperties.remove(KEY);
    }


    @Test
    public void test() {
        final String value = "New KEY value pair";
        SystemProperties.set(KEY, value);
        assertThat(SystemProperties.get(KEY), is(Optional.of(value)));
    }
}