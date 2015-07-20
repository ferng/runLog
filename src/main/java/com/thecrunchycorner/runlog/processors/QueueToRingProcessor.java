package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.LinkedBlockingQueueStore;
import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.msgstore.Store;
import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.PosController;
import com.thecrunchycorner.runlog.ringbufferaccess.PosControllerFactory;
import com.thecrunchycorner.runlog.ringbufferaccess.Writer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder;

public class QueueToRingProcessor implements Processor, Runnable{
    private LinkedBlockingQueueStore queue;
    private RingBufferStore ring;
    private ProcProperties procProps;
    private Writer writer;
    private PosController posCtrlr = PosControllerFactory.getController();



    public QueueToRingProcessor(LinkedBlockingQueueStore queue, RingBufferStore ring) {
        this.queue = queue;
        this.ring = ring;

        posCtrlr.setPos(ProcessorType.INPUT_PROCESSOR, 0);
        int busProcHead = posCtrlr.getPos(ProcessorType.BUSINESS_PROCESSOR);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(ring)
                .setProcessor(ProcessorType.INPUT_PROCESSOR)
                .setLeadProc(ProcessorType.BUSINESS_PROCESSOR)
                .setInitialHead(busProcHead)
                .createProcProperties();

        writer = new Writer(procProps);
    }


    public Message getMessage(Store store) {
        return null;
    }

    public Message processMessage(Message msg) {
        return msg;
    }

    public OpStatus putMessage(Store store, Message msg) {
        return null;
    }

    public boolean updatePos(PosController pCtrlr) {
        return false;
    }

    public void run() {
//        while (true) {
            Message msg = processMessage((Message) queue.take());
            while (writer.write(msg) != OpStatus.WRITE_SUCCESS) {
            }
//        }
    }
}
