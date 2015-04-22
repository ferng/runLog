package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.types.BufferType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferSizeStaysConstantOnPutTest {

    private RingBuffer<Integer> buffer;
    private static final int BUFFER_SIZE = 4;


    @Before
    public void setup() {
        buffer = new RingBuffer(BUFFER_SIZE, BufferType.INPUT);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        for (int i=0; i<BUFFER_SIZE; i++) {
            buffer.put(i, new Integer(i));
        }

        buffer.put(9, new Integer(1));

        assertThat(buffer.size(), is(BUFFER_SIZE));
    }
}
