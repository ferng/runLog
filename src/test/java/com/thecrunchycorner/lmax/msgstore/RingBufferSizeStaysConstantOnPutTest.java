package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RingBufferSizeStaysConstantOnPutTest {

    private RingBufferStore<Integer> buffer;
    private int bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));


    @Before
    public void setup() {
        buffer = new RingBufferStore<>(bufferSize);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        for (int i = 0; i < bufferSize; i++) {
            buffer.set(i, i);
        }

        buffer.set(9, 1);

        assertThat(buffer.size(), is(bufferSize));
    }
}
