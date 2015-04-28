package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.enums.BufferType;
import com.thecrunchycorner.runlog.services.SystemProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferPutNoWrappingTest {

    private RingBuffer<Integer> buffer;

    @Before
    public void setup() {
        buffer = new RingBuffer(Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size")), BufferType.INPUT);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        Integer testInt = 74;
        buffer.put(0, testInt);

        assertThat(buffer.get(0), is(testInt));
    }
}
