package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;
import org.junit.Before;
import org.junit.Test;

public class RingBufferPutNoWrappingTest {
    private static final int THRESHOLD_SIZE = SystemProperties.getThresholdBufferSize();
    private RingBufferStore<Integer> buffer;


    @Before
    public void setUp() {
        buffer = new RingBufferStore<>(THRESHOLD_SIZE);
    }


    @Test
    public void test() {
        final Integer testInt = 74;
        buffer.set(0, testInt);

        assertThat(buffer.get(0), is(testInt));
    }
}
