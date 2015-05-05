package com.thecrunchycorner.runlog.ringbufferprocessor;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;

/**
 * Used to instantiate ProcProperties. Behaviour is undefined if ProcProperties is instantiated directly.
 */
public class ProcPropertiesBuilder {
    private RingBuffer buffer;
    private ProcessorType proc;
    private ProcessorType leadProc;
    private int initialHead;


    /**
     * The buffer to be accessed.
     */
    public ProcPropertiesBuilder setBuffer(RingBuffer buffer) {
        this.buffer = buffer;
        return this;
    }


    /**
     * ID of the processor accessing the buffer.
     */
    public ProcPropertiesBuilder setProcessor(ProcessorType proc) {
        this.proc = proc;
        return this;
    }


    /**
     * Id of the processor we are following so we don't jump ahead of it to avoid reading stale data or loose our data somewhere
     * as it will be overwritte
     */
    public ProcPropertiesBuilder setLeadProc(ProcessorType leadProc) {
        this.leadProc = leadProc;
        return this;
    }


    /**
     * How far can we go to when we first start. In truth only the leading reader/writer will ever be non-zero in which case this value should
     * be set to the size of the buffer
     */
    public ProcPropertiesBuilder setInitialHead(int initialHead) {
        this.initialHead = initialHead;
        return this;
    }


    /**
     * Instantiate this set of properties
     * @return newly created properties object
     */
    public ProcProperties createProcProperties() {
        return new ProcProperties(buffer, proc, leadProc, initialHead);
    }
}
