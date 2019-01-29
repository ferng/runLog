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
public class ProcProperties {
    private final int id;
    private final int procId;
    private final int priority;
    private final Reader<Message> reader;
    private final Writer<Message> writer;
    private int head;
    private int pos;
    private UnaryOperator<Message> process;
    private boolean external;

    ProcProperties(ProcPropertiesBuilder builder) {
        this.id = builder.getId();
        this.procId = builder.getProcId();
        this.priority = builder.getPriority();
        this.reader = builder.getReader();
        this.writer = builder.getWriter();
        this.head = builder.getInitialHead();
        this.process = builder.getProcess();
        this.external = builder.isExternal();
        this.pos = 0;
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

    public boolean isExternal() {
        return external;
    }
}
