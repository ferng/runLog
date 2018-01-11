package com.thecrunchycorner.lmax.storehandler;

import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.msgstore.RingBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides client classes with the means to write to a buffer.
 * Each writer is unique to the processor using it.
 */
public class BufferWriter<E> implements Writer<E>{
    private static Logger LOGGER = LogManager.getLogger(BufferWriter.class);

    private RingBuffer<E> buffer;


    /**
     * Each BufferWriter is unique to a processor (although a processor can have multiple readers
     * / writers)
     *
     * @param buffer is the RingBuffer we will be writing to
     */
    public BufferWriter(RingBuffer<E> buffer) {
        this.buffer = buffer;
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
    public final void write(int pos, E msg) throws IllegalArgumentException {
        if (pos < 0) {
            LOGGER.error("Position cannot be negative, message write failed");
            throw new IllegalArgumentException("Position cannot be negative");
        }
        if (msg == null) {
            LOGGER.error("Cannot write null to the buffer, message write failed");
            throw new IllegalArgumentException("Cannot write null to the buffer");
        }

        buffer.set(pos, msg);
    }
}
