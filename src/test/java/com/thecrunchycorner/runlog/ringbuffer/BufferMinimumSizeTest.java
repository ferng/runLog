package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.enums.BufferType;
import com.thecrunchycorner.runlog.services.SystemProperties;
import com.thecrunchycorner.runlog.services.SystemPropertiesFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferMinimumSizeTest {

    private RingBuffer<Integer> buffer;


    @Before
    public void setup() {
        SystemPropertiesFactory.loadSystemProperties();
    }


    @After
    public void tearDOWN() {

    }

    @Test
    public void Test() {
        buffer = new RingBuffer(4, BufferType.INPUT);
        assertThat(buffer.size(), is(Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"))));
    }
}
