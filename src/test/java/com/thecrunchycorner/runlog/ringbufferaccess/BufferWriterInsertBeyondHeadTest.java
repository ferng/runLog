package com.thecrunchycorner.runlog.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbuffer.types.AccessStatus;
import com.thecrunchycorner.runlog.ringbuffer.types.BufferType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferWriterInsertBeyondHeadTest {

    private RingBuffer<Integer> buffer;
    private static final int BUFFER_SIZE = 4;

    private  BufferWriter writer;
    private BufferReader reader;

    @Before
    public void setup() {
        buffer = new RingBuffer(BUFFER_SIZE, BufferType.INPUT);
        writer = new BufferWriter(buffer);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        for (int i=0; i <= BUFFER_SIZE - 2; i++) {
            writer.write(i);
        }

        assertThat(writer.write("hello"), is(AccessStatus.HEADER_REACHED));
    }

}