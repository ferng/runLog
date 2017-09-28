package com.thecrunchycorner.lmax.ringbufferaccess.enums;

/** Identifies the types a disruptor consists of. Each processor is assigned a priority with 1
 * being the highest.
 *
 * <p>Each producer must have a unique priority as no two processors can write to the the same
 * queue simultaneously. Consumers on the other hand can share priorities as reading from a store
 * can be done concurrently, this allows concurrent journaling, and marshalling for example.</p>
 *
 * <p>You may choose not touse a specific processor, maybe journalling provides all the
 * resilience you need. Or maybe you don't nee auditing.
 * </p>*/
public enum ProcessorId {
    /** Read data from the outside world and place it on an internal queue. */
    IN_Q_RECEIVE(1),
    /** Unmarshall data from the queue, prep and place it on a RingBufferStore. */
    IN_UNMARSHALL(2),
    /** Replicates data placed on a RingBufferStore to another RingBufferStore (for HA). */
    IN_REPLICATE(2),
    /** Journal write to RingBufferStore in case of fatal crash to reply data. */
    IN_JOURNAL(2),
    /** Audit a subset of data from each record. */
    IN_AUDIT(2),
    /** Read, process and Write data on a RingBufferStore. */
    BUSINESS_PROCESSOR(3),
    /** Read data from a RingBufferStore, Marshall it and place it on an outbound queue. */
    OUT_MARSHALL(4),
    /** Read data from the queue and send it to the outside world. */
    OUT_Q_SEND(6);

    private final int priority;

    ProcessorId(final int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
