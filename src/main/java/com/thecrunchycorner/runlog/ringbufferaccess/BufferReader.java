package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbuffer.types.AccessStatus;

public class BufferReader {
    private BufferPosController posController =  BufferPosControllerFactory.getInstance();
    private RingBuffer buffer;
    private int head;
    private int pos;

    public BufferReader(RingBuffer buffer, int head, int pos) {
        this.buffer = buffer;
        this.head = head;
        this.pos = pos;
    }


    public Object read() {
        return buffer.get(pos++);
    }

}
