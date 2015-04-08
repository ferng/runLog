package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SizeStaysConstantOnPutTest {

    private RingBuffer<Integer> buffer;
    private static final int bufferSize = 4;

    @Before
    public void setup() {
        buffer = new RingBuffer(bufferSize);
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
