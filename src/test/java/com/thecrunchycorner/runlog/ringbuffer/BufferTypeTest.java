package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.types.BufferType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferTypeTest {

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
        assertThat(buffer.getType(), is(BufferType.INPUT));
    }
}
