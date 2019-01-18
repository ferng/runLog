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
    private ProcProperties props;


    public Processor(ProcProperties procProperties) {
        this.props = procProperties;
    }


    public Supplier<ProcessorStatus> processLoop = () -> {
        while (!interrupt) {
            if (props.getPos() == props.getHead()) {
                if (headUpdated()) {
                    LOGGER.debug("processing stuff");
                    processPending();
                } else {
                    LOGGER.debug("waiting for stuff");
                }
            }
        }
        status = ProcessorStatus.SHUTDOWN;
        LOGGER.debug("Processor {} shutdown", props.getId());
        return ProcessorStatus.SHUTDOWN;
    };

    private boolean headUpdated() {
        int leadPos = ProcessorWorkflow.getLeadPos(props.getPriority());
        if (props.getHead() < leadPos) {
            props.setHead(leadPos);
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
        Message msg = props.getReader().read(props.getPos());

        props.movePos();
        return msg;
    }


    Message processMessage(Message msg) {
        return props.getProcess().apply(msg);
    }


    OpStatus writeMessage(Message msg) {
        props.getWriter().write(props.getPos(), msg);
        props.movePos();
        return OpStatus.WRITE_SUCCESS;
    }

    public ProcessorStatus getStatus() {
        return status;
    }
}
