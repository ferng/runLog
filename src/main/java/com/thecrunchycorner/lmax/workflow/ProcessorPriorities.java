package com.thecrunchycorner.lmax.workflow;

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
public enum ProcessorPriorities {
    /**
     * Unmarshall data from the queue, prep and place it on a RingBuffer.
     */
    IN_UNMARSHALL(0),
//    /**
//     * Replicates data placed on a RingBuffer to another RingBuffer (for HA).
//     */
//    IN_REPLICATE(1, new Processor(21)),
//    /**
//     * Journal write to RingBuffer in case of fatal crash to reply data.
//     */
//    IN_JOURNAL(1, new Processor(22)),
//    /**
//     * Audit a subset of data from each record.
//     */
//    IN_AUDIT(1, new Processor(23)),
//    /**
//     * Read, process and Write data on a RingBuffer.
//     */
    BUSINESS_PROCESSOR(1);
    /**
     * Read data from a RingBuffer, Marshall it and place it on an outbound queue.
     */
//    OUT_MARSHALL(3, new Processor(7));
////    /** Read data from the queue and send it to the outside world. */


    private final int priority;

    ProcessorPriorities(final int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
