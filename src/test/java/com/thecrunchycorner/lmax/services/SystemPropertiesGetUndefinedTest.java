package com.thecrunchycorner.lmax.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import org.junit.Test;

public class SystemPropertiesGetUndefinedTest {
    @Test
    public void test() {
        final String key = "unit.test.value.undefined";
        final Optional value = Optional.empty();

        assertThat(SystemProperties.get(key), is(value));
    }
}