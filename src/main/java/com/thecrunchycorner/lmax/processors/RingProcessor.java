package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.msgstore.OpStatus;
import com.thecrunchycorner.lmax.workflow.ProcessorId;
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;

public abstract class RingProcessor extends Processor implements Runnable {
    private ProcessorId procId;
    private volatile boolean interrupt = false;

    ProcessorId getProcId() {
        return procId;
    }

    void setProcId(ProcessorId procId) {
        this.procId = procId;
    }

    @Override
    void updateHead() {
        int leadPos = ProcessorWorkflow.getLeadPos(procId);
        updatePos(leadPos);
    }


    public final boolean isInterrupt() {
        return interrupt;
    }

    public final void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
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
