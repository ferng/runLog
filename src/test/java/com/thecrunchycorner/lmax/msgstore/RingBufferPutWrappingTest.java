package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;
import org.junit.Before;
import org.junit.Test;

public class RingBufferPutWrappingTest {
    private static final int THRESHOLD_SIZE = SystemProperties.getThresholdBufferSize();
    private RingBufferStore<Integer> buffer;


    @Before
    public void setUp() {
        buffer = new RingBufferStore<>(THRESHOLD_SIZE);
    }


    @Test
    public void test() {
        final Integer testInt1 = -1;

        for (int i = 0; i < THRESHOLD_SIZE; i++) {
            buffer.set(i, i);
        }

        buffer.set(THRESHOLD_SIZE, testInt1);

        assertThat(buffer.get(THRESHOLD_SIZE), is(testInt1));
        assertThat(buffer.get(0), is(testInt1));
    }
}
