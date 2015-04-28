package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbuffer.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reader {
    private static Logger logger = LogManager.getLogger(Writer.class);

    private PosController posController =  PosControllerFactory.getController();

    private RingBuffer buffer;
    private ProcessorType processor;
    private ProcessorType myLead;
    private int head;


    public Reader(ProcProperties props) {
        buffer = props.getBuffer();
        processor = props.getProc();
        myLead = props.getLeadProc();
        head = props.getInitialHead();

        posController.setPos(processor, 0);
    }


    public Object read() {
        int pos = posController.getPos(processor);

        if (pos > head) {
            head = posController.getPos(myLead);
            if (pos > head) {
                return OpStatus.HEADER_REACHED;
            }
        }



        return buffer.get(pos++);
    }

}
