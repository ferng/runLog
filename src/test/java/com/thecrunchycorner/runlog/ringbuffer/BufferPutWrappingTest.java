package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.enums.BufferType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferPutWrappingTest {

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
        Integer testInt1 = new Integer(23);

        for (int i = 0; i < BUFFER_SIZE; i++) {
            buffer.put(i, new Integer(i));
        }

        buffer.put(BUFFER_SIZE + 1, testInt1);

        assertThat(buffer.get(BUFFER_SIZE), is(testInt1));
        assertThat(buffer.get(0), is(testInt1));

    }
}
