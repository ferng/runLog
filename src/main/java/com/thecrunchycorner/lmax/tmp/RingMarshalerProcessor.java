package com.thecrunchycorner.lmax.tmp;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.workflow.ProcessorId;
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
