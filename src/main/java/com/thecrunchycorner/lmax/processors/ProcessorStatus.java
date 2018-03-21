package com.thecrunchycorner.lmax.processors;

/**
 * Defines the current status for each processor.
 */
public enum ProcessorStatus {
    /**
     * Initialization complete.
     */
    INITIALIZED,
    /**
     * Processor has started and is ready to start work.
     */
    STARTED,
    /**
     * Processor is busy processing data.
     */
    BUSY,
    /**
     * Processor is wating for some more work to do.
     */
    WAITING,
    /**
     * Processor has completed it's work, cleaned up and has shutdown.
     */
    SHUTDOWN,
    /**
     * Processor crashed for some reason.
     */
    ERROR
}
