package com.thecrunchycorner.lmax.processorproperties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.thecrunchycorner.lmax.msgstore.RingBuffer;
import com.thecrunchycorner.lmax.services.SystemProperties;
import com.thecrunchycorner.lmax.workflow.ProcessorPriorities;
import org.junit.Before;
import org.junit.Test;

public class ProcPropertiesBuilderBufferTest {
    private ProcProperties procProps;
    private int initialBufferSize = SystemProperties.getThresholdBufferSize();
    private int initialHead = SystemProperties.getThresholdBufferSize() - 2;

    @Before
    public void setUp() {
        final RingBuffer<Integer> buffer = new RingBuffer<>(initialBufferSize);
        final BufferReaderWriter<Object> handler = new BufferReaderWriter<>();

        procProps = new ProcProperties.Builder()
                .setProcessorPriorities(ProcessorPriorities.BUSINESS_PROCESSOR)
                .setBuffer(buffer)
                .setHandler(handler)
                .setInitialHead(initialHead)
                .createProcProperties();
    }


    @Test
    public void testProcessorId() {
        final ProcessorPriorities procId = procProps.getProcessorPriorities();
        assertTrue(procId != null);
        assertEquals(ProcessorPriorities.BUSINESS_PROCESSOR, procId);
    }


    @Test
    public void testBuffer() {
        final RingBuffer actualBuffer = procProps.getBuffer();
        assertTrue(actualBuffer != null);
        assertEquals(initialBufferSize, actualBuffer.size());
    }


    @Test
    public void testHandler() {
        final BufferHandler actualHandler = procProps.getBufferHandler();
        assertTrue(actualHandler != null);
        assertTrue(actualHandler instanceof BufferReaderWriter);
    }


    @Test
    public void testHead() {
        final int head = procProps.getHead();
        assertEquals(initialHead, head);
    }


    @Test
    public void testPos() {
        final int pos = procProps.getPos();
        assertEquals(0, pos);
    }

}