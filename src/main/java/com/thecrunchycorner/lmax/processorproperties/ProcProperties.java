package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.storehandler.BufferHandler;
import com.thecrunchycorner.lmax.workflow.ProcessorId;


/**
 * Details about each the processor. This can only be instantiated through the builder.
 */
public class ProcProperties {
    private final ProcessorId processorId;
    private final ProcessorId leadProcessorId;
    private final RingBufferStore buffer;
    private final BufferHandler bufferHandler;
    private int head;
    private int pos;

    private ProcProperties(final ProcessorId processorId, final ProcessorId leadProcessorId,
                           RingBufferStore buffer, final BufferHandler bufferHandler,
                           final int head, int pos) {
        this.processorId = processorId;
        this.leadProcessorId = leadProcessorId;
        this.buffer = buffer;
        this.bufferHandler = bufferHandler;
        this.head = head;
        this.pos = pos;
    }


    /** Get the ID for this processor.
     *
     * @return ProcessorId
     */
    public ProcessorId getProcessorId() {
        return processorId;
    }


    /** Get the ID for this processor's leding processor as we mustn't operate beyong it.
     *
     * @return ProcessorId
     */
    public ProcessorId getLeadProcessorId() {
        return leadProcessorId;
    }


    /** Get the buffer this processor operates on.
     *
     * @return RingBufferStore
     */
    public final RingBufferStore getBuffer() {
        return buffer;
    }


    public BufferHandler getBufferHandler() {
        return bufferHandler;
    }

    /** The position of the buffer head we can read/write to.
     *
     * @return head position
     */
    public final int getHead() {
        return head;
    }


    /** Update the position of the buffer head we can read/write to.
     *
     * @param head position
     */
    public final void setHead(int head) {
        this.head = head;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }


    /** Builder used to instantiate ProcProperties.
     */
    public static class Builder {
        private ProcessorId processorId;
        private ProcessorId leadProcessorId;
        private RingBufferStore buffer;
        private BufferHandler accessor;
        private int initialHead;


        /** set ID of the processor accessing the buffer.
         *
         * @param procId the Processor's ID
         */
        public final Builder setProcessorId(final ProcessorId procId) {
            this.processorId = procId;
            return this;
        }


        /** set ID of the processor this processor followa.
         *
         * @param procId the Processor's ID
         */
        public final Builder setLeadProcessorId(final ProcessorId procId) {
            this.leadProcessorId = procId;
            return this;
        }


        /** Sets the buffer.
         *
         * @param buffer The buffer to be accessed
         */
        public final Builder setBuffer(final RingBufferStore buffer) {
            this.buffer = buffer;
            return this;
        }


        public final Builder setAccessor(final BufferHandler accessor) {
            this.accessor = accessor;
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
            return new ProcProperties(processorId, leadProcessorId, buffer, accessor, initialHead,
                    0);
        }
    }

}
