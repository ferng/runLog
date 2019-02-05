package com.thecrunchycorner.lmax.handlers;

import com.thecrunchycorner.lmax.buffer.Message;

/**
 * Provides clients with the means to write to a store of some source (buffer, jmsqueue, file).
 * Each reader is unique to a processor.
 */
public interface Writer {

    /**
     * What's the ID of the buffer we are accessing.
     *
     * @return buffer the buffer we work on
     */
    int getBufferId();

    /**
     * Writes an object to its buffer. It is up to the client to ensure the position being
     * written is not beyond the leading processor's position thereby corrupting data being still
     * to be read or written by it's leading processor.
     *
     * @param pos what position in the buffer do we want to write data to
     * @param msg what's the message to to write to this buffer?
     * @throws IllegalArgumentException if the position is negative or the message is null
     */
    void write(int pos, Message msg);
}
