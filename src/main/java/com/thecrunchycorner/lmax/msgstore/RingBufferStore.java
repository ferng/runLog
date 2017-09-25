package com.thecrunchycorner.lmax.msgstore;

import com.thecrunchycorner.lmax.services.SystemProperties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicReferenceArray;


/**
 * A circular buffer used to exchange data between a disruptor and a processor.
 *
 * @param <E> the type of the contents held by the buffer.
 *
 * The buffer carries out no checks on the data being inserted besides the type checks carried out by the generics
 * framework.
 */
public class RingBufferStore<E> implements Store<E> {
    private static Logger logger = LogManager.getLogger(RingBufferStore.class);

    private AtomicReferenceArray<E> buffer;
    private int size;


    /**
     * @param size the size of the buffer. Once instantiated it cannot be changed. If the size requested is less than
     * that specified in threshold.buffer.minimum.size it will be increased to that threshold.
     */
    public RingBufferStore(int size) {
        int minSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));
        if (size < minSize) {
            logger.warn("Suggested buffer size is too small, defaulting to minimum {}.", minSize);
        } else {
            minSize = size;
        }
        buffer = new AtomicReferenceArray<>(minSize);
        this.size = minSize;
    }


    /**
     * @param pos the index-ed buffer position to write to
     * @param item the new value
     * @return the value of the index-ed position prior to any update
     */
    public final E set(int pos, E item) {
        int realPos = getRealPos(pos);
        E prevVal = buffer.getAndSet(realPos, item);
        logger.debug("value [{}] placed at index[{}] (real position [{}])", item, pos, realPos);
        return prevVal;
    }



    /**
     * @param pos the index to read from
     * @return the value of the index-ed position
     */
    public final E get(int pos) {
        return buffer.get(getRealPos(pos));
    }


    /**
     * @return the size of the buffer
     */
    public final int size() {
        return buffer.length();
    }


    private int getRealPos(int pos) {
        if (pos < size) {
            return pos;
        } else {
            return pos % size;
        }
    }

}
