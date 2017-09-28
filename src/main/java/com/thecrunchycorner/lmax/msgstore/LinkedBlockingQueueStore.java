package com.thecrunchycorner.lmax.msgstore;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This queue is used by disruptors to read from prior to passing onto the buffer for processing
 * or once the processing is complete and ready to be picked/ sent to external resources.
 *
 * @param <E> the type of the contents held by the queue.
 *
 * <p>The queue carries out no checks on the data being inserted besides the type checks carried out
 *      by the generics framework.</p>
 */
public class LinkedBlockingQueueStore<E> implements Store<E> {
    private static final Logger LOGGER = LogManager.getLogger(LinkedBlockingQueueStore.class);

    private final transient ConcurrentLinkedQueue<E> queue;

    public LinkedBlockingQueueStore() {
        queue = new ConcurrentLinkedQueue<>();
    }


    /**
     * Adds an item to the Queue.
     *
     * @param item item to add to the tail of the queue
     * @return true if successful
     * @throws IllegalArgumentException if item is null
     */
    public final boolean add(final E item) {
        if (item == null) {
            LOGGER.error("Argument cannot be null, message commit failed");
            throw new IllegalArgumentException("Argument cannot be null");
        } else {
            return queue.add(item);
        }
    }


    /**
     * Takes an item from the Queue.
     *
     * @return the head of of this queue or null if empty
     */
    public final E take() {
        return queue.poll();
    }

}
