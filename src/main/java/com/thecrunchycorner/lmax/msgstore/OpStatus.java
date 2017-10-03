package com.thecrunchycorner.lmax.msgstore;

/**
 * The outcome of the operation carried out on a store.
 */
public enum OpStatus {
    /** Message written to a store successfully. */
    READ_SUCCESS,
    /** Message read from a store successfully. */
    WRITE_SUCCESS,
    /** No more messages left in the store to process. */
    HEADER_REACHED,
    /** Error accessing store This will be fatal. */
    ERROR

}

