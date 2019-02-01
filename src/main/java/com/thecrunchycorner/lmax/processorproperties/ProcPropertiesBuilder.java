package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.handlers.Reader;
import com.thecrunchycorner.lmax.handlers.Writer;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.UnaryOperator;


/**
 * Details about each the processor. These can only be instantiated through the builder.
 */
public class ProcPropertiesBuilder {
    private static HashSet<Integer> uniqueIds = new HashSet<>();
    private int id = -1;
    private int procId = -1;
    private int priority = -1;
    private Reader reader = null;
    private Writer writer = null;
    private int initialHead = -1;
    private UnaryOperator<Message> process = null;
    private boolean external = false;

    public final ProcPropertiesBuilder setId(int id) {
        if (!uniqueIds.add(id)) {
            throw new IllegalArgumentException("ID already assigned to processor. id: " + id);
        }
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative. id: " + id);
        }
        this.id = id;
        return this;
    }

    public final ProcPropertiesBuilder setProcId(int procId) {
        if (procId < 0) {
            throw new IllegalArgumentException("Processor ID cannot be negative. id: " + procId);
        }
        this.procId = procId;
        return this;
    }


    // lower values have higher
    public final ProcPropertiesBuilder setPriority(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException("Priority cannot be negative. id: " + id);
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
    public final ProcPropertiesBuilder setReader(final Reader reader) {
        Objects.requireNonNull(reader, "Buffer reader cannot be null. id: " + id);
        this.reader = reader;
        return this;
    }


    /**
     * Sets the Buffer writer.
     *
     * @param writer the writer
     * @return a builder to carry on building
     */
    public final ProcPropertiesBuilder setWriter(final Writer writer) {
        Objects.requireNonNull(writer, "Buffer writer cannot be null. id: " + id);
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
    public final ProcPropertiesBuilder setInitialHead(final int initialHead) {
        if (initialHead < 0) {
            throw new IllegalArgumentException("Initial head cannot be negative. id: " + id);
        }
        this.initialHead = initialHead;
        return this;
    }

    public final ProcPropertiesBuilder setProcess(UnaryOperator<Message> process) {
        this.process = process;
        return this;
    }

    public final ProcPropertiesBuilder setExternal(boolean external) {
        this.external = external;
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
        if (procId == -1) {
            throw new IllegalStateException("Missing property: procId. id: " + id);
        }
        if (priority == -1) {
            throw new IllegalStateException("Missing property: priority. id: " + id);
        }
        if (reader == null && writer == null) {
            throw new IllegalStateException("Invalid configuration: reader or writer must be " +
                    "configured. id: " + id);
        }
        if (reader != null && writer != null) {
            throw new IllegalStateException("Invalid configuration: reader or writer must be " +
                    "configured not both. id: " + id);
        }
        if (reader != null && process == null) {
            throw new IllegalStateException("Missing property in primary (reader): process. id: " + id);
        }
        ProcProperties props = new ProcProperties(this);
        reset();
        return props;
    }

    private void reset() {
        id = -1;
        procId = -1;
        priority = -1;
        reader = null;
        writer = null;
        initialHead = -1;
        process = null;
        external = false;
    }

    public int getId() {
        return id;
    }

    public int getProcId() {
        return procId;
    }

    public int getPriority() {
        return priority;
    }

    public Reader getReader() {
        return reader;
    }

    public Writer getWriter() {
        return writer;
    }

    public int getInitialHead() {
        return initialHead;
    }

    public UnaryOperator<Message> getProcess() {
        return process;
    }

    public boolean isExternal() {
        return external;
    }
}
