//package com.thecrunchycorner.lmax.processors;
//
//import com.thecrunchycorner.lmax.msgstore.OpStatus;
//import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
//import com.thecrunchycorner.lmax.msgstore.Message;
//import com.thecrunchycorner.lmax.workflow.ProcessorId;
//import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
//
//public class RingUnmarshalerProcessor extends Processor implements Runnable {
//
//    public RingUnmarshalerProcessor(RingBufferStore ring) {
//        ProcProperties procProps = getProcProperties(ring, ProcessorId.IN_UNMARSHALL);
//
//        initRingReader(procProps);
//        initRingWriter(procProps);
//    }
//
//    @Override
//    protected Message getMessage() {
//        return null;
//    }
//
//    @Override
//    protected final Message processMessage(Message msg) {
//        return msg;
//    }
//
//    @Override
//    protected OpStatus putMessage(Message msg) {
//        return null;
//    }
//
//    @Override
//    public void run() {
//
//    }
//}
