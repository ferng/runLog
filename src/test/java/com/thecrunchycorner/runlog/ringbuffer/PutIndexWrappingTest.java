package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PutIndexWrappingTest {

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
        Integer testInt1 = new Integer(23);

        for (int i=0; i<bufferSize; i++) {
            buffer.put(i, new Integer(i));
        }

        buffer.put(bufferSize+1, testInt1);

        assertThat(buffer.get(bufferSize), is(testInt1));
        assertThat(buffer.get(0), is(testInt1));

    }
}
