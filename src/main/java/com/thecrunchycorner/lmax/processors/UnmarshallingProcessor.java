package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.msgstore.OpStatus;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;

public abstract class UnmarshallingProcessor<T> {
    private ProcessorStatus status;
    private volatile boolean interrupt = false;
    private ProcProperties props;


    public UnmarshallingProcessor(ProcProperties procProperties) {
        this.props = props;
    }


    void updatePos(int pos) {
        props.setPos(pos);
    }

    void updateHead() {
        int leadPos = ProcessorWorkflow.getLeadPos(props.getPriority());
        updatePos(leadPos);
    }

    private void readAndProcessMsg() {
        Message msg = processMessage(readMessage());
        while (writeMessage(msg) == OpStatus.HEADER_REACHED) {
        }
    }

    private void batchReadAndProcessMsg() {
        Message msg = processMessage(readMessage());
        while (writeMessage(msg) == OpStatus.HEADER_REACHED) {
        }
    }

    private Message readMessage() {
        return props.getReader().read(props.getPos());
    }


    private Message processMessage(Message msg) {
        return props.getProcess().apply(msg);
    }

    abstract OpStatus writeMessage(Message msg);


    ProcessorStatus getStatus() {
        return status;
    }

    void setStatus(ProcessorStatus status) {
        this.status = status;
    }

    public final boolean isInterrupt() {
        return interrupt;
    }

    public final void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    public final void reqInterrupt() {
        interrupt = true;
    }

    public final void run() {
        while (!interrupt) {
            readAndProcessMsg();
        }
    }
}
