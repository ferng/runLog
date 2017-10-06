package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import org.junit.After;
import org.junit.Test;

public class SystemPropertiesRefreshFileValueRemoveTest {
    @After
    public void tearDown() throws Exception {
        SystemProperties.refreshProperties();
    }


    @Test
    public void test() {
        final String key = "new.property.value";
        final String value = "hello";
        SystemProperties.set(key, value);
        assertThat(SystemProperties.get(key), is(Optional.of(value)));

        SystemProperties.refreshProperties();
        assertThat(SystemProperties.get(key), is(Optional.empty()));
    }
}