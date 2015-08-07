package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;

public class RingMarshalerProcessor extends RingProcessor implements Runnable {

    public RingMarshalerProcessor(RingBufferStore ring) {
        ProcProperties procProps = getProcProperties(ring, ProcessorID.OUT_MARSHALER);

        initRingReader(procProps);
        initRingWriter(procProps);
    }

    @Override
    protected final Message processMessage(Message msg) {
        return msg;
    }
}
