package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;


/**
 * Details about each the processor. This must be instantiated through the
 * builder.
 */
public class ProcProperties {
    private final RingBufferStore buffer;
    private final ProcessorId procType;
    private final ProcessorId leadProcType;
    private final int head;

    private ProcProperties(final RingBufferStore buffer, final ProcessorId procType,
                             final ProcessorId leadProcType, final int initialHead) {
        this.buffer = buffer;
        this.procType = procType;
        this.leadProcType = leadProcType;
        this.head = initialHead;
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
        return procType;
    }


    /** Get the ID for the processor this processor has to wait on, to make sure we don't read
     * push data onto the BufferStore ahead of it being generated or read by the lead processor.
     *
     * @return ProcessorId
     */
    public final ProcessorId getLeadProc() {
        return leadProcType;
    }


    /** The position of the buffer head we can read/write to.
     *
     * @return head position
     */
    public final int getHead() {
        return head;
    }


    /** Builder used to instantiate ProcProperties.
     */
    public static class Builder {
        private RingBufferStore buffer;
        private ProcessorId proc;
        private ProcessorId leadProc;
        private int head;


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
            this.proc = proc;
            return this;
        }


        /** Id of the processor we are following so we don't jump ahead of it to avoid reading
         * stale data or loose our data somewhere as it will be overwrite.
         *
         * @param leadProc the Processor's ID
         */
        public final Builder setLeadProc(final ProcessorId leadProc) {
            this.leadProc = leadProc;
            return this;
        }


        /** How far can we go to when we first start. In truth only the
         * leading reader/writer will ever be non-zero in which case this value should
         * be set to the size of the buffer
         *
         * @param head the highest buffer position we can read/write to
         */
        public final Builder setHead(final int head) {
            this.head = head;
            return this;
        }


        /** Instantiate this set of properties.
         *
         * @return newly created properties object
         */
        public final ProcProperties createProcProperties() {
            return new ProcProperties(buffer, proc, leadProc, head);
        }
    }

}
