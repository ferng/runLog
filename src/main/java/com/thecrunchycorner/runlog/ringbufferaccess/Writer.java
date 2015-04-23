package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbuffer.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Writer {
    private static Logger logger = LogManager.getLogger(Writer.class);

    private PosController posController =  PosControllerFactory.getInstance();

    private RingBuffer buffer;
    private ProcessorType processor;
    private ProcessorType myLead;
    private int head;
    private int pos;

    public Writer(ProcProperties props) {
        buffer = props.getBuffer();
        processor = props.getProc();
        myLead = props.getLeadProc();
        head = props.getInitialHead();

        posController.setPos(processor, 0);
    }


    public OpStatus write(Object msg) {
        if (msg == null) {
            logger.warn("attempting to insert null in buffer at pos {})", pos);
            return OpStatus.ERROR;
        }

        if (pos > head) {
            head = posController.getPos(myLead);
            if (pos > head) {
                return OpStatus.HEADER_REACHED;
            }
        }
        buffer.put(pos++, msg);
        return OpStatus.WRITE_SUCCESS;
    }

}
