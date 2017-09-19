package com.thecrunchycorner.lmax.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.msgstore.enums.OpStatus;
import com.thecrunchycorner.lmax.processors.ProcessorWorkflow;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcPropertiesBuilder;
import com.thecrunchycorner.lmax.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WriterAttemptInsertBeyondHeadSuccessTest {

    private RingBufferStore<Integer> buffer;
    private Writer writer;
    private ProcProperties procProps;
    private int bufferSize;
    private int busProcHead;
    private PosController proc;
    private ProcessorID trailProc = ProcessorID.IN_BUSINESS_PROCESSOR;
    private ProcessorID leadProc = ProcessorWorkflow.getLeadProc(trailProc);


    @Before
    public void setup() {
        bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));
        buffer = new RingBufferStore(bufferSize);
        busProcHead = 10;

        proc = PosControllerFactory.getController();
        proc.setPos(trailProc, 0);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(trailProc)
                .setLeadProc(leadProc)
                .setInitialHead(busProcHead)
                .createProcProperties();

        proc.setPos(leadProc, busProcHead);

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

        proc.setPos(leadProc, busProcHead + 10);

        //attempt to go beyond head
        assertThat(writer.write(new Integer((600))), is(OpStatus.WRITE_SUCCESS));
    }

}