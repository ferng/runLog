package com.thecrunchycorner.lmax.workflow;

import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.processors.Processor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;

/**
 * Initially sets up, initiates and then manages the processor workflow for all processors
 * accessing a single RingBuferStore.
 *
 * <p>This is necessary as processors should not be able to access any buffer cells the leading
 * processor has not yet released. This is further complicated as several processors can process
 * the same released chunk of the buffer, eg. Journaller and Auditer
 * </p>
 */
public final class ProcessorWorkflow {
    private static Map<Integer, ArrayList<ProcessorId>> processors = new HashMap<>();
    private static HashMap<ProcessorId, ProcProperties> propsById = ProcessorConfig
            .getPropertiesById();
    private static HashMap<Integer, ArrayList<ProcProperties>> propsByPriority = ProcessorConfig
            .getPropertiesByPriority();
    private static int lastProcessor;

    private ProcessorWorkflow() {
    }


    static {
        loadProcs();
    }


    public static int getLeadPos(ProcessorId procId) {
        int leadProcessorPriority = getLeadingProcPriority(procId.getPriority());
        ArrayList<ProcProperties> proc = propsByPriority.get(leadProcessorPriority);

        Optional<Integer> leadProcPos =
                proc
                        .stream()
                        .map(ProcProperties::getPos)
                        .reduce(Integer::min);

        if (leadProcPos.isPresent()) {
            return leadProcPos.get();
        } else {
            throw new MissingResourceException("Mandatory workflow definition missing, please "
                    + "check ProcessorId", ProcessorWorkflow.class.getName(), "");
        }
    }


    private static void loadProcs() {
        Arrays.stream(ProcessorId.values())
                .forEach(id -> {
                    lastProcessor = id.getPriority();
                    ArrayList<ProcessorId> procList = processors.get(lastProcessor);
                    if (procList == null) {
                        procList = new ArrayList<>();
                    }
                    procList.add(id);
                    processors.put(id.getPriority(), procList);
                });
    }


    private static int getLeadingProcPriority(int currentProcPriority) {
        //using a bit of bitwise logic here to take care of any negative values (0-1) the & ignores
        //the sign and we end up with the value we want 11111 & 0010 == 2 which is the priority
        //belonging to the last processor, et voila
        return (currentProcPriority - 1) & lastProcessor;
    }
}
