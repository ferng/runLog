package com.thecrunchycorner.lmax.handlers;

import com.thecrunchycorner.lmax.buffer.Message;

/**
 * Provides clients with the means to read from a store of some source (buffer, jmsqueue, file).
 * Each reader is unique to a processor.
 */
public interface Reader {

    /**
     * What's the ID of the buffer we are accessing.
     *
     * @return buffer the buffer we work on
     */
    int getBufferId();

    /**
     * Retrieves an object from its buffer.
     *
     * @param pos the position on the buffer to read from. It is up to the client to ensure the
     * position being read is not beyond the leading processor's position thereby reading invalid
     * data.
     * @throws IllegalArgumentException if the position is negative or the message is null
     */
    Message read(int pos);
}
