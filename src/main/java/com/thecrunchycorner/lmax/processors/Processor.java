package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.buffer.OpStatus;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A collection of data and functions which define a processor in LMAX
 * <p>
 * <p>
 * Each processor gets data (from a buffer or external system), carries out some computation on
 * the data, then writes the data to the same buffer in the same cell, eg. unmarshalling, or to a
 * separate buffer (as buffers can be chained or in the case of a replicator), to an external
 * system (a jms queue) or nowhere at all (in which case the processor could simply log or
 * journal the data.
 * </p>
 */
public class Processor {
    private static Logger LOGGER = LogManager.getLogger(Processor.class);
    private ProcessorStatus status = ProcessorStatus.INITIALIZED;
    private volatile boolean interrupt = false;
    private ProcProperties primary;
    private ProcProperties secondary = null;


    /**
     * Some processors only have a primary set of properties, for example they read data then the
     * processing stage logs something to LOG4J
     * @param primary set of properties for the reader
     */
    public Processor(ProcProperties primary) {
        this.primary = primary;
    }


    /**
     * Other processors (most of them) have a separate writer
     * @param primary the reader which gets data and then carries out the processing
     * @param secondary the writer
     */
    public Processor(ProcProperties primary, ProcProperties secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }


    /**
     * A function which carries out the the processing for this processor (essentially looping
     * round the buffer reading data processing it then optionally writing it somewhere
     */
    public Supplier<ProcessorStatus> processLoop = () -> {
        while (!interrupt) {
            if (primary.getPos() == primary.getHead() || primary.isExternal()) {
                if (headUpdated(primary)) {
                    LOGGER.debug("processing stuff by {}", primary.getProcId());
                    processPending();
                }
            }
        }
        status = ProcessorStatus.SHUTDOWN;
        LOGGER.debug("Processor {} shutdown", primary.getId());
        return ProcessorStatus.SHUTDOWN;
    };


    private boolean headUpdated(ProcProperties properties) {
        int leadPos = ProcessorWorkflow.getLeadPos(properties.getBufferId(),
                properties.getStage());

        if (properties.getHead() < leadPos) {
            properties.setHead(leadPos);
            return true;
        }
        return false;
    }


    /**
     * Only used by processLoop to process a single data item
     * @return the outcome of the operation
     */
    OpStatus processPending() {
        Message in = readMessage();
        if (in == null || in.getPayload() == null) {
            return OpStatus.HEADER_REACHED;
        } else {
            Message msg = processMessage(in);
            LOGGER.debug("Processing {}", msg.getPayload());
            if (secondary == null) {
                return OpStatus.NO_WRITE_OP;
            }
            return writeMessage(msg);
        }
    }


    /**
     * Shutdown this processor
     */
    public void shutdown() {
        interrupt = true;
    }


    /**
     * Read the next message available for this processor
     * @return the next message (data from a cell in the ring buffer
     */
    Message readMessage() {
        Message msg = primary.getReader().read(primary.getPos());

        primary.movePos();
        return msg;
    }


    private Message processMessage(Message msg) {
        return primary.getProcess().apply(msg);
    }


    /**
     * Write a message to the next available cell in the ring buffer this processor has access to
     * @param msg the message to be written
     * @return the outcome of the operation
     */
    OpStatus writeMessage(Message msg) {
        secondary.getWriter().write(secondary.getPos(), msg);
        secondary.movePos();
        return OpStatus.WRITE_SUCCESS;
    }


    /**
     * Get the current status of this processor
     * @return the current status of this processor
     */
    public ProcessorStatus getStatus() {
        return status;
    }
}
