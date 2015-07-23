package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;

import java.util.concurrent.ConcurrentHashMap;

public class ProcessorWorkflow {
    private static ConcurrentHashMap<ProcessorID, ProcessorID> leadProcs = new ConcurrentHashMap<>();

    private void ProcessorWorkFLow() {
    }

    private static void loadProcs() {
        leadProcs.put(ProcessorID.INPUT_QUEUE_PROCESSOR, ProcessorID.BUSINESS_PROCESSOR);
        leadProcs.put(ProcessorID.UNMARSHALER, ProcessorID.INPUT_QUEUE_PROCESSOR);
        leadProcs.put(ProcessorID.BUSINESS_PROCESSOR, ProcessorID.UNMARSHALER);
    }

    public static synchronized ProcessorID getLeadProc(ProcessorID procId) {
        if (leadProcs.isEmpty()) {
            loadProcs();
        }
        return leadProcs.get(procId);

    }

}
