package com.thecrunchycorner.lmax.ringbufferprocessor;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;

/**
 * Used to instantiate ProcProperties. Behaviour is undefined
 * if ProcProperties is instantiated directly.
 */
public class ProcPropertiesBuilder {
    private RingBufferStore buffer;
    private ProcessorId proc;
    private ProcessorId leadProc;
    private int initialHead;


    /**
     * The buffer to be accessed.
     */
    public final ProcPropertiesBuilder setBuffer(RingBufferStore buffer) {
        this.buffer = buffer;
        return this;
    }


    /**
     * ID of the processor accessing the buffer.
     */
    public final ProcPropertiesBuilder setProcessor(ProcessorId proc) {
        this.proc = proc;
        return this;
    }


    /**
     * Id of the processor we are following so we don't jump ahead
     * of it to avoid reading stale data or loose our data somewhere
     * as it will be overwritte
     */
    public final ProcPropertiesBuilder setLeadProc(ProcessorId leadProc) {
        this.leadProc = leadProc;
        return this;
    }


    /**
     * How far can we go to when we first start. In truth only the
     * leading reader/writer will ever be non-zero in which case this value should
     * be set to the size of the buffer
     */
    public final ProcPropertiesBuilder setInitialHead(int initialHead) {
        this.initialHead = initialHead;
        return this;
    }


    /**
     * Instantiate this set of properties
     *
     * @return newly created properties object
     */
    public final ProcProperties createProcProperties() {
        return new ProcProperties(buffer, proc, leadProc, initialHead);
    }
}
