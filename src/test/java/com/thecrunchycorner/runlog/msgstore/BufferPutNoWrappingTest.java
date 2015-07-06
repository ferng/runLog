package com.thecrunchycorner.runlog.msgstore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferPutNoWrappingTest {

    private RingBufferStore<Integer> buffer;

    @Before
    public void setup() {
        buffer = new RingBufferStore(Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size")));
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        Integer testInt = 74;
        buffer.set(0, testInt);

        assertThat(buffer.get(0), is(testInt));
    }
}
