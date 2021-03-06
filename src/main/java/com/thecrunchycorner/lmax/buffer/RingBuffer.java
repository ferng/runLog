package com.thecrunchycorner.lmax.buffer;

import com.thecrunchycorner.lmax.services.SystemProperties;
import java.util.MissingResourceException;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A circular buffer used to exchange data between a disruptor and a processor.
 */
public class RingBuffer {
    private static final Logger LOGGER = LogManager.getLogger(RingBuffer.class);

    private final transient Message[] buffer;
    private final transient int bufferSize;
    private final transient int id;


    /**
     * Constructor for RingBuffer.
     *
     * @param size the size of the buffer. Once instantiated it cannot be changed. If the size
     * requested is less than that specified in threshold.buffer.minimum.size it will be
     * increased to that threshold, so, essentially, passing 0 will result in a buffer with the
     * default threshold buffer size.
     */
    public RingBuffer(final int id, final int size)
            throws MissingResourceException, IllegalStateException {
        int minSize = SystemProperties.getThresholdBufferSize();
        if (size < minSize) {
            LOGGER.warn("Suggested buffer size {} is too small, defaulting to minimum {}.",
                    size, minSize);
        } else {
            minSize = size;
        }
        buffer = new Message[minSize];
        this.bufferSize = minSize;
        this.id = id;
    }


    /**
     * Inserts the item into the given buffer position.
     * <p>
     * <p>
     * You must check whether you can write to a given position with:
     * {@link com.thecrunchycorner.lmax.workflow.ProcessorWorkflow#getLeadPos(int, int)}
     * prior to writing to it as this will happily overwrite data which hasn't been processed yet.
     * </p>
     *
     * @param pos the index-ed buffer position to write to
     * @param item the new value
     * @throws IllegalArgumentException if the position is negative or the message is null
     */
    final void set(final int pos, final Message item) {
        if (pos < 0) {
            LOGGER.error("Position cannot be negative, message write failed");
            throw new IllegalArgumentException("Position cannot be negative");
        }
        Objects.requireNonNull(item, "Cannot write null to the buffer");

        final int realPos = getRealPos(pos);
        buffer[realPos] = item;
        LOGGER.debug("value [{}] placed at index[{}] (real position [{}])", item, pos, realPos);
    }


    /**
     * Gets the item from the given position.
     * <p>
     * <p>
     * This operation does not remove any data being held
     * by the buffer, nor does it affect the buffer in any way. This means the data is available
     * for any other processor that may need it as long as the leading processor's head hasn't
     * overwritten it, this should not happen as the current processor's position will still keep
     * control of the data until it no longer needs it
     * </p>
     * <p>
     * You must check whether you can read from a given position with:
     * {@link com.thecrunchycorner.lmax.workflow.ProcessorWorkflow#getLeadPos(int, int)}
     * prior to reading from it, as this will happily read data out of sequence which hasn't been
     * processed by leading processors yet.
     * </p>
     *
     * @param pos the index to read from
     * @return the value of the index-ed position
     * @throws IllegalArgumentException if the position is negative
     */
    final Message get(final int pos) {
        if (pos < 0) {
            LOGGER.error("Position cannot be negative, message read failed");
            throw new IllegalArgumentException("Position cannot be negative");
        }
        return buffer[getRealPos(pos)];
    }


    /**
     * What's this buffer's ID?
     * @return the ID for this buffer
     */
    public final int getId() {
        return id;
    }


    /**
     * Gets the size of the buffer which will either the default or the value passed to then
     * constructor, whichever is larger.
     *
     * @return the size of the buffer
     */
    public final int size() {
        return bufferSize;
    }


    private int getRealPos(final int pos) {
        if (pos < bufferSize) {
            return pos;
        } else {
            return pos % bufferSize;
        }
    }

}
