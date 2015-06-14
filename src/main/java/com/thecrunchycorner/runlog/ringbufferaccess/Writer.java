package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbuffer.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides client classes with the means to write to a buffer.  Each writer is unique to the processor using it and keeps track of:
 * 1) Which buffer it's writing to
 * 2) where it has written up to
 * 3) where it can write up to
 */
public class Writer {
    private static Logger logger = LogManager.getLogger(Writer.class);

    private PosController posController = PosControllerFactory.getController();

    private RingBuffer buffer;
    private ProcessorType processor;
    private ProcessorType myLead;
    private int head;


    /**
     * Each Writer is unique to a processor (although a processor can have multiple readers / writers)
     *
     * @param props - details for this wtriter:
     *              1) Which buffer is to be accessed
     *              2) ID for the processor that owns us and
     *              3) ID for the processor we follow
     */
    public Writer(ProcProperties props) {
        buffer = props.getBuffer();
        processor = props.getProc();
        myLead = props.getLeadProc();
        head = props.getInitialHead();

        posController.setPos(processor, 0);
    }


    /**
     * Writes an object to its buffer if there is room.
     *
     * @return OpStatus.WRITE_SUCCESS if object was successfully written to the buffer
     * or, OpStatus.HEADER_REACHED if there was no room, it is up to the client to wait an appropriate amount of time before retrying.
     */
    public OpStatus write(Object msg) {
        int pos = posController.getPos(processor);

        if (msg == null) {
            logger.warn("attempting to insert null in buffer at pos {})", pos);
            return OpStatus.ERROR;
        }

        if (pos == head) {
            head = posController.getPos(myLead);
            if (pos == head) {
                return OpStatus.HEADER_REACHED;
            }
        }

        buffer.put(pos, msg);
        posController.incrPos(processor);

        return OpStatus.WRITE_SUCCESS;
    }

}
