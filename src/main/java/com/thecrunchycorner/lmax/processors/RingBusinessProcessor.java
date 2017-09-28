package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.ringbufferaccess.Message;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;

public class RingBusinessProcessor extends RingProcessor implements Runnable {

    public RingBusinessProcessor(RingBufferStore readRing, RingBufferStore writeRing) {
//        initRingReader(getProcProperties(readRing, ProcessorId.IN_BUSINESS_PROCESSOR));
//        initRingWriter(getProcProperties(writeRing, ProcessorId.OUT_BUSINESS_PROCESSOR));
    }


    @Override
    protected final Message processMessage(Message msg) {
        return msg;
    }
}
