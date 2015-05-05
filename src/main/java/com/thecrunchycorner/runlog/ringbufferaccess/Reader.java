package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbuffer.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides client classes with the means to read from a buffer.  Each reader is unique to the processor using it and keeps track of:
 *      1) Which buffer it's reading from
 *      2) where it has read to
 *      3) where it can read up to
 */
public class Reader {
    private static Logger logger = LogManager.getLogger(Writer.class);

    private PosController posController =  PosControllerFactory.getController();

    private RingBuffer buffer;
    private ProcessorType processor;
    private ProcessorType myLead;
    private int head;


    /**
     * Each Reader is unique to a processor (although a processor can have multiple readers / writers)
     *
     * @param props - Details for this reader:
     *              1) Which buffer is to be accessed
     *              2) ID for the processor that owns us and
     *              3) ID for the processor we follow
     */
    public Reader(ProcProperties props) {
        buffer = props.getBuffer();
        processor = props.getProc();
        myLead = props.getLeadProc();
        head = props.getInitialHead();

        posController.setPos(processor, 0);
    }


    /**
     * Retrieves an object from its buffer if any.
     *
     * @return  The next object from the buffer
     *          or, OpStatus.HEADER_REACHED if there isn't any, it is up to the client to wait an appropriate amount of time before retrying.
     */
    public Object read() {
        int pos = posController.getPos(processor);

        if (pos > head) {
            head = posController.getPos(myLead);
            if (pos > head) {
                return OpStatus.HEADER_REACHED;
            }
        }

        posController.incrPos(processor);
        return buffer.get(pos);
    }

}
