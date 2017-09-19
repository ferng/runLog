package com.thecrunchycorner.lmax.msgstore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @param <E> - the type of the contents held by the queue.
 *
 *              The queue carries out no checks on the data being inserted besides the type checks carried out by the generics framework.
 */
public class LinkedBlockingQueueStore<E> implements Store<E>{
    private static Logger logger = LogManager.getLogger(LinkedBlockingQueueStore.class);

    private ConcurrentLinkedQueue queue;

    public LinkedBlockingQueueStore() {
        queue = new ConcurrentLinkedQueue();
    }


    /**
     * @param item - item to add to the tail of the queue
     * @return - true if successful
     * @throws NullPointerException - if item is null
     */
    public final boolean add(E item) {
        if (item == null) {
            logger.error("Argument cannot be null, message commit abandoned");
            throw new IllegalArgumentException("Argument cannot be null");
        } else {
            return queue.add(item);
        }
    }

    /**
     * @return - the head of of this queue or
     */
    public final E take() {
        return (E) queue.poll();
    }

}
