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
    private LinkedBlockingQueueStore queue;
    private ProcessorID writeProcID;
    private ProcessorID writeLeadProcID;
    private volatile boolean interrupt = false;


    public QueueToRingProcessor(LinkedBlockingQueueStore queue, RingBufferStore ring) {
        this.queue = queue;

        writeProcID = ProcessorID.INPUT_QUEUE_PROCESSOR;
        writeLeadProcID = ProcessorWorkflow.getLeadProc(writeProcID);

        ProcProperties procProps;
        posCtrlr.setPos(writeProcID, 0);
        int leadProcHead = posCtrlr.getPos(writeLeadProcID);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(ring)
                .setProcessor(writeProcID)
                .setLeadProc(writeLeadProcID)
                .setInitialHead(leadProcHead)
                .createProcProperties();

        initRingWriter(procProps);
    }


    @Override
    protected void initRingWriter(ProcProperties procProps) {
        writer = new Writer(procProps);
    }


    @Override
    protected void initRingReader(ProcProperties procProps) {

    }


    @Override
    protected Message getMessage() {
        return (Message) queue.take();
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
        nextPos(writeProcID);
    }


    public void reqInterrupt() {
        interrupt = true;
    }
}
