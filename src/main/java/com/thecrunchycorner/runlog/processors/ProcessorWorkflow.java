package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;

import java.util.concurrent.ConcurrentHashMap;

public final class ProcessorWorkflow {
    private static ConcurrentHashMap<ProcessorID, ProcessorID> leadProcs = new ConcurrentHashMap<>();

    private ProcessorWorkflow() {
    }

    private static void loadProcs() {
        leadProcs.put(ProcessorID.IN_Q_RECEIVER, ProcessorID.IN_BUSINESS_PROCESSOR);
        leadProcs.put(ProcessorID.IN_BUSINESS_PROCESSOR, ProcessorID.IN_UNMARSHALER);
        leadProcs.put(ProcessorID.IN_UNMARSHALER, ProcessorID.IN_Q_RECEIVER);
        leadProcs.put(ProcessorID.OUT_BUSINESS_PROCESSOR, ProcessorID.OUT_Q_SENDER);
        leadProcs.put(ProcessorID.OUT_Q_SENDER, ProcessorID.OUT_MARSHALER);
        leadProcs.put(ProcessorID.OUT_MARSHALER, ProcessorID.OUT_BUSINESS_PROCESSOR);
    }

    public static synchronized ProcessorID getLeadProc(ProcessorID procId) {
        if (leadProcs.isEmpty()) {
            loadProcs();
        }
        return leadProcs.get(procId);

    }

}
