package com.thecrunchycorner.runlog.ringbuffer;

import com.thecrunchycorner.runlog.ringbuffer.types.BufferType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class RingBuffer<E> {
    private static Logger logger = LogManager.getLogger(RingBuffer.class);

    private AtomicReferenceArray<E> buffer;
    private int modSize;
    private BufferType type;


    public RingBuffer(int size, BufferType type) {
        buffer = new AtomicReferenceArray<E>(size);
        this.modSize = size;
        this.type = type;
    }


    public void put(int pos, E item) {
        int realPos = pos % modSize;
        if (buffer.get(modSize-1) != null) {
            realPos--;
        }
        buffer.set(realPos % modSize, item);
        logger.debug("put: {} at {} (internal {})", item, pos, realPos);
    }


    public E get(int pos) {
        return buffer.get(pos % modSize);
    }


    public int size() {
        return buffer.length();
    }


    public BufferType getType() {
        return type;
    }


}
