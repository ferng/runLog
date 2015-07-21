package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.Reader;
import com.thecrunchycorner.runlog.ringbufferaccess.Writer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder;

public class RingProcessor extends Processor implements Runnable {
    private ProcessorID ringProcID;
    private ProcessorID ringLeadProcID;
    private static final int ringPos = 0;

    private volatile boolean interrupt = false;


    public RingProcessor(RingBufferStore ring) {
        ringProcID = ProcessorID.UNMARSHALER;
        ringLeadProcID = ProcessorID.BUSINESS_PROCESSOR;

        initRingReader(ring, ringPos);
        initRingWriter(ring, ringPos);
    }


    public RingProcessor(RingBufferStore readRing, RingBufferStore writeRing) {
        ringProcID = ProcessorID.UNMARSHALER;
        ringLeadProcID = ProcessorID.BUSINESS_PROCESSOR;

        initRingReader(readRing, ringPos);
        initRingWriter(writeRing, ringPos);
    }


    @Override
    protected void initRingReader(RingBufferStore ring, int initPos) {
        ProcProperties procProps;
        procProps = getProcProperties(ring, initPos);

        reader = new Reader(procProps);
    }


    @Override
    protected void initRingWriter(RingBufferStore ring, int initPos) {
        ProcProperties procProps;
        procProps = getProcProperties(ring, initPos);

        writer = new Writer(procProps);
    }


    private ProcProperties getProcProperties(RingBufferStore ring, int initPos) {
        ProcProperties procProps;
        posCtrlr.setPos(ringProcID, initPos);
        int leadProcHead = posCtrlr.getPos(ringLeadProcID);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(ring)
                .setProcessor(ringProcID)
                .setLeadProc(ringLeadProcID)
                .setInitialHead(leadProcHead)
                .createProcProperties();
        return procProps;
    }


    @Override
    protected Message getMessage() {
        return (Message) reader.read();
    }


    @Override
    protected Message processMessage(Message msg) {
        return msg;
    }


    @Override
    protected OpStatus putMessage(Message msg) {
        return writer.write(msg);
    }


    public void run() {
        while (!interrupt) {
            getAndProcessMsg();
        }
    }


    protected void getAndProcessMsg() {
        Message msg = processMessage(getMessage());
        while (putMessage(msg) == OpStatus.HEADER_REACHED) {
        }
        nextPos(ringProcID);
    }


    public void reqInterrupt() {
        interrupt = true;
    }
}
