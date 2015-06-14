package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides client classes with the means to read from a buffer.  Each reader is unique to the processor using it and keeps track of:
 * 1) Which buffer it's reading from
 * 2) where it has read to
 * 3) where it can read up to
 */
public class Reader {
    private static Logger logger = LogManager.getLogger(Writer.class);

    private PosController posController = PosControllerFactory.getController();

    private RingBuffer buffer;
    private ProcessorType processor;
    private ProcessorType myLead;
    private int head;


    /**
     * Each Reader is unique to a processor (although a processor can have multiple readers / writers)
     *
     * @param props - details for this reader:
     *              1) Which buffer is to be accessed
     *              2) ID for the processor that owns us and
     *              3) ID for the processor we follow
     *              4) Initial head position, should be buffer size for lead processor, 0 for all others
     */
    public Reader(ProcProperties props) {
        buffer = props.getBuffer();
        processor = props.getProc();
        myLead = props.getLeadProc();
        head = props.getInitialHead();

        posController.setPos(processor, 0);
    }


    /**
     * Retrieves an object from its buffer or null if none, it is up to the client to wait an appropriate amount of time before retrying.
     */
    public Object read() {
        int pos = posController.getPos(processor);

        if (pos == head) {
            head = posController.getPos(myLead);
            if (pos == head) {
                return null;
            }
        }

        posController.incrPos(processor);
        return buffer.get(pos);
    }


    /**
     * Retrieves an object from its buffer if any within a specified time (ms) or null upon time out.
     *
     * @param timeout - millisesonds to wait until we give up
     * @throws InterruptedException if interrupted while waiting
     */
    public Object read(long timeout) throws InterruptedException {
        int pos = posController.getPos(processor);

        if (pos == head) {
            Thread.sleep(timeout);
            head = posController.getPos(myLead);
            if (pos == head) {
                return null;
            }
        }

        posController.incrPos(processor);
        return buffer.get(pos);
    }


    /**
     * Retrieves an object from its buffer if any within a number of attempts, waiting the specified time (ms) between each attempt or null upon retyr exhaustion.
     *
     * @param attempts - how many times to retry the read before giving up
     * @param timeout  - millisesonds to wait until we give up
     */
    public Object read(long timeout, long attempts) throws InterruptedException {
        int pos = posController.getPos(processor);

        while (attempts > 0) {
            if (pos == head) {
                Thread.sleep(timeout);
                head = posController.getPos(myLead);
                attempts--;
            } else {
                posController.incrPos(processor);
                return buffer.get(pos);
            }
        }
        return null;
    }

}
