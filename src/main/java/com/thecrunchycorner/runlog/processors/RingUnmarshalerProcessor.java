package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;

public class RingUnmarshalerProcessor extends RingProcessor implements Runnable {

    public RingUnmarshalerProcessor(RingBufferStore ring) {
        super(ring);
    }


    @Override
    protected Message processMessage(Message msg) {
        return msg;
    }
}
