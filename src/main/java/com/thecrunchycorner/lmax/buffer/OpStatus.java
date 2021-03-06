package com.thecrunchycorner.lmax.buffer;

/**
 * The outcome of the operation carried out on a store.
 */
public enum OpStatus {
    /**
     * Message written to a store successfully.
     */
    WRITE_SUCCESS,
    /**
     * No more messages left in the store to process.
     */
    HEADER_REACHED,
    /**
     * There is no buffer write operation (could be a logger, or replicator or sending it to
     * something else.
     */
    NO_WRITE_OP,
    /**
     * Error accessing store This will be fatal.
     */
    ERROR


}

