package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.msgstore.OpStatus;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;

public abstract class Processor {
    ProcProperties props;
    ProcessorStatus status;


    protected Processor() {
    }

    void setProps(ProcProperties props) {
        this.props = props;
    }

    void updatePos(int pos) {
        props.setPos(pos);
    }

    ProcessorStatus getStatus() {
        return status;
    }

    void setStatus(ProcessorStatus status) {
        this.status = status;
    }

    abstract void updateHead();

    protected abstract Message getMessage();

    protected abstract Message processMessage(Message msg);

    protected abstract OpStatus putMessage(Message msg);
}
