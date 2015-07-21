package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.LinkedBlockingQueueStore;
import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.Writer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder;

public class QueueToRingProcessor extends Processor implements Runnable {
    private ProcessorID writeProcID;
    private ProcessorID writeLeadProcID;
    private LinkedBlockingQueueStore queue;
    private RingBufferStore ring;
    private ProcProperties procProps;
    private volatile boolean interrupt = false;


    public QueueToRingProcessor(LinkedBlockingQueueStore queue, RingBufferStore ring) {
        this.queue = queue;
        this.ring = ring;

        writeProcID = ProcessorID.INPUT_QUEUE_PROCESSOR;
        writeLeadProcID = ProcessorID.UNMARSHALER;

        initWriter(0);
    }


    protected void initWriter(int initPos) {
        posCtrlr.setPos(writeProcID, initPos);
        int leadProcHead = posCtrlr.getPos(writeLeadProcID);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(ring)
                .setProcessor(writeProcID)
                .setLeadProc(writeLeadProcID)
                .setInitialHead(leadProcHead)
                .createProcProperties();

        writer = new Writer(procProps);
    }


    protected Message getMessage() {
        return (Message) queue.take();
    }


    protected Message processMessage(Message msg) {
        return msg;
    }


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
        nextPos(writeLeadProcID);
    }


    public void reqInterrupt() {
        interrupt = true;
    }
}
