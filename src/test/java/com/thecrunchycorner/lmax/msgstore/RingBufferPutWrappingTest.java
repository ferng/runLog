package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;
import java.util.OptionalInt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RingBufferPutWrappingTest {

    private RingBufferStore<Integer> buffer;
    private int bufferSize;

    @Before
    public void setUp() {
        final OptionalInt bufferSizeOpt = SystemProperties.getAsInt("threshold.buffer.minimum" +
                ".size");
        if (bufferSizeOpt.isPresent()) {
            bufferSize = bufferSizeOpt.getAsInt();
            buffer = new RingBufferStore<>(bufferSizeOpt.getAsInt());
        } else {
            Assert.fail();
        }
    }


    @Test
    public void test() {
        final Integer testInt1 = -1;

        for (int i = 0; i < bufferSize; i++) {
            buffer.set(i, i);
        }

        buffer.set(bufferSize, testInt1);

        assertThat(buffer.get(bufferSize), is(testInt1));
        assertThat(buffer.get(0), is(testInt1));
    }
}
