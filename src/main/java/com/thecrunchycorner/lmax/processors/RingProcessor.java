package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.msgstore.enums.OpStatus;
import com.thecrunchycorner.lmax.ringbufferaccess.Message;
import com.thecrunchycorner.lmax.ringbufferaccess.Reader;
import com.thecrunchycorner.lmax.ringbufferaccess.Writer;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcPropertiesBuilder;

public abstract class RingProcessor extends Processor implements Runnable {
    private ProcessorId ringProcID;
    private ProcessorId ringLeadProcID;
    private volatile boolean interrupt = false;

    public final ProcessorId getRingProcID() {
        return ringProcID;
    }

    public final void setRingProcID(ProcessorId ringProcID) {
        this.ringProcID = ringProcID;
    }

    public final ProcessorId getRingLeadProcID() {
        return ringLeadProcID;
    }

    public final void setRingLeadProcID(ProcessorId ringLeadProcID) {
        this.ringLeadProcID = ringLeadProcID;
    }

    public final boolean isInterrupt() {
        return interrupt;
    }

    public final void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    final ProcProperties getProcProperties(RingBufferStore ring, ProcessorId ringProcID) {
        this.ringProcID = ringProcID;
        ringLeadProcID = ProcessorWorkflow.getLeadProc(ringProcID);

        ProcProperties procProps;
        getPosCtrlr().setPos(ringProcID, 0);
        int leadProcHead = getPosCtrlr().getPos(ringLeadProcID);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(ring)
                .setProcessor(ringProcID)
                .setLeadProc(ringLeadProcID)
                .setInitialHead(leadProcHead)
                .createProcProperties();
        return procProps;
    }


    @Override
    protected final void initRingReader(ProcProperties procProps) {
        setReader(new Reader(procProps));
    }


    @Override
    protected final void initRingWriter(ProcProperties procProps) {
        setWriter(new Writer(procProps));
    }


    @Override
    protected final Message getMessage() {
        return (Message) getReader().read();
    }


    @Override
    protected abstract Message processMessage(Message msg);


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
