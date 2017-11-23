package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.services.SystemProperties;
import com.thecrunchycorner.lmax.storehandler.BufferHandler;
import com.thecrunchycorner.lmax.workflow.ProcessorId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Details about each the processor. This can only be instantiated through the builder.
 */
public class ProcProperties {
    private final ProcessorId processorId;
    private final RingBufferStore buffer;
    private final BufferHandler bufferHandler;
    private int head;
    private int pos;

    private ProcProperties(final ProcessorId processorId, RingBufferStore buffer,
                           final BufferHandler bufferHandler, final int head, int pos) {
        this.processorId = processorId;
        this.buffer = buffer;
        this.bufferHandler = bufferHandler;
        this.head = head;
        this.pos = pos;
    }


    /**
     * Get the ID for this processor.
     *
     * @return ProcessorId
     */
    public ProcessorId getProcessorId() {
        return processorId;
    }


    /**
     * Get the buffer this processor operates on.
     *
     * @return RingBufferStore
     */
    public final RingBufferStore getBuffer() {
        return buffer;
    }


    public BufferHandler getBufferHandler() {
        return bufferHandler;
    }


    /**
     * The position of the buffer head we can read/write to.
     *
     * @return head position
     */
    public final int getHead() {
        return head;
    }



    /**
     * Update the position of the buffer head we can read/write to.
     *
     * @param head position
     */
    public final void setHead(int head) {
        this.head = head;
    }


    /**
     * Get the current position this processor is working on, not other processor with a higher
     * sequence processorId (which denotes lower priority) may go past this position.
     *
     * @return the position
     */
    public int getPos() {
        return pos;
    }


    /**
     * Update this processor's position, we have finished working with this cell and can release
     * it for lower priority processors to work on, the move can be a single cell or a block of
     * them if a batch process was executed.
     *
     * @param pos where to move the position pointer to
     */
    public void setPos(int pos) {
        this.pos = pos;
    }


    /**
     * Builder used to instantiate ProcProperties.
     */
    public static class Builder {
        private static final Logger LOGGER = LogManager.getLogger(SystemProperties.class);
        private ProcessorId processorId;
        private RingBufferStore buffer;
        private BufferHandler handler;
        private int initialHead;


        /**
         * Sets the ID of the processor accessing the buffer.
         *
         * @param procId the Processor's ID
         * @return a builder to carry on building
         */
        public final Builder setProcessorId(final ProcessorId procId) {
            if (procId == null) {
                LOGGER.error("Argument cannot be null: procId");
                throw new IllegalArgumentException("Argument cannot be null");
            } else {
                this.processorId = procId;
                return this;
            }

        }


        /**
         * Sets the buffer.
         *
         * @param buffer The buffer to be accessed
         * @return a builder to carry on building
         */
        public final Builder setBuffer(final RingBufferStore buffer) {
            if (buffer == null) {
                LOGGER.error("Argument cannot be null: buffer");
                throw new IllegalArgumentException("Argument cannot be null");
            } else {
                this.buffer = buffer;
                return this;
            }
        }


        /**
         * Sets the Handler (reader / writer).
         *
         * @param handler the handler
         * @return a builder to carry on building
         */
        public final Builder setHandler(final BufferHandler handler) {
            if (handler == null) {
                LOGGER.error("Argument cannot be null: handler");
                throw new IllegalArgumentException("Argument cannot be null");
            } else {
                this.handler = handler;
                return this;
            }
        }


        /**
         * How far can we go to when we first start. In truth, only the leading reader/writer
         * will be non-zero in which case its value should be set to the size of the buffer.
         *
         * @param initialHead the highest buffer position we can read/write to
         * @return a builder to carry on building
         */
        public final Builder setInitialHead(final int initialHead) {
            if (initialHead < 0) {
                LOGGER.error("Argument cannot less than zero: initialHead");
                throw new IllegalArgumentException("Argument cannot be < 0");
            } else {
                this.initialHead = initialHead;
                return this;
            }
        }


        /**
         * Instantiate this set of properties.
         *
         * @return newly created properties object
         */
        public final ProcProperties createProcProperties() {
            return new ProcProperties(processorId, buffer, handler, initialHead, 0);
        }
    }

}
