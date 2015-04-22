package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.types.BufferType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferPutNoIndexWrappingTest {

    RingBuffer<Integer> buffer;


    @Before
    public void setup() {
        buffer = new RingBuffer(8, BufferType.INPUT);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        Integer testInt = new Integer(23);
        buffer.put(0, testInt);

        assertThat(buffer.get(0), is(testInt));
    }
}
