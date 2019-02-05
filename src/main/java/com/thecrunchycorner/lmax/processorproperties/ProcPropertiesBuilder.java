package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.handlers.Reader;
import com.thecrunchycorner.lmax.handlers.Writer;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.UnaryOperator;


/**
 * Details about properties used by the processor. Processors can only be instantiated through the
 * builder.
 */
public class ProcPropertiesBuilder {
    private static HashSet<Integer> uniqueIds = new HashSet<>();
    private int id = -1;
    private int procId = -1;
    private int stage = -1;
    private Reader reader = null;
    private Writer writer = null;
    private int initialHead = -1;
    private UnaryOperator<Message> process = null;
    private boolean external = false;

    /**
     * Set the id property using this builder for this set of properties
     *
     * @param id that will be assigned to this set of properties
     * @return a ProcPropertiesBuilder with the id set
     * @throws IllegalArgumentException if id has already been used or the id is negative
     */
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


    /**
     * Set the id property using this builder for the processor this set of properties is for
     *
     * @param procId the id that will be assigned to the processor
     * @return a ProcPropertiesBuilder with the procId set
     * @throws IllegalArgumentException if id is negative
     */
    public final ProcPropertiesBuilder setProcId(int procId) {
        if (procId < 0) {
            throw new IllegalArgumentException("Processor ID cannot be negative. id: " + procId);
        }
        this.procId = procId;
        return this;
    }


    /**
     * Set the stage property using this builder for the this set of properties.
     *
     * <p>
     * <p>
     * Multiple properties and processors can be at the same stage. The stage is used to
     * group together sets of behaviour, for example a logger and replicator can work in parallel
     * without harming each other, as long as the data in the buffer remains unaffected multiple
     * processors can work in parallel.
     * </p>
     * <p>
     * Processors within a stage wait for earlier processes to release a buffer cell so they can
     * gain access to that cell: stage 0 processors must move on before stage 1 processors can
     * get to that data and so on.
     * </p>
     * <p>
     * As it's a circular buffer stage 0 will then wait until the last stage has released that
     * cell before it can get to it.
     * </p>
     *
     * @param stage the stage this processor belongs to.
     * @return a ProcPropertiesBuilder with the stage set
     * @throws IllegalArgumentException if the stage is negative
     */
    public final ProcPropertiesBuilder setStage(int stage) {
        if (stage < 0) {
            throw new IllegalArgumentException("Stage cannot be negative. id: " + id);
        }
        this.stage = stage;
        return this;
    }


    /**
     * Sets the Buffer reader.
     *
     * @param reader the reader
     * @return a ProcPropertiesBuilder with the reader set
     * @throws NullPointerException if the reader is null
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
     * @return a ProcPropertiesBuilder with the writer set
     * @throws NullPointerException if the writer is null
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
     * @return a ProcPropertiesBuilder with the initialHead set
     * @throws IllegalArgumentException if the head is negative
     */
    public final ProcPropertiesBuilder setInitialHead(final int initialHead) {
        if (initialHead < 0) {
            throw new IllegalArgumentException("Initial head cannot be negative. id: " + id);
        }
        this.initialHead = initialHead;
        return this;
    }


    /**
     * This provides a function to the system which does the actual work for the processor. This
     * can be anything from true mathematical functions to reading from a file or logging the
     * data, in fact anything else that needs to happen to the data.
     *
     * @param process the function to carry out on that data
     * @return a ProcPropertiesBuilder with the process set
     */
    public final ProcPropertiesBuilder setProcess(UnaryOperator<Message> process) {
        this.process = process;
        return this;
    }


    /**
     * This identifies the processor as relying on external resources.
     * <p>
     * <p>
     * For example when de-queuing from a jms resource a reader doesn't rely on the buffer, as
     * such the reader's
     * location on a buffer is irrelevant (important for subsequent users which may otherwise
     * wait on the reader before they can gain access to a cell.
     * </p>
     *
     * @param external true if it is false otherwise
     * @return a ProcPropertiesBuilder with the external flag set
     */
    public final ProcPropertiesBuilder setExternal(boolean external) {
        this.external = external;
        return this;
    }


    /**
     * Instantiate this set of properties.
     *
     * @return newly created properties object
     * @throws IllegalStateException If the builder is called before the minimum required
     * configuration has been set this consists of:
     * id, procId, stage, a reader or a writer, a process in the primary (reader) set of properties.
     */
    public final ProcProperties build() throws IllegalStateException {
        if (id == -1) {
            throw new IllegalStateException("Missing property: ID");
        }
        if (procId == -1) {
            throw new IllegalStateException("Missing property: procId. id: " + id);
        }
        if (stage == -1) {
            throw new IllegalStateException("Missing property: stage. id: " + id);
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
        stage = -1;
        reader = null;
        writer = null;
        initialHead = -1;
        process = null;
        external = false;
    }


    /**
     * Returns the property's id
     *
     * @return the property's id
     */
    int getId() {
        return id;
    }


    /**
     * Returns the processors's id
     *
     * @return the processors's id
     */
    int getProcId() {
        return procId;
    }


    /**
     * Returns the property's stage
     *
     * @return the property's stage
     */
    int getStage() {
        return stage;
    }


    /**
     * Returns the property's reader
     *
     * @return the property's reader
     */
    Reader getReader() {
        return reader;
    }


    /**
     * Returns the property's writer
     *
     * @return the property's writer
     */
    Writer getWriter() {
        return writer;
    }


    /**
     * Returns the property's head
     *
     * @return the property's head
     */
    int getInitialHead() {
        return initialHead;
    }


    /**
     * Returns the property's process
     *
     * @return the property's process
     */
    UnaryOperator<Message> getProcess() {
        return process;
    }


    /**
     * Returns whether the property is for an external reader / writer
     *
     * @return whether the property is for an external reader / writer
     */
    boolean isExternal() {
        return external;
    }
}
