package com.thecrunchycorner.lmax.processorproperties;

import static org.junit.Assert.assertEquals;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;
import com.thecrunchycorner.lmax.services.SystemProperties;

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
        ProcessorId trailProc = ProcessorId.BUSINESS_PROCESSOR;
        ProcessorId leadProc = ProcessorWorkflow.getLeadProc(trailProc);

        procProps = new ProcProperties.Builder()
                .setBuffer(buffer)
                .setProcessor(trailProc)
                .setLeadProc(leadProc)
                .setHead(initialHead)
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