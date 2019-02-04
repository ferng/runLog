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
    SHUTDOWN,
    /**
     * Processor crashed for some reason.
     */
}
