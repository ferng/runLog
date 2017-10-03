package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.OpStatus;
import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.ringbufferaccess.BufferReader;
import com.thecrunchycorner.lmax.ringbufferaccess.BufferWriter;
import com.thecrunchycorner.lmax.ringbufferaccess.PosController;
import com.thecrunchycorner.lmax.ringbufferaccess.PosControllerFactory;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;

public abstract class Processor {
    private PosController posCtrlr = PosControllerFactory.getController();
    private BufferWriter writer;
    private BufferReader reader;

    final PosController getPosCtrlr() {
        return posCtrlr;
    }

    public final BufferWriter getWriter() {
        return writer;
    }

    public final void setWriter(BufferWriter writer) {
        this.writer = writer;
    }

    public final BufferReader getReader() {
        return reader;
    }

    public final void setReader(BufferReader reader) {
        this.reader = reader;
    }

    protected abstract Message getMessage();

    protected abstract Message processMessage(Message msg);

    protected abstract OpStatus putMessage(Message msg);

    protected abstract void initRingReader(ProcProperties procProps);

    protected abstract void initRingWriter(ProcProperties procProps);
}
