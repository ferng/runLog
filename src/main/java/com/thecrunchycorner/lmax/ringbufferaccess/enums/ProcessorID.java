package com.thecrunchycorner.lmax.ringbufferaccess.enums;

/** Identifies the types a disruptor consists of. Each processor is assigned a priority with 1
 * being the highest. Each producer (producing processor) must have a unique priority as no two
 * processors can write to the the same queue simultaneously. Consumers however can share
 * priorities as reading from a store can be done concurrently, this allows concurrent journaling
 * and marshelling for example. */
public enum ProcessorID {
    IN_Q_RECEIVER,
    IN_UNMARSHALER,
    IN_BUSINESS_PROCESSOR,
    OUT_BUSINESS_PROCESSOR,
    OUT_MARSHALER,
    OUT_Q_SENDER,
}
