package com.thecrunchycorner.runlog.ringbufferprocessor;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;


/**
 * Used to instantiate PosController. Behaviour is undefined if PosController is instantiate directly.
 * Immutable details about the processor. Once created the processor cannot change it's identity
 */
public class ProcProperties {
    private RingBuffer buffer;
    private ProcessorType proc;
    private ProcessorType leadProc;
    private int initialHead;

    protected ProcProperties(RingBuffer buffer, ProcessorType proc, ProcessorType leadProc, int initialHead) {
        this.buffer = buffer;
        this.proc = proc;
        this.leadProc = leadProc;
        this.initialHead = initialHead;
    }

    public RingBuffer getBuffer() {
        return buffer;
    }

    public ProcessorType getProc() {
        return proc;
    }

    public ProcessorType getLeadProc() {
        return leadProc;
    }

    public int getInitialHead() {
        return initialHead;
    }

}
