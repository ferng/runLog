package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.enums.BufferType;
import com.thecrunchycorner.runlog.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferSizeStaysConstantOnPutTest {

    private RingBuffer<Integer> buffer;
    private int bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));


    @Before
    public void setup() {
        buffer = new RingBuffer(bufferSize, BufferType.INPUT);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        for (int i=0; i<bufferSize; i++) {
            buffer.put(i, new Integer(i));
        }

        buffer.put(9, new Integer(1));

        assertThat(buffer.size(), is(bufferSize));
    }
}
