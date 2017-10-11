package com.thecrunchycorner.lmax.workflow;

import com.thecrunchycorner.lmax.processors.Processor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;

public final class ProcessorWorkflow {
    private static Map<Integer, ArrayList<ProcessorId>> leadProcs = new HashMap<>();
    private static int lastProcPriority;

    private ProcessorWorkflow() {
    }


    public static int getLeadPos(ProcessorId procId) {
        if (leadProcs.isEmpty()) {
            loadProcs();
        }

        int leadProcessorPriority = getLeadingProcPriority(procId.getPriority());
        ArrayList<ProcessorId> proc = leadProcs.get(leadProcessorPriority);

        Optional<Integer> leadProcPos =
                proc
                        .stream()
                        .map(ProcessorId::getProcessor)
                        .map(Processor::getPos)
                        .reduce(Integer::min);

        if (leadProcPos.isPresent()) {
            return leadProcPos.get();
        } else {
            throw new MissingResourceException("Mandatory default system propery missing: "
                    + "threshold.buffer.minimum.size", ProcessorWorkflow.class.getName(), "");
        }
    }


    private static void loadProcs() {
        Arrays.stream(ProcessorId.values())
                .forEach(id -> {
                    lastProcPriority = id.getPriority();
                    ArrayList<ProcessorId> procList = leadProcs.get(lastProcPriority);
                    if (procList == null) {
                        procList = new ArrayList<>();
                    }
                    procList.add(id);
                    leadProcs.put(id.getPriority(), procList);
                });
    }


    private static int getLeadingProcPriority(int currentProcPriority) {
        return (currentProcPriority - 1) & lastProcPriority;
    }
}
