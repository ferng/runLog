package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.Reader;
import com.thecrunchycorner.runlog.ringbufferaccess.Writer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder;

public abstract class RingProcessor extends Processor implements Runnable {
    private ProcessorID ringProcID;
    private ProcessorID ringLeadProcID;
    private volatile boolean interrupt = false;

    public final ProcessorID getRingProcID() {
        return ringProcID;
    }

    public final void setRingProcID(ProcessorID ringProcID) {
        this.ringProcID = ringProcID;
    }

    public final ProcessorID getRingLeadProcID() {
        return ringLeadProcID;
    }

    public final void setRingLeadProcID(ProcessorID ringLeadProcID) {
        this.ringLeadProcID = ringLeadProcID;
    }

    public final boolean isInterrupt() {
        return interrupt;
    }

    public final void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    protected final ProcProperties getProcProperties(RingBufferStore ring, ProcessorID ringProcID) {
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
