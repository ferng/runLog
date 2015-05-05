package com.thecrunchycorner.runlog.ringbuffer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferPutWrappingTest {

    private RingBuffer<Integer> buffer;
    private int bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));


    @Before
    public void setup() {
        buffer = new RingBuffer(bufferSize);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        Integer testInt1 = 23;

        for (int i = 0; i < bufferSize; i++) {
            buffer.put(i, new Integer(i));
        }

        buffer.put(bufferSize + 1, testInt1);

        assertThat(buffer.get(bufferSize), is(testInt1));
        assertThat(buffer.get(0), is(testInt1));

    }
}
