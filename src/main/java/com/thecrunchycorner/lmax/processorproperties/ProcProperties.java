package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.storehandler.BufferReader;
import com.thecrunchycorner.lmax.storehandler.BufferWriter;
import com.thecrunchycorner.lmax.workflow.ProcessorId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Details about each the processor. This can only be instantiated through the builder.
 */
public class ProcProperties {
    private final ProcessorId processorId;
    private final BufferReader<Message> reader;
    private final BufferWriter<Message> writer;
    private int head;
    private int pos;

    private ProcProperties(final ProcessorId processorId, final BufferReader<Message> reader,
                           final BufferWriter<Message> writer, final int head, int pos) {
        this.processorId = processorId;
        this.reader = reader;
        this.writer = writer;
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


    public BufferReader<Message> getReader() {
        return reader;
    }

    public BufferWriter<Message> getWriter() {
        return writer;
    }

    /**
     * The position of the buffer head we can read/write to.
     *
     * @return head position
     */
    final int getHead() {
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
        private static final Logger LOGGER = LogManager.getLogger(Builder.class);
        private ProcessorId processorId = null;
        private BufferReader<Message> reader = null;
        private BufferWriter<Message> writer = null;
        private int initialHead = -1;


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
            }
            this.processorId = procId;
            return this;

        }


        /**
         * Sets the Buffer reader.
         *
         * @param reader the reader
         * @return a builder to carry on building
         */
        public final Builder setReader(final BufferReader<Message> reader) {
            if (reader == null) {
                LOGGER.error("Buffer reader cannot be null");
                throw new IllegalArgumentException("Argument cannot null");
            }
            this.reader = reader;
            return this;
        }


        /**
         * Sets the Buffer writer.
         *
         * @param writer the writer
         * @return a builder to carry on building
         */
        public final Builder setWriter(final BufferWriter<Message> writer) {
            if (writer == null) {
                LOGGER.error("Buffer writer cannot be null");
                throw new IllegalArgumentException("Argument cannot null");
            }
            this.writer = writer;
            return this;
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
            }
            this.initialHead = initialHead;
            return this;
        }


        /**
         * Instantiate this set of properties.
         *
         * @return newly created properties object
         * @throws IllegalStateException If the builder is called before the minimum required
         * configuration has been set this consists of: ProcessorId, Buffer, Reader or Writer or
         * both, an initial head value to process up to
         */
        public final ProcProperties createProcProperties() {
            if (processorId == null) {
                LOGGER.error("ProcessorId must be specified");
                throw new IllegalStateException("ProcessorId must be specified");
            }
            if (reader == null && writer == null) {
                LOGGER.error("Reader or writer or both must be specified");
                throw new IllegalStateException("Reader or writer or both must be specified");
            }
            if (initialHead < 0) {
                LOGGER.error("Initial head not specified");
                throw new IllegalStateException("Initial head not specified");
            }


            return new ProcProperties(processorId, reader, writer, initialHead, 0);
        }
    }

}
