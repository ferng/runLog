package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.msgstore.OpStatus;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;

public abstract class ProcessorAbs<T> {
    private ProcessorStatus status;
    private volatile boolean interrupt = false;
    private ProcProperties props;


    public ProcessorAbs(ProcProperties procProperties) {
        this.props = props;
    }


    void updatePos(int pos) {
        props.setPos(pos);
    }

    void updateHead() {
        int leadPos = ProcessorWorkflow.getLeadPos(props.getPriority());
        updatePos(leadPos);
    }

    abstract void readAndProcessMsg();

    abstract void batchReadAndProcessMsg();

    abstract T readPayload();


    abstract Message processMessage(Message msg);

    abstract OpStatus writePyload(T payload);


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
