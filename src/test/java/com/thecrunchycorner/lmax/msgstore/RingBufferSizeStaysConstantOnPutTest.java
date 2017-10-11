package com.thecrunchycorner.lmax.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.services.SystemProperties;

import java.util.OptionalInt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RingBufferSizeStaysConstantOnPutTest {

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
        for (int i = 0; i < bufferSize; i++) {
            buffer.set(i, i);
        }

        buffer.set(bufferSize+1, 1);

        assertThat(buffer.size(), is(bufferSize));
    }
}
