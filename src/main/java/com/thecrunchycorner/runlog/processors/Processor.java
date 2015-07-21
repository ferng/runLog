package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.PosController;
import com.thecrunchycorner.runlog.ringbufferaccess.PosControllerFactory;
import com.thecrunchycorner.runlog.ringbufferaccess.Reader;
import com.thecrunchycorner.runlog.ringbufferaccess.Writer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;

public abstract class Processor {
    protected PosController posCtrlr = PosControllerFactory.getController();
    protected Writer writer;
    protected Reader reader;

    protected abstract Message getMessage();

    protected abstract Message processMessage(Message msg);

    protected abstract OpStatus putMessage(Message msg);

    protected abstract void initWriter(int initPos);

    protected void nextPos(ProcessorID prcType) {
        posCtrlr.incrPos(prcType);
    }
}
