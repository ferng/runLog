package com.thecrunchycorner.lmax.workflow;

import com.thecrunchycorner.lmax.processors.Processor;

/**
 * Identifies the types a buffer processor consists of.
 *
 * <p>Each processor has an priority, 0 being the highest, it simply means it goes first.
 * Processors with lower priority cannot go past the position a higher-precedence processor is
 * currently writing to or reading from.</p>
 *
 * <p>Each producer must have a unique priority as no two processors can write to the the same
 * queue simultaneously. Consumers on the other hand can share priorities as reading from a store
 * can be done concurrently, this allows concurrent journaling, and marshalling for example.</p>
 *
 * <p>You may choose not to use a specific processor, maybe journalling provides all the
 * resilience you need. Or maybe you don't nee auditing.
 * </p>
 */
public enum ProcessorId {
//    /** Read data from the outside world and place it on an internal queue. */
//    IN_Q_RECEIVE(0),
    /**
     * Unmarshall data from the queue, prep and place it on a RingBufferStore.
     */
    IN_UNMARSHALL(0, new Processor(110)),
    /**
     * Replicates data placed on a RingBufferStore to another RingBufferStore (for HA).
     */
    IN_REPLICATE(1, new Processor(21)),
    /**
     * Journal write to RingBufferStore in case of fatal crash to reply data.
     */
    IN_JOURNAL(1, new Processor(22)),
    /**
     * Audit a subset of data from each record.
     */
    IN_AUDIT(1, new Processor(23)),
    /**
     * Read, process and Write data on a RingBufferStore.
     */
    BUSINESS_PROCESSOR(2, new Processor(15)),
    /**
     * Read data from a RingBufferStore, Marshall it and place it on an outbound queue.
     */
    OUT_MARSHALL(3, new Processor(7));
//    /** Read data from the queue and send it to the outside world. */
//    OUT_Q_SEND(4);

    private final int priority;
    private final Processor processor;

    ProcessorId(final int priority, final Processor processor) {
        this.priority = priority;
        this.processor = processor;
    }

    public int getPriority() {
        return priority;
    }

    public Processor getProcessor() {
        return processor;
    }

}
