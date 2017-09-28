package com.thecrunchycorner.lmax.ringbufferprocessor;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;


/**
 * Immutable details about the processor. Must be instantiated through
 * ProcPropertiesBuilder, behaviour is undefined otherwise.
 */
public class ProcProperties {
    private RingBufferStore buffer;
    private ProcessorId procType;
    private ProcessorId leadProcType;
    private int initialHead;

    protected ProcProperties(RingBufferStore buffer, ProcessorId procType, ProcessorId leadProcType, int initialHead) {
        this.buffer = buffer;
        this.procType = procType;
        this.leadProcType = leadProcType;
        this.initialHead = initialHead;
    }

    public final RingBufferStore getBuffer() {
        return buffer;
    }

    public final ProcessorId getProc() {
        return procType;
    }

    public final ProcessorId getLeadProc() {
        return leadProcType;
    }

    public final int getInitialHead() {
        return initialHead;
    }

}
