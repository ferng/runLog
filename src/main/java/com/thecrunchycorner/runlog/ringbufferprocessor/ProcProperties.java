package com.thecrunchycorner.runlog.ringbufferprocessor;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;


/**
 * Immutable details about the processor. Must be instantiated through ProcPropertiesBuilder, behaviour is undefined otherwise.
 */
public class ProcProperties {
    private RingBuffer buffer;
    private ProcessorType procType;
    private ProcessorType leadProcType;
    private int initialHead;

    protected ProcProperties(RingBuffer buffer, ProcessorType procType, ProcessorType leadProcType, int initialHead) {
        this.buffer = buffer;
        this.procType = procType;
        this.leadProcType = leadProcType;
        this.initialHead = initialHead;
    }

    public RingBuffer getBuffer() {
        return buffer;
    }

    public ProcessorType getProc() {
        return procType;
    }

    public ProcessorType getLeadProc() {
        return leadProcType;
    }

    public int getInitialHead() {
        return initialHead;
    }

}
