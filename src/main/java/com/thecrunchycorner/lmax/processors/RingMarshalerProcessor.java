package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.ringbufferaccess.Message;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;

public class RingMarshalerProcessor extends RingProcessor implements Runnable {

    public RingMarshalerProcessor(RingBufferStore ring) {
        ProcProperties procProps = getProcProperties(ring, ProcessorId.OUT_MARSHALL);

        initRingReader(procProps);
        initRingWriter(procProps);
    }

    @Override
    protected final Message processMessage(Message msg) {
        return msg;
    }
}
