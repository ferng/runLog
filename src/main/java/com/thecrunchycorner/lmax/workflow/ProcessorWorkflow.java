package com.thecrunchycorner.lmax.workflow;

import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.processors.Processor;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Initially sets up, initiates and then manages the processor workflow for all processors.
 * <p>
 * <p>This is necessary as processors should not be able to access any buffer cells the leading
 * processor has not yet released. This is further complicated as several processors can process
 * the same released chunk of the buffer, eg. Journaller and Auditer
 * </p>
 */
public final class ProcessorWorkflow {
    private static int lastPriority;

    private static Map<Integer, Processor> processorById;
    private static Map<Integer, List<ProcProperties>> propertiesByPriority;

    private ProcessorWorkflow() {
    }


    private static void init() {
        processorById = ProcessorConfig.getProperties().stream()
                .collect(Collectors.toConcurrentMap(
                        ProcProperties::getId, initProcessor
                ));

        propertiesByPriority = ProcessorConfig.getProperties().stream()
                .collect(Collectors.groupingBy(ProcProperties::getPriority));

        lastPriority = ProcessorConfig.getProperties().stream()
                .map(ProcProperties::getPriority)
                .reduce(0, Integer::compare);

    }

    private static Function<ProcProperties, Processor<Message>> initProcessor = Processor::new;


    public static int getLeadPos(int priority) {
        int leadProcessorPriority = getLeadingProcPriority(priority);
        List<ProcProperties> props = propertiesByPriority.get(leadProcessorPriority);

        Optional<Integer> leadProcPos = props
                .stream()
                .map(ProcProperties::getPos)
                .reduce(Integer::min);

        if (leadProcPos.isPresent()) {
            return leadProcPos.get();
        } else {
            throw new MissingResourceException("Mandatory workflow definition missing, please "
                    + "check ProcessorPriorities", ProcessorWorkflow.class.getName(), "");
        }
    }


    private static int getLeadingProcPriority(int currentProcPriority) {
        //using a bit of bitwise logic here to take care of any negative values (0-1) the & ignores
        //the sign and we end up with the value we want 11111 & 0010 == 2 which is the priority
        //belonging to the last processor, et voila
        return (currentProcPriority - 1) & lastPriority;
    }
}
