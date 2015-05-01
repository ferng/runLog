package com.thecrunchycorner.runlog.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbuffer.enums.BufferType;
import com.thecrunchycorner.runlog.ringbuffer.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder;
import com.thecrunchycorner.runlog.services.SystemProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WriterAttemptInsertBeyondHeadSuccessTest {

    private RingBuffer<Integer> buffer;
    private Writer writer;
    private ProcProperties procProps;
    private int bufferSize;
    private int busProcHead;
    private PosController proc;

    @Before
    public void setup() {
        bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));
        buffer = new RingBuffer(bufferSize, BufferType.INPUT);
        busProcHead = 10;

        proc = PosControllerFactory.getController();
        proc.setPos(ProcessorType.BUSINESS_PROCESSOR, 0);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(buffer)
                .setProcessor(ProcessorType.BUSINESS_PROCESSOR)
                .setLeadProc(ProcessorType.INPUT_PROCESSOR)
                .setInitialHead(busProcHead)
                .createProcProperties();

        proc.setPos(ProcessorType.INPUT_PROCESSOR, busProcHead);

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

        proc.setPos(ProcessorType.INPUT_PROCESSOR, busProcHead+10);
        //attempt to go beyond head
        assertThat(writer.write(new Integer((600))), is(OpStatus.WRITE_SUCCESS));
    }

}