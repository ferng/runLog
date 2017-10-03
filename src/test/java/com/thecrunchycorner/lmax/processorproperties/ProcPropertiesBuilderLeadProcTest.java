package com.thecrunchycorner.lmax.processorproperties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;
import com.thecrunchycorner.lmax.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProcPropertiesBuilderLeadProcTest {

    private RingBufferStore<Integer> buffer;
    private ProcProperties procProps;
    private int initialHead = 20;

    @Before
    public void setup() {
        buffer = new RingBufferStore(Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size")));
        ProcessorId trailProc = ProcessorId.BUSINESS_PROCESSOR;
        ProcessorId leadProc = ProcessorWorkflow.getLeadProc(trailProc);

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
        assertThat(procProps.getLeadProc(), is(ProcessorId.IN_UNMARSHALL));
    }

}