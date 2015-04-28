package com.thecrunchycorner.runlog.ringbufferprocessor;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbuffer.enums.BufferType;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import com.thecrunchycorner.runlog.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProcPropertiesLeadProcTest {

    private RingBuffer<Integer> buffer;
    private ProcProperties procProps;
    private int initialHead = 20;

    @Before
    public void setup() {
        buffer = new RingBuffer(Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size")), BufferType.INPUT);
        procProps = new ProcProperties(buffer, ProcessorType.BUSINESS_PROCESSOR, ProcessorType.INPUT_PROCESSOR, initialHead);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        assertThat(procProps.getLeadProc(), is(ProcessorType.INPUT_PROCESSOR));
    }

}