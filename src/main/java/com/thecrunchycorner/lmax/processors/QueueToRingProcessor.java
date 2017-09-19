package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.LinkedBlockingQueueStore;
import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.msgstore.enums.OpStatus;
import com.thecrunchycorner.lmax.ringbufferaccess.Message;
import com.thecrunchycorner.lmax.ringbufferaccess.Writer;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcPropertiesBuilder;

public class QueueToRingProcessor extends Processor implements Runnable {
    private LinkedBlockingQueueStore queue;

    private volatile boolean interrupt = false;


    public QueueToRingProcessor(LinkedBlockingQueueStore queue, RingBufferStore ring) {
        ProcessorID writeProcID;
        ProcessorID writeLeadProcID;

        this.queue = queue;

        writeProcID = ProcessorID.IN_Q_RECEIVER;
        writeLeadProcID = ProcessorWorkflow.getLeadProc(writeProcID);

        ProcProperties procProps;
        getPosCtrlr().setPos(writeProcID, 0);
        int leadProcHead = getPosCtrlr().getPos(writeLeadProcID);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(ring)
                .setProcessor(writeProcID)
                .setLeadProc(writeLeadProcID)
                .setInitialHead(leadProcHead)
                .createProcProperties();

        initRingWriter(procProps);
    }


    @Override
    protected final void initRingWriter(ProcProperties procProps) {
        setWriter(new Writer(procProps));
    }


    @Override
    protected final void initRingReader(ProcProperties procProps) {
    }


    @Override
    protected final Message getMessage() {
        return (Message) queue.take();
    }


    @Override
    protected final Message processMessage(Message msg) {
        return msg;
    }


    @Override
    protected final OpStatus putMessage(Message msg) {
        return getWriter().write(msg);
    }


    public final void run() {
        while (!interrupt) {
            getAndProcessMsg();
        }
    }


    protected final void getAndProcessMsg() {
        Message msg = processMessage(getMessage());
        while (putMessage(msg) == OpStatus.HEADER_REACHED) {
        }
    }


    public final void reqInterrupt() {
        interrupt = true;
    }
}
