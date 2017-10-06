package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;

import org.junit.Before;
import org.junit.Test;

public class RingBufferSizeStaysConstantOnPutTest {

    private RingBufferStore<Integer> buffer;
    private final int BUFFER_SIZE = SystemProperties.getAsInt("threshold.buffer.minimum.size")
            .getAsInt();


    @Before
    public void setUp() {
        buffer = new RingBufferStore<>(BUFFER_SIZE);
    }


    @Test
    public void test() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            buffer.set(i, i);
        }

        buffer.set(9, 1);

        assertThat(buffer.size(), is(BUFFER_SIZE));
    }
}
