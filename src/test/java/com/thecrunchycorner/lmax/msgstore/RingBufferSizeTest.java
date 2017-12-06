package com.thecrunchycorner.lmax.msgstore;

import static org.junit.Assert.assertEquals;

import com.thecrunchycorner.lmax.services.SystemProperties;
import org.junit.Test;

public class RingBufferSizeTest {
    private static final int THRESHOLD_SIZE = SystemProperties.getThresholdBufferSize();

    @Test
    public void smallerThanThreshold() {
        final RingBuffer<Integer> buffer = new RingBuffer<>(4);
        assertEquals(THRESHOLD_SIZE, buffer.size());
    }

    @Test
    public void sameAsThreshold() {
        final RingBuffer<Integer> buffer = new RingBuffer<>(8);
        assertEquals(THRESHOLD_SIZE, buffer.size());
    }

    @Test
    public void largerThanThreshold() {
        final RingBuffer<Integer> buffer = new RingBuffer<>(99);
        assertEquals(99, buffer.size());
    }


}
