package com.thecrunchycorner.lmax.msgstore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @param <E> the type of the contents held by the queue.
 *
 * The queue carries out no checks on the data being inserted besides the type checks carried out by the generics
 * framework.
 */
public class LinkedBlockingQueueStore<E> implements Store<E>{
    private static final Logger LOGGER = LogManager.getLogger(LinkedBlockingQueueStore.class);

    private final transient ConcurrentLinkedQueue<E> queue;

    public LinkedBlockingQueueStore() {
        queue = new ConcurrentLinkedQueue<>();
    }


    /**
     * @param item item to add to the tail of the queue
     * @return true if successful
     * @throws IllegalArgumentException- if item is null
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
     * @return the head of of this queue or null if empty
     */
    public final E take() {
        return queue.poll();
    }

}
