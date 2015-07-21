package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.Reader;
import com.thecrunchycorner.runlog.ringbufferaccess.Writer;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;

public abstract class RingProcessor extends Processor implements Runnable {
    protected ProcessorID ringProcID;
    protected ProcessorID ringLeadProcID;


    private volatile boolean interrupt = false;

    @Override
    protected void initRingReader(ProcProperties procProps) {
        reader = new Reader(procProps);
    }


    @Override
    protected void initRingWriter(ProcProperties procProps) {
        writer = new Writer(procProps);
    }


    @Override
    protected Message getMessage() {
        return (Message) reader.read();
    }


    @Override
    protected abstract Message processMessage(Message msg);


    @Override
    protected OpStatus putMessage(Message msg) {
        return writer.write(msg);
    }


    public void run() {
        while (!interrupt) {
            getAndProcessMsg();
        }
    }

    protected void getAndProcessMsg() {
        Message msg = processMessage(getMessage());
        while (putMessage(msg) == OpStatus.HEADER_REACHED) {
        }
        nextPos(ringProcID);
    }


    public void reqInterrupt() {
        interrupt = true;
    }
}
