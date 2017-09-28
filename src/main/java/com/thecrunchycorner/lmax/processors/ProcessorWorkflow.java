package com.thecrunchycorner.lmax.processors;

import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorId;

import java.util.concurrent.ConcurrentHashMap;

public final class ProcessorWorkflow {
    private static ConcurrentHashMap<ProcessorId, ProcessorId> leadProcs = new ConcurrentHashMap<>();

    private ProcessorWorkflow() {
    }

    private static void loadProcs() {
//        leadProcs.put(ProcessorId.IN_Q_RECEIVE, ProcessorId.IN_BUSINESS_PROCESSOR);
//        leadProcs.put(ProcessorId.IN_BUSINESS_PROCESSOR, ProcessorId.IN_UNMARSHALL);
        leadProcs.put(ProcessorId.IN_UNMARSHALL, ProcessorId.IN_Q_RECEIVE);
//        leadProcs.put(ProcessorId.OUT_BUSINESS_PROCESSOR, ProcessorId.OUT_Q_SEND);
//        leadProcs.put(ProcessorId.OUT_Q_SEND, ProcessorId.OUT_MARSHALL);
//        leadProcs.put(ProcessorId.OUT_MARSHALL, ProcessorId.OUT_BUSINESS_PROCESSOR);
    }

    public static synchronized ProcessorId getLeadProc(ProcessorId procId) {
        if (leadProcs.isEmpty()) {
            loadProcs();
        }
        return leadProcs.get(procId);

    }

}
