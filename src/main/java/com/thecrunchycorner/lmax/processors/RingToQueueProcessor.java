package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.LinkedBlockingQueueStore;
import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.msgstore.enums.OpStatus;
import com.thecrunchycorner.lmax.ringbufferaccess.Message;
import com.thecrunchycorner.lmax.ringbufferaccess.Reader;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcPropertiesBuilder;

public class RingToQueueProcessor extends Processor implements Runnable {
    private LinkedBlockingQueueStore<Message> queue;
    private volatile boolean interrupt = false;


    public RingToQueueProcessor(RingBufferStore ring, LinkedBlockingQueueStore<Message> queue) {
        ProcessorId readProcID;
        ProcessorId readLeadProcID;
        this.queue = queue;

        readProcID = ProcessorId.OUT_Q_SEND;
        readLeadProcID = ProcessorWorkflow.getLeadProc(readProcID);

        ProcProperties procProps;
        getPosCtrlr().setPos(readProcID, 0);
        int leadProcHead = getPosCtrlr().getPos(readLeadProcID);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(ring)
                .setProcessor(readProcID)
                .setLeadProc(readLeadProcID)
                .setInitialHead(leadProcHead)
                .createProcProperties();

        initRingReader(procProps);
    }


    @Override
    protected final void initRingWriter(ProcProperties procProps) {
    }


    @Override
    protected final void initRingReader(ProcProperties procProps) {
        setReader(new Reader(procProps));
    }


    @Override
    protected final Message getMessage() {
        return (Message) getReader().read();
    }


    @Override
    protected final Message processMessage(Message msg) {
        return msg;
    }


    @Override
    protected final OpStatus putMessage(Message msg) {
        return (queue.add(msg) ? OpStatus.WRITE_SUCCESS : OpStatus.ERROR);
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
