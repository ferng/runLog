package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.PosController;
import com.thecrunchycorner.runlog.ringbufferaccess.PosControllerFactory;
import com.thecrunchycorner.runlog.ringbufferaccess.Reader;
import com.thecrunchycorner.runlog.ringbufferaccess.Writer;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;

public abstract class Processor {
    private PosController posCtrlr = PosControllerFactory.getController();
    private Writer writer;
    private Reader reader;

    public final PosController getPosCtrlr() {
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
