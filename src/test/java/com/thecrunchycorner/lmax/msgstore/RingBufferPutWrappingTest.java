package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;
import org.junit.Before;
import org.junit.Test;

public class RingBufferPutWrappingTest {

    private RingBufferStore<Integer> buffer;
    //if the test crashes because there is no int then it's a failure I'm happy to highlight
    private final int BUFFER_SIZE = SystemProperties.getAsInt("threshold.buffer.minimum.size")
            .getAsInt();


    @Before
    public void setUp() {
        buffer = new RingBufferStore<>(BUFFER_SIZE);
    }


    @Test
    public void test() {
        final Integer testInt1 = 23;

        for (int i = 0; i < BUFFER_SIZE; i++) {
            buffer.set(i, i);
        }

        buffer.set(BUFFER_SIZE, testInt1);

        assertThat(buffer.get(BUFFER_SIZE), is(testInt1));
        assertThat(buffer.get(0), is(testInt1));

    }
}
