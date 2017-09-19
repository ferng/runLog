package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferPutWrappingTest {

    private RingBufferStore<Integer> buffer;
    private int bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));


    @Before
    public void setup() {
        buffer = new RingBufferStore(bufferSize);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        Integer testInt1 = 23;

        for (int i = 0; i < bufferSize; i++) {
            buffer.set(i, new Integer(i));
        }

        buffer.set(bufferSize + 1, testInt1);

        assertThat(buffer.get(bufferSize), is(testInt1));
        assertThat(buffer.get(0), is(testInt1));

    }
}
