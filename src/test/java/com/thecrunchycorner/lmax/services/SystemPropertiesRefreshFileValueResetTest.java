package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import org.junit.After;
import org.junit.Test;

public class SystemPropertiesRefreshFileValueResetTest {
    @After
    public void tearDown() throws Exception {
        SystemProperties.refreshProperties();
    }


    @Test
    public void test() {
        final String newValue = "22";
        final String key = "threshold.buffer.minimum.size";
        SystemProperties.set(key, newValue);

        SystemProperties.refreshProperties();
        final String originalValue = "16";
        assertThat(SystemProperties.get(key), is(Optional.of(originalValue)));
    }
}