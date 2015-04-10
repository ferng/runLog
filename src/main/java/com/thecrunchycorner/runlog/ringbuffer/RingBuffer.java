package com.thecrunchycorner.runlog.ringbuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class RingBuffer<E> {
    private static Logger logger = LogManager.getLogger(RingBuffer.class);

    private AtomicReferenceArray<E> buffer;
    private int modSize;


    public RingBuffer(int size) {
        buffer = new AtomicReferenceArray<E>(size);
        this.modSize = size;
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
}
