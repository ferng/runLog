package com.thecrunchycorner.runlog.ringbufferaccess;

import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
import com.thecrunchycorner.runlog.ringbuffer.types.AccessStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.types.ProcessorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class BufferWriter {
    private static Logger logger = LogManager.getLogger(BufferWriter.class);

    private BufferPosController posController =  BufferPosControllerFactory.getInstance();
    private RingBuffer buffer;
    private ProcessorType writer;
    private ProcessorType myLead;
    private int head;
    private int pos;

    public BufferWriter(RingBuffer buffer, ProcessorType writer, ProcessorType myLead) {
        this.buffer = buffer;
        this.writer = writer;
        this.myLead = myLead;

    }


    public AccessStatus write(Object msg) {
        if (msg == null) {
            logger.warn("attempting to insert null in buffer at pos {})", pos);
            return AccessStatus.ERROR;
        }

        if (pos > head) {
            posController
            return AccessStatus.HEADER_REACHED;
        }
        buffer.put(pos++, msg);
        return AccessStatus.WRITE_SUCCESS;
    }

}
