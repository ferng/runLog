package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.ringbufferaccess.Message;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcProperties;

public class RingUnmarshalerProcessor extends RingProcessor implements Runnable {

    public RingUnmarshalerProcessor(RingBufferStore ring) {
        ProcProperties procProps = getProcProperties(ring, ProcessorId.IN_UNMARSHALL);

        initRingReader(procProps);
        initRingWriter(procProps);
    }

    @Override
    protected final Message processMessage(Message msg) {
        return msg;
    }
}
