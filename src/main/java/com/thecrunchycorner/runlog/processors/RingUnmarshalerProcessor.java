package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.msgstore.RingBufferStore;
import com.thecrunchycorner.runlog.ringbufferaccess.Message;
import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder;

public class RingUnmarshalerProcessor extends RingProcessor implements Runnable {

    public RingUnmarshalerProcessor(RingBufferStore ring) {
        ProcProperties procProps = getWriterProcProperties(ring);

        initRingWriter(procProps);
        initRingReader(procProps);
    }


    private ProcProperties getWriterProcProperties(RingBufferStore ring) {
        ringProcID = ProcessorID.UNMARSHALER;
        ringLeadProcID = ProcessorWorkflow.getLeadProc(ringProcID);

        ProcProperties procProps;
        posCtrlr.setPos(ringProcID, 0);
        int leadProcHead = posCtrlr.getPos(ringLeadProcID);

        procProps = new ProcPropertiesBuilder()
                .setBuffer(ring)
                .setProcessor(ringProcID)
                .setLeadProc(ringLeadProcID)
                .setInitialHead(leadProcHead)
                .createProcProperties();
        return procProps;
    }


    @Override
    protected Message processMessage(Message msg) {
        return msg;
    }
}
