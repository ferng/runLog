package com.thecrunchycorner.lmax.processorproperties;

import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.handlers.Reader;
import com.thecrunchycorner.lmax.handlers.Writer;
import java.util.function.UnaryOperator;


/**
 * Details about properties used by the processor. Processors can only be instantiated through the
 * builder.
 */
public class ProcProperties {
    private final int id;
    private final int procId;
    private final int stage;
    private final Reader reader;
    private final Writer writer;
    private int head;
    private int pos;
    private UnaryOperator<Message> process;
    private boolean external;

    ProcProperties(ProcPropertiesBuilder builder) {
        this.id = builder.getId();
        this.procId = builder.getProcId();
        this.stage = builder.getStage();
        this.reader = builder.getReader();
        this.writer = builder.getWriter();
        this.head = builder.getInitialHead();
        this.process = builder.getProcess();
        this.external = builder.isExternal();
        this.pos = 0;
    }


    /** Returns the property's id
     *
     * @return the property's id
     */
    public int getId() {
        return id;
    }


    /** Returns the processors's id
     *
     * @return the processors's id
     */
    public int getProcId() {
        return procId;
    }


    /** Returns the property's stage
     *
     * @return the property's stage
     */
    public int getStage() {
        return stage;
    }


    /** Returns the property's reader
     *
     * @return the property's reader
     */
    public Reader getReader() {
        return reader;
    }


    /** Returns the property's writer
     *
     * @return the property's writer
     */
    public Writer getWriter() {
        return writer;
    }


    /** Returns the property's head
     *
     * @return the property's head
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
     * sequence processorStage (which denotes lower stage) may go past this position.
     *
     * @return the position
     */
    public int getPos() {
        return pos;
    }


    /**
     * Update this processor's position, we have finished working with this cell and can release
     * it for lower stage processors to work on, the move can be a single cell or a block of
     * them if a batch process was executed.
     *
     * @param pos where to move the position pointer to
     */
    public void setPos(int pos) {
        this.pos = pos;
    }


    /**
     * Get the buffer's id for the buffer this processor is working on
     *
     * @return the buffer's id
     */
    public int getBufferId() {
        if (reader != null) {
            return reader.getBufferId();
        } else {
            return writer.getBufferId();
        }
    }


    /**
     * Move this processor's reader / writer one along
     */
    public void movePos() {
        pos++;
    }


    /**
     * Returns the property's process
     *
     * @return the property's process
     */
    public UnaryOperator<Message> getProcess() {
        return process;
    }


    /**
     * Returns whether the property is for an external reader / writer
     *
     * @return whether the property is for an external reader / writer
     */
    public boolean isExternal() {
        return external;
    }
}
