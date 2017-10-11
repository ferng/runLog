package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;
import java.util.OptionalInt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RingBufferPutNoWrappingTest {
    private RingBufferStore<Integer> buffer;

    @Before
    public void setUp() {
        final OptionalInt bufferSize = SystemProperties.getAsInt("threshold.buffer.minimum.size");
        if (bufferSize.isPresent()) {
            buffer = new RingBufferStore<>(bufferSize.getAsInt());
        } else {
            Assert.fail();
        }
    }


    @Test
    public void test() {
        final Integer testInt = 74;
        buffer.set(0, testInt);

        assertThat(buffer.get(0), is(testInt));
    }
}
