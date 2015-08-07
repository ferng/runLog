package com.thecrunchycorner.runlog.ringbufferprocessor;

import static org.junit.Assert.assertEquals;

import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.processors.ProcessorWorkflow;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.runlog.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProcPropertiesBuilderBufferTest {

    private RingBufferStore<Integer> buffer;
    private ProcProperties procProps;
    private int initialHead = 20;

    @Before
    public void setup() {
        buffer = new RingBufferStore(Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size")));
        ProcessorID trailProc = ProcessorID.IN_BUSINESS_PROCESSOR;
        ProcessorID leadProc = ProcessorWorkflow.getLeadProc(trailProc);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(trailProc)
                .setLeadProc(leadProc)
                .setInitialHead(initialHead)
                .createProcProperties();
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        assertEquals(procProps.getBuffer() instanceof RingBufferStore, Boolean.TRUE);
    }

}