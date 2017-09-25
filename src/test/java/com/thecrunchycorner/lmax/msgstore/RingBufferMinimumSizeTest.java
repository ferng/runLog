package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RingBufferMinimumSizeTest {


    @Before
    public void setup() {
    }


    @After
    public void tearDOWN() {

    }

    @Test
    public void Test() {
        RingBufferStore<Integer> buffer = new RingBufferStore<>(4);
        assertThat(buffer.size(), is(Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))));
    }
}
