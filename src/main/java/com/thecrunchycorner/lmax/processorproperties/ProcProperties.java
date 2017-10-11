package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.workflow.ProcessorId;


/**
 * Details about each the processor. This must be instantiated through the
 * builder.
 */
public class ProcProperties {
    private final RingBufferStore buffer;
    private final ProcessorId processorId;
    private final int initialHead;

    private ProcProperties(final RingBufferStore buffer, final ProcessorId procType,
                             final int initialHead) {
        this.buffer = buffer;
        this.processorId = procType;
        this.initialHead = initialHead;
    }


    /** Get the buffer this processor operates on.
     *
     * @return RingBufferStore
     */
    public final RingBufferStore getBuffer() {
        return buffer;
    }


    /** Get the ID for this processor.
     *
     * @return ProcessorId
     */
    public final ProcessorId getProc() {
        return processorId;
    }


    /** The position of the buffer initialHead we can read/write to.
     *
     * @return initialHead position
     */
    public final int getInitialHead() {
        return initialHead;
    }


    /** Builder used to instantiate ProcProperties.
     */
    public static class Builder {
        private RingBufferStore buffer;
        private ProcessorId processorId;
        private int initialHead;


        /** Sets the buffer.
         *
         * @param buffer The buffer to be accessed
         */
        public final Builder setBuffer(final RingBufferStore buffer) {
            this.buffer = buffer;
            return this;
        }


        /** set ID of the processor accessing the buffer.
         *
         * @param proc the Processor's ID
         */
        public final Builder setProcessor(final ProcessorId proc) {
            this.processorId = proc;
            return this;
        }


        /** How far can we go to when we first start. In truth only the
         * leading reader/writer will ever be non-zero in which case this value should
         * be set to the size of the buffer
         *
         * @param initialHead the highest buffer position we can read/write to
         */
        public final Builder setInitialHead(final int initialHead) {
            this.initialHead = initialHead;
            return this;
        }


        /** Instantiate this set of properties.
         *
         * @return newly created properties object
         */
        public final ProcProperties createProcProperties() {
            return new ProcProperties(buffer, processorId, initialHead);
        }
    }

}
