package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;

public abstract class Processor {
    protected  abstract Message getMessage();

    protected abstract Message processMessage(Message msg);

    protected abstract OpStatus putMessage(Message msg);

    protected abstract void nextPos(ProcessorType prcType);
}
