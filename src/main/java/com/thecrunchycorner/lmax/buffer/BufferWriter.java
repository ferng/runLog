package com.thecrunchycorner.lmax.buffer;

import com.thecrunchycorner.lmax.handlers.Writer;
import java.util.Objects;

/**
 * Provides clients with the means to write to a buffer. Each reader is unique to a processor.
 */
public class BufferWriter implements Writer {
    private RingBuffer buffer;


    /**
     * What's the ID of the buffer we are accessing.
     *
     * @param buffer the buffer we will be reading from
     */
    public BufferWriter(RingBuffer buffer) {
        Objects.requireNonNull(buffer, "Missing buffer");
        this.buffer = buffer;
    }


    /**
     * What's the ID of the buffer we are accessing.
     *
     * @return buffer the buffer we work on
     */
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
    public void write(int pos, Message msg) throws IllegalArgumentException {
        Objects.requireNonNull(msg, "Cannot write null to the buffer");
        buffer.set(pos, msg);
    }
}
