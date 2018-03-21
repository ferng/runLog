package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.buffer.OpStatus;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
import java.util.function.Supplier;

public class Processor {
    private ProcessorStatus status = ProcessorStatus.INITIALIZED;
    private volatile boolean interrupt = false;
    private ProcProperties props;


    public Processor(ProcProperties procProperties) {
        this.props = props;
    }


    private void updatePos(int pos) {
        props.setPos(pos);
    }

    private boolean updateHead() {
        int leadPos = ProcessorWorkflow.getLeadPos(props.getPriority());
        if (props.getHead() < leadPos) {
            props.setHead(leadPos);
            return true;
        }
        return false;
    }

    public Supplier<ProcessorStatus> processLoop = () -> {
        while (!interrupt) {
            if (props.getPos() == props.getHead()) {
                if (!updateHead()) {
                    continue;;
                }
            }



        }

    }


    OpStatus readAndProcess() {
        Message msg = processMessage(readMessage());
        System.out.println(msg.getPayload());
        return writeMessage(msg);
    }

    void batchReadAndProcessMsg() {
        Message msg = processMessage(readMessage());
        while (writeMessage(msg) == OpStatus.HEADER_REACHED) {
            System.out.println(msg.getPayload());
        }
    }

    Object readPayload() {
        return null;
    }

    private Message readMessage() {
        return props.getReader().read(props.getPos());
    }


    Message processMessage(Message msg) {
        return props.getProcess().apply(msg);
    }

    OpStatus writePayload(Object payload) {
        return null;
    }

    OpStatus writeMessage(Message msg) {
        props.getWriter().write(props.getPos(), msg);
        return OpStatus.WRITE_SUCCESS;
    }

    ;


    ProcessorStatus getStatus() {
        return status;
    }

    void setStatus(ProcessorStatus status) {
        this.status = status;
    }

    public final boolean isInterrupt() {
        return interrupt;
    }

    public final void setInterrupt(boolean interrupt) {
        this.interrupt = interrupt;
    }

    public final void reqInterrupt() {
        interrupt = true;
    }

    public final void run() {
        while (!interrupt) {
            readAndProcess();
        }
    }
}
