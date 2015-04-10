package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PutNoIndexWrappingTest {

    RingBuffer<Integer> buffer;


    @Before
    public void setup() {
        buffer = new RingBuffer(8);
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
