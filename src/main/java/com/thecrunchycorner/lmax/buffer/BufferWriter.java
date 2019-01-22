package com.thecrunchycorner.lmax.buffer;

import com.thecrunchycorner.lmax.handlers.Writer;
import java.util.Objects;

/**
 * Provides client classes with the means to write to a buffer.
 * Each writer is unique to the processor using it.
 */
public class BufferWriter<E> implements Writer<E> {
    private RingBuffer<E> buffer;


    /**
     * Each BufferWriter is unique to a processor (although a processor can have multiple readers
     * / writers)
     *
     * @param buffer is the RingBuffer we will be writing to
     */
    public BufferWriter(RingBuffer<E> buffer) {
        Objects.requireNonNull(buffer, "Missing buffer");
        this.buffer = buffer;
    }

    @Override
    public int getBufferId() {
        return buffer.getId();
    }

    /**
     * Writes an object to its buffer. It is up to the client to ensure the position being
     * written is not beyond the leading processor's position thereby corrupting data being still
     * to be read or written by it's leading processor.
     *
     * @param pos what position in the buffer do we want to write data to
     * @param msg what's the message to to write to this buffer?
     * @throws IllegalArgumentException if the position is negative or the message is null
     */
    @Override
    public void write(int pos, E msg) throws IllegalArgumentException {
        Objects.requireNonNull(msg, "Cannot write null to the buffer");
        buffer.set(pos, msg);
    }
}
