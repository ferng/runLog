package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;

import java.util.OptionalInt;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RingBufferMinimumSizeTest {


    @Before
    public void setUp() {
    }


    @After
    public void tearDown() {
    }

    @Test
    public void test() {
        RingBufferStore<Integer> buffer = new RingBufferStore<>(4);
        OptionalInt value = SystemProperties.getAsInt("threshold.buffer.minimum.size");
        if (value.isPresent()) {
            assertThat(buffer.size(), is(value.getAsInt()));
        } else {
            Assert.fail();
        }
    }
}
