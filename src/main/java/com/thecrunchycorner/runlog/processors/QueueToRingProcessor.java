package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.LinkedBlockingQueueStore;
import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.PosController;
import com.thecrunchycorner.runlog.ringbufferaccess.PosControllerFactory;
import com.thecrunchycorner.runlog.ringbufferaccess.Writer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder;

public class QueueToRingProcessor extends Processor implements Runnable{
    private LinkedBlockingQueueStore queue;
    private RingBufferStore ring;
    private ProcProperties procProps;
    private Writer writer;
    private PosController posCtrlr = PosControllerFactory.getController();
    private volatile boolean interrupt = false;


    public QueueToRingProcessor(LinkedBlockingQueueStore queue, RingBufferStore ring) {
        this.queue = queue;
        this.ring = ring;

        posCtrlr.setPos(ProcessorType.INPUT_PROCESSOR, 0);
        int busProcHead = posCtrlr.getPos(ProcessorType.BUSINESS_PROCESSOR);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(ring)
                .setProcessor(ProcessorType.BUSINESS_PROCESSOR)
                .setLeadProc(ProcessorType.INPUT_PROCESSOR)
                .setInitialHead(busProcHead)
                .createProcProperties();

        writer = new Writer(procProps);
    }


    @Override
    protected Message getMessage() {
        return (Message) queue.take();
    }


    @Override
    protected Message processMessage(Message msg) {
        return msg;
    }


    @Override
    protected OpStatus putMessage(Message msg) {
        return writer.write(msg);
    }


    @Override
    protected void nextPos(ProcessorType prcType) {
        posCtrlr.incrPos(prcType);
    }


    public void reqInterrupt() {
        interrupt = true;
    }


    public void run() {
        while (! interrupt) {
            getAndProcessMsg();
        }
    }


    protected void getAndProcessMsg() {
        Message msg = processMessage(getMessage());
        while (putMessage(msg) == OpStatus.HEADER_REACHED) {

        }
        nextPos(ProcessorType.BUSINESS_PROCESSOR);
    }
}
