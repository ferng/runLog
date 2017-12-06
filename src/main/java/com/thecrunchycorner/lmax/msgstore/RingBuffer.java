package com.thecrunchycorner.lmax.msgstore;

import com.thecrunchycorner.lmax.services.SystemProperties;
import java.util.MissingResourceException;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A circular buffer used to exchange data between a disruptor and a processor.
 *
 * @param <E> the type of the contents held by the buffer.
 *
 * <p>The buffer carries out no checks on the data being inserted besides the type checks carried
 * out by the generics framework.</p>
 */
public class RingBuffer<E> {
    private static final Logger LOGGER = LogManager.getLogger(RingBuffer.class);

    private final transient AtomicReferenceArray<E> buffer;
    private final transient int bufferSize;


    /**
     * Constructor for RingBuffer.
     *
     * @param size the size of the buffer. Once instantiated it cannot be changed. If the size
     * requested is less than that specified in threshold.buffer.minimum.size it will be
     * increased to that threshold, so, essentially, passing 0 will result in a buffer with the
     * default threshold buffer size.
     */
    public RingBuffer(final int size) {
        OptionalInt opt = SystemProperties.getAsInt("threshold.buffer.minimum.size");
        if (!opt.isPresent()) {
            throw new MissingResourceException("Mandatory default system propery missing: "
                    + "threshold.buffer.minimum.size", getClass().getName(), "");
        }
        int minSize = opt.getAsInt();
        if (size < minSize) {
            LOGGER.warn("Suggested buffer size {} is too small, defaulting to minimum {}.",
                    size, minSize);
        } else {
            minSize = size;
        }
        buffer = new AtomicReferenceArray<>(minSize);
        this.bufferSize = minSize;
    }


    /**
     * Inserts the item into the given buffer position.
     *
     * @param pos the index-ed buffer position to write to
     * @param item the new value
     * @return the value of the index-ed position prior to any update
     */
    public final E set(final int pos, final E item) {
        final int realPos = getRealPos(pos);
        final E prevVal = buffer.getAndSet(realPos, item);
        LOGGER.debug("value [{}] placed at index[{}] (real position [{}])", item, pos, realPos);
        return prevVal;
    }


    /**
     * Gets the item from the given position. This operation does not remove any data being held
     * by the buffer, nor does it affect the buffer in any way. This means the data is available
     * for any other processor that may need it as long as the leading processor's head hasn't
     * overwritten it, this should not happen as the current processor's position will still keep
     * control of the data until it no longer needs it
     *
     * @param pos the index to read from
     * @return the value of the index-ed position
     */
    public final E get(final int pos) {
        return buffer.get(getRealPos(pos));
    }


    /**
     * Gets the size of the buffer which will either the default or the value passed to then
     * constructor, whichever is larger.
     *
     * @return the size of the buffer
     */
    public final int size() {
        return buffer.length();
    }


    private int getRealPos(final int pos) {
        if (pos < bufferSize) {
            return pos;
        } else {
            return pos % bufferSize;
        }
    }

}
