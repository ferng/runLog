package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.buffer.OpStatus;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Processor {
    private static Logger LOGGER = LogManager.getLogger(Processor.class);
    private ProcessorStatus status = ProcessorStatus.INITIALIZED;
    private volatile boolean interrupt = false;
    private ProcProperties primary;
    private ProcProperties secondary;


    public Processor(ProcProperties primary, ProcProperties secondary) {
        this.primary = primary;
        if (secondary == null) {
            this.secondary = primary;
        } else {
            this.secondary = secondary;
        }
    }


    public Supplier<ProcessorStatus> processLoop = () -> {
        while (!interrupt) {
            if (primary.getPos() == primary.getHead()) {
                if (headUpdated(primary)) {
                    LOGGER.debug("processing stuff");
                    processPending();
                } else {
                    LOGGER.debug("waiting for stuff");
                }
            }
        }
        status = ProcessorStatus.SHUTDOWN;
        LOGGER.debug("Processor {} shutdown", primary.getId());
        return ProcessorStatus.SHUTDOWN;
    };

    private boolean headUpdated(ProcProperties properties) {
        int leadPos = ProcessorWorkflow.getLeadPos(properties.getBufferId(), properties.getPriority());
        if (properties.getHead() < leadPos) {
            properties.setHead(leadPos);
            return true;
        }
        return false;
    }

    OpStatus processPending() {
        Message msg = processMessage(readMessage());
        LOGGER.debug(msg.getPayload());
//        return writeMessage(msg);
        return OpStatus.WRITE_SUCCESS;
    }

    public void shutdown() {
        interrupt = true;
    }

    Message readMessage() {
        Message msg = primary.getReader().read(primary.getPos());

        primary.movePos();
        return msg;
    }


    Message processMessage(Message msg) {
        return primary.getProcess().apply(msg);
    }


    OpStatus writeMessage(Message msg) {
        secondary.getWriter().write(secondary.getPos(), msg);
        secondary.movePos();
        return OpStatus.WRITE_SUCCESS;
    }

    public ProcessorStatus getStatus() {
        return status;
    }

    public int getId() {
        return primary.getProcId();
    }
}
