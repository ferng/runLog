package com.thecrunchycorner.lmax.msgstore;

import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This queue is used by disruptors to retrieve data from the outside world prior to placing onto
 * the buffer for processing or once the processing is complete and ready to be picked up / sent
 * to external resources.
 *
 * @param <E> the type of the contents held by the queue
 * <p>
 * <p>The queue carries out no checks on the data being inserted besides the type checks carried out
 * by the generics framework.</p>
 */
public class Queue<E> {
    private static final Logger LOGGER = LogManager.getLogger(Queue.class);

    private final transient ConcurrentLinkedQueue<E> queue;

    public Queue() {
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
            LOGGER.error("Cannot add null to queue, message commit failed");
            throw new IllegalArgumentException("Cannot add null to queue");
        }
        return queue.add(item);
    }


    /**
     * Takes an item from the Queue.
     *
     * @return the head of of this queue or null if empty
     */
    public final E take() {
        return queue.poll();
    }


    /**
     * Gets the size of the buffer.
     *
     * @return the size of the buffer
     */
    public final int size() {
        return queue.size();
    }
}
