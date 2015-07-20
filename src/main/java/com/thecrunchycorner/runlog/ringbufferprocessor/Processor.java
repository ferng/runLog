package com.thecrunchycorner.runlog.ringbufferprocessor;

import com.thecrunchycorner.runlog.msgstore.Store;
import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.PosController;

public interface Processor {
    public Message getMessage(Store store);

    public Message processMessage(Message msg);

    public OpStatus putMessage(Store store, Message msg);

    public boolean updatePos(PosController pCtrlr);
}
