package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.OptionalInt;
import org.junit.Test;

public class SystemPropertiesGetNonIntAsIntTest {
    @Test
    public void test() {
        final String key = "unit.test.value.systemdefault";

        assertThat(SystemProperties.getAsInt(key), is(OptionalInt.empty()));
    }
}