package com.thecrunchycorner.lmax.ringbufferprocessor;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorID;


/**
 * Immutable details about the processor. Must be instantiated through ProcPropertiesBuilder, behaviour is undefined otherwise.
 */
public class ProcProperties {
    private RingBufferStore buffer;
    private ProcessorID procType;
    private ProcessorID leadProcType;
    private int initialHead;

    protected ProcProperties(RingBufferStore buffer, ProcessorID procType, ProcessorID leadProcType, int initialHead) {
        this.buffer = buffer;
        this.procType = procType;
        this.leadProcType = leadProcType;
        this.initialHead = initialHead;
    }

    public final RingBufferStore getBuffer() {
        return buffer;
    }

    public final ProcessorID getProc() {
        return procType;
    }

    public final ProcessorID getLeadProc() {
        return leadProcType;
    }

    public final int getInitialHead() {
        return initialHead;
    }

}
