package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.storehandler.BufferReader;
import com.thecrunchycorner.lmax.storehandler.BufferWriter;
import com.thecrunchycorner.lmax.storehandler.Reader;
import com.thecrunchycorner.lmax.storehandler.Writer;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.UnaryOperator;


/**
 * Details about each the processor. This can only be instantiated through the builder.
 */
public class ProcProperties {
    private final int id;
    private final int priority;
    private final Reader<Message> reader;
    private final Writer<Message> writer;
    private int head;
    private int pos;
    private UnaryOperator<Message> process;

    private ProcProperties(final int id, final int priority,
                           final Reader<Message> reader, final Writer<Message> writer,
                           final int head, int pos, UnaryOperator<Message> process) {
        this.id = id;
        this.priority = priority;
        this.reader = reader;
        this.writer = writer;
        this.pos = pos;
        this.head = head;
        this.process = process;

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
     * sequence processorPriorities (which denotes lower priority) may go past this position.
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

    public UnaryOperator<Message> getProcess() {
        return process;
    }

    public void setProcess(UnaryOperator<Message> process) {
        this.process = process;
    }

    /**
     * Builder used to instantiate ProcProperties.
     */
    public static class Builder {
        private static HashSet<Integer> uniqueIds = new HashSet<>();
        private static HashSet<Integer> multiplePriorities = new HashSet<>();
        private int id;
        private int priority;
        private Reader<Message> reader = null;
        private Writer<Message> writer = null;
        private int initialHead = -1;
        private UnaryOperator<Message> process;

        public final Builder setId(int id) {
            if (uniqueIds.add(id)) {
                throw new IllegalArgumentException("Id already assigned to processor");
            }
            this.id = id;
            return this;
        }

        public final Builder setPriority(int priority) {
            multiplePriorities.add(priority);
            this.priority = priority;
            return this;
        }

        /**
         * Sets the Buffer reader.
         *
         * @param reader the reader
         * @return a builder to carry on building
         */
        public final Builder setReader(final Reader reader) {
            if (reader == null) {
                throw new IllegalArgumentException("Buffer reader cannot be null");
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
        public final Builder setWriter(final Writer<Message> writer) {
            if (writer == null) {
                throw new IllegalArgumentException("Buffer writer cannot be null");
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
                throw new IllegalArgumentException("Argument cannot less than zero: initialHead");
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
         * configuration has been set this consists of: ProcessorPriorities, Buffer, Reader or
         * Writer or
         * both, an initial head value to process up to
         */
        public final ProcProperties createProcProperties() throws IllegalStateException{
            if (multiplePriorities.size() < 2) {
                throw new IllegalStateException("Multiple processor priorities required");
            }
            if (reader == null || writer == null) {
                throw new IllegalStateException("Reader and writer must be specified");
            }
            if (initialHead < 0) {
                throw new IllegalStateException("Invalid value for initial head");
            }
            if (process == null) {
                throw new IllegalStateException("Processing function required");
            }
            return new ProcProperties(id, priority, reader, writer, initialHead, 0, process);
        }
    }

}
