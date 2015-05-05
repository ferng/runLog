package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferMinimumSizeTest {

    private RingBuffer<Integer> buffer;


    @Before
    public void setup() {
    }


    @After
    public void tearDOWN() {

    }

    @Test
    public void Test() {
        buffer = new RingBuffer(4);
        assertThat(buffer.size(), is(Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))));
    }
}
