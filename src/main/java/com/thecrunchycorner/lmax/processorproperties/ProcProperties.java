package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.buffer.BufferReader;
import com.thecrunchycorner.lmax.buffer.BufferWriter;
import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.handlers.Reader;
import com.thecrunchycorner.lmax.handlers.Writer;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.UnaryOperator;


/**
 * Details about each the processor. These can only be instantiated through the builder.
 */
public class ProcProperties {
    private final int id;
    private final int priority;
    private final Reader<Message> reader;
    private final Writer<Message> writer;
    private int head;
    private int pos;
    private UnaryOperator<Message> process;

    private ProcProperties(Builder builder) {
        this.id = builder.id;
        this.priority = builder.priority;
        this.reader = builder.reader;
        this.writer = builder.writer;
        this.pos = 0;
        this.head = builder.initialHead;
        this.process = builder.process;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public Reader<Message> getReader() {
        return reader;
    }

    public Writer<Message> getWriter() {
        return writer;
    }

    /**
     * The position of the buffer head we can read/write to.
     *
     * @return head position
     */
    public int getHead() {
        return head;
    }


    /**
     * Update the position of the buffer head we can read/write to.
     *
     * @param head position
     */
    public void setHead(int head) {
        this.head = head;
    }


    /**
     * Get the current position this processor is working on, not other processor with a higher
     * sequence processorPriorities (which denotes lower priority) may go past this position.
     *
     * @return the position
     */
    public int getPos() {
        return pos;
    }

    public int getBufferId() {
        if (reader != null) {
            return reader.getBufferId();
        } else {
            return writer.getBufferId();
        }
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

    public void movePos() {
        pos++;
    }

    public UnaryOperator<Message> getProcess() {
        return process;
    }

    /**
     * Builder used to instantiate ProcProperties.
     */
    public static class Builder {
        private static HashSet<Integer> uniqueIds = new HashSet<>();
        private int id = -1;
        private int priority = -1;
        private BufferReader<Message> reader = null;
        private BufferWriter<Message> writer = null;
        private int initialHead = -1;
        private UnaryOperator<Message> process = null;

        public final Builder setId(int id) {
            if (!uniqueIds.add(id)) {
                throw new IllegalArgumentException("ID already assigned to processor");
            }
            if (id < 0) {
                throw new IllegalArgumentException("ID cannot be negative");
            }
            this.id = id;
            return this;
        }

        // lower values have higher
        public final Builder setPriority(int priority) {
            if (priority < 0) {
                throw new IllegalArgumentException("Priority cannot be negative");
            }
            this.priority = priority;
            return this;
        }

        /**
         * Sets the Buffer reader.
         *
         * @param reader the reader
         * @return a builder to carry on building
         */
        public final Builder setReader(final BufferReader<Message> reader) {
            Objects.requireNonNull(reader, "Buffer reader cannot be null");
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
            Objects.requireNonNull(writer, "Buffer writer cannot be null");
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
                throw new IllegalArgumentException("Initial head cannot be negative");
            }
            this.initialHead = initialHead;
            return this;
        }

        public final Builder setProcess(UnaryOperator<Message> process) {
            this.process = process;
            return this;
        }

        /**
         * Instantiate this set of properties.
         *
         * @return newly created properties object
         * @throws IllegalStateException If the builder is called before the minimum required
         * configuration has been set this consists of: Buffer, Reader or
         * Writer or
         * both, an initial head value to process up to
         */
        public final ProcProperties build() throws IllegalStateException {
            if (id == -1) {
                throw new IllegalStateException("Missing property: ID");
            }
            if (priority == -1) {
                throw new IllegalStateException("Missing property: priority");
            }
            if (initialHead == -1) {
                throw new IllegalStateException("Missing property: initialHead");
            }
            if (reader == null && writer == null) {
                throw new IllegalStateException("Invalid configuration: reader or writer must be " +
                        "configured");
            }
            if (!(reader == null) && !(writer == null)) {
                throw new IllegalStateException("Invalid configuration: reader or writer must be " +
                        "configured not both");
            }
            if (process == null) {
                throw new IllegalStateException("Missing property: process");
            }
            return new ProcProperties(this);
        }
    }

}
