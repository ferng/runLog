package com.thecrunchycorner.runlog.ringbuffer;

import com.thecrunchycorner.runlog.ringbuffer.enums.BufferType;
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


    @Test(expected=IllegalArgumentException.class)
    public void Test() {
        new RingBuffer(4, BufferType.INPUT);
    }
}
