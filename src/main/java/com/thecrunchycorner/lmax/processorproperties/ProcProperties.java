package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.storeaccessor.BufferAccessor;
import com.thecrunchycorner.lmax.workflow.ProcessorId;


/**
 * Details about each the processor. This must be instantiated through the
 * builder.
 */
public class ProcProperties {
    private final ProcessorId processorId;
    private final RingBufferStore buffer;
    private final BufferAccessor accessor;
    private int head;
    private int pos;

    private ProcProperties(final ProcessorId procType, final RingBufferStore buffer,
                             final BufferAccessor accessor, final int head, int pos) {
        this.processorId = procType;
        this.buffer = buffer;
        this.accessor = accessor;
        this.head = head;
        this.pos = pos;
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

    public BufferAccessor getAccessor() {
        return accessor;
    }

    /** Builder used to instantiate ProcProperties.
     */
    public static class Builder {
        private ProcessorId processorId;
        private RingBufferStore buffer;
        private BufferAccessor accessor;
        private int initialHead;


        /** set ID of the processor accessing the buffer.
         *
         * @param proc the Processor's ID
         */
        public final Builder setProcessor(final ProcessorId proc) {
            this.processorId = proc;
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

        public final Builder setAccessor(final BufferAccessor accessor) {
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
            return new ProcProperties(processorId, buffer, accessor, initialHead, 0);
        }
    }

}
