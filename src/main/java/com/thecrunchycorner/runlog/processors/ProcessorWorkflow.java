package com.thecrunchycorner.runlog.processors;

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;

import java.util.EnumSet;
import java.util.HashSet;

public class ProcessorWorkflow {
    HashSet processors = new HashSet<ProcessorID>(EnumSet.allOf(ProcessorID.class))

    public static ProcessorID getLeadProc(ProcessorID procId) {



    }
}
