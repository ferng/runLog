package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.OptionalInt;
import org.junit.Test;

public class SystemPropertiesGetIntFromFileTest {
    @Test
    public void test() {
        final String key = "threshold.buffer.minimum.size";
        final int value = 16;

        assertThat(SystemProperties.getAsInt(key), is(OptionalInt.of(value)));
    }
}