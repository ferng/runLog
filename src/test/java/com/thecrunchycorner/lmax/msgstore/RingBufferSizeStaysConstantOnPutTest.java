package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;
import org.junit.Before;
import org.junit.Test;

public class RingBufferSizeStaysConstantOnPutTest {
    private static final int THRESHOLD_SIZE = SystemProperties.getThresholdBufferSize();
    private RingBufferStore<Integer> buffer;


    @Before
    public void setUp() {
        buffer = new RingBufferStore<>(THRESHOLD_SIZE);
    }


    @Test
    public void test() {
        for (int i = 0; i < THRESHOLD_SIZE; i++) {
            buffer.set(i, i);
        }

        buffer.set(THRESHOLD_SIZE + 1, 1);

        assertThat(buffer.size(), is(THRESHOLD_SIZE));
    }
}
