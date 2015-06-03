package com.thecrunchycorner.runlog.ringbuffer;

import com.thecrunchycorner.runlog.services.SystemProperties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicReferenceArray;


/**
 * @param <E> - the type of the contents held by the buffer.
 *            <p>
 *            The buffer carries out no checks on the data being inserted besides the type checks carried out by the generics framework.
 */
public class RingBuffer<E> {
    private static Logger logger = LogManager.getLogger(RingBuffer.class);

    private AtomicReferenceArray<E> buffer;
    private int modSize;


    /**
     * @param size - the size of the buffer.  Once instantiated it cannot be changed.  If the size requested is less than that specified in threshold.buffer.minimum.size
     *             it will quietly be increased to that threshold.
     */
    public RingBuffer(int size) {
        int minSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));
        if (size < minSize) {
            logger.warn("Suggested buffer size is too small, defaulting to minimum {}.", minSize);
            size = minSize;
        }
        buffer = new AtomicReferenceArray<E>(size);
        this.modSize = size;
    }


    public void put(int pos, E item) {
        int realPos = pos % modSize;
        if (buffer.get(modSize - 1) != null) {
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
