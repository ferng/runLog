package com.thecrunchycorner.runlog.ringbufferprocessor;

import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;


/**
 * Immutable details about the processor. Must be instantiated through ProcPropertiesBuilder, behaviour is undefined otherwise.
 */
public class ProcProperties {
    private RingBufferStore buffer;
    private ProcessorType procType;
    private ProcessorType leadProcType;
    private int initialHead;

    protected ProcProperties(RingBufferStore buffer, ProcessorType procType, ProcessorType leadProcType, int initialHead) {
        this.buffer = buffer;
        this.procType = procType;
        this.leadProcType = leadProcType;
        this.initialHead = initialHead;
    }

    public final RingBufferStore getBuffer() {
        return buffer;
    }

    public final ProcessorType getProc() {
        return procType;
    }

    public final ProcessorType getLeadProc() {
        return leadProcType;
    }

    public final int getInitialHead() {
        return initialHead;
    }

}
