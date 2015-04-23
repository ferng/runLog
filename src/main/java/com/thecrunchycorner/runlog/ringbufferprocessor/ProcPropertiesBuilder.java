package com.thecrunchycorner.runlog.ringbufferprocessor;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;

public class ProcPropertiesBuilder {
    private RingBuffer buffer;
    private ProcessorType proc;
    private ProcessorType leadProc;
    private int initialHead;


    public ProcPropertiesBuilder setBuffer(RingBuffer buffer) {
        this.buffer = buffer;
        return this;
    }

    public ProcPropertiesBuilder setProcessor(ProcessorType proc) {
        this.proc = proc;
        return this;
    }

    public ProcPropertiesBuilder setLeadProc(ProcessorType leadProc) {
        this.leadProc = leadProc;
        return this;
    }

    public ProcPropertiesBuilder setInitialHead(int initialHead) {
        this.initialHead = initialHead;
        return this;
    }


    public ProcProperties createProcProperties() {
        return new ProcProperties(buffer, proc, leadProc, initialHead);
    }
}
