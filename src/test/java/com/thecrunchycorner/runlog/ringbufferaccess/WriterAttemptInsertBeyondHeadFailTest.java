package com.thecrunchycorner.runlog.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.processors.ProcessorWorkflow;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder;
import com.thecrunchycorner.runlog.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WriterAttemptInsertBeyondHeadFailTest {

    private RingBufferStore<Integer> buffer;
    private Writer writer;
    private ProcProperties procProps;
    private int bufferSize;
    private int busProcHead;

    @Before
    public void setup() {
        bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));
        buffer = new RingBufferStore(bufferSize);
        busProcHead = 10;
        ProcessorID trailProc = ProcessorID.IN_BUSINESS_PROCESSOR;
        ProcessorID leadProc = ProcessorWorkflow.getLeadProc(trailProc);

        PosController proc = PosControllerFactory.getController();
        proc.setPos(leadProc, 0);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(leadProc)
                .setLeadProc(trailProc)
                .setInitialHead(busProcHead)
                .createProcProperties();

        proc.setPos(trailProc, busProcHead);

        writer = new Writer(procProps);
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        //write to head
        for (int i = 0; i < busProcHead; i++) {
            writer.write(new Integer((i)));
        }

        //attempt to go beyond head
        assertThat(writer.write(new Integer((600))), is(OpStatus.HEADER_REACHED));
    }

}