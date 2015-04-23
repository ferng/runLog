package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;

public class Reader {
    private PosController posController =  PosControllerFactory.getInstance();
    private RingBuffer buffer;
    private int head;
    private int pos;

    public Reader(RingBuffer buffer, int head, int pos) {
        this.buffer = buffer;
        this.head = head;
        this.pos = pos;
    }


    public Object read() {
        return buffer.get(pos++);
    }

}
