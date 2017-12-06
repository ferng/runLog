package com.thecrunchycorner.lmax.storehandler;

import com.thecrunchycorner.lmax.msgstore.RingBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides client classes with the means to read from a buffer. Each reader is uniquely owned by
 * the processor using it.
 */
public class BufferReader<E> {
    private static Logger LOGGER = LogManager.getLogger(BufferWriter.class);

    private RingBuffer<E> buffer;


    /**
     * Each BufferReader is unique to a processor.
     *
     * @param buffer the buffer we will be reading from
     */
    public BufferReader(RingBuffer<E> buffer) {
        this.buffer = buffer;
    }


    /**
     * Retrieves an object from its buffer.
     *
     * @param pos the position on the buffer to read from. It is up to the client to ensure the
     * position being read is not beyond the leading processor's position thereby reading invalid
     * data.
     * @throws IllegalArgumentException if the position is negative or the message is null
     */
    public final E read(int pos) {
        if (pos < 0) {
            LOGGER.error("Position cannot be negative, message read failed");
            throw new IllegalArgumentException("Position cannot be negative");
        }
        return buffer.get(pos);
    }
}
