package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;

public class RingBusinessProcessor extends RingProcessor implements Runnable {

    public RingBusinessProcessor(RingBufferStore readRing, RingBufferStore writeRing) {
        initRingReader(getProcProperties(readRing, ProcessorID.IN_BUSINESS_PROCESSOR));
        initRingWriter(getProcProperties(writeRing, ProcessorID.OUT_BUSINESS_PROCESSOR));
    }


    @Override
    protected final Message processMessage(Message msg) {
        return msg;
    }
}
