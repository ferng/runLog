package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.msgstore.enums.OpStatus;
import com.thecrunchycorner.lmax.ringbufferaccess.Message;
import com.thecrunchycorner.lmax.ringbufferaccess.PosController;
import com.thecrunchycorner.lmax.ringbufferaccess.PosControllerFactory;
import com.thecrunchycorner.lmax.ringbufferaccess.Reader;
import com.thecrunchycorner.lmax.ringbufferaccess.Writer;
import com.thecrunchycorner.lmax.ringbufferprocessor.ProcProperties;

public abstract class Processor {
    private PosController posCtrlr = PosControllerFactory.getController();
    private Writer writer;
    private Reader reader;

    final PosController getPosCtrlr() {
        return posCtrlr;
    }

    public final Writer getWriter() {
        return writer;
    }

    public final void setWriter(Writer writer) {
        this.writer = writer;
    }

    public final Reader getReader() {
        return reader;
    }

    public final void setReader(Reader reader) {
        this.reader = reader;
    }

    protected abstract Message getMessage();

    protected abstract Message processMessage(Message msg);

    protected abstract OpStatus putMessage(Message msg);

    protected abstract void initRingReader(ProcProperties procProps);

    protected abstract void initRingWriter(ProcProperties procProps);
}
