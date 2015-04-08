package com.thecrunchycorner.runlog.ringbuffer;

import java.util.ArrayList;

public class RingBuffer<E> {

    private ArrayList<E> buffer;
    private int modSize;

    public RingBuffer(int size) {
        buffer = new ArrayList<E>(size);
        this.modSize = size;
    }


    public void put(int pos, E item) {
        if (buffer.size() < modSize) {
            buffer.add(pos % modSize, item);
        } else {
            buffer.set((pos % modSize) - 1, item);
        }
    }


    public E get(int pos) {
        return buffer.get(pos % modSize);
    }


    public int size() {
        return buffer.size();
    }
}
