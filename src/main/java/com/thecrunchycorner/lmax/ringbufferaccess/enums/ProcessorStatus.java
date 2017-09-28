package com.thecrunchycorner.lmax.ringbufferaccess.enums;

/** Defines the current status for each processor.
 */
public enum ProcessorStatus {
    /** Initialization complete. */
    INITIALIZED,
    /** Processor has started and is ready to start work. */
    STARTED,
    /** Processor is busy processing data. */
    BUSY,
    /** Processor has completed it's work and doesn't have anything else to do. */
    WAITING
}
