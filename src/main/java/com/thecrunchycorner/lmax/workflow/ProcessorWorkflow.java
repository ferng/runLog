package com.thecrunchycorner.lmax.workflow;

import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.processors.Processor;
import com.thecrunchycorner.lmax.processors.ProcessorStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Initially sets up, initiates and then manages the processor workflow for all processors.
 * <p>
 * <p>This is necessary as processors should not be able to access any buffer cells the leading
 * processor has not yet released. This is further complicated as several processors can process
 * the same released chunk of the buffer, eg. Journaller and Auditer
 * </p>
 */
public final class ProcessorWorkflow {
    private static Logger LOGGER = LogManager.getLogger(ProcessorWorkflow.class);

    private static Map<Integer, Processor> processorsById;
    private static Map<Integer, Map<Integer, List<ProcProperties>>> propertiesByBufferByPriority;
    private static Map<Integer, Map<Integer, Integer>> priorityPairsByBuffer = new HashMap<>();
    private static Map<Integer, CompletableFuture<ProcessorStatus>> procFutureById =
            new HashMap<>();


    private ProcessorWorkflow() {
    }

    public static void init(List<ProcProperties> properties) {
        Objects.requireNonNull(properties, "Processor properties cannot be null");
        properties.forEach((p) -> {
            if (p == null) {
                throw new NullPointerException("Processor properties cannot be null");
            }
        });

        Map<Integer, List<ProcProperties>> propertiesByProcessor = getPropertiesByProcessorId(properties);

        processorsById = getProcessorsById(propertiesByProcessor);

        propertiesByBufferByPriority = getPropertiesByBufferByPriority(properties);

        Map<Integer, ArrayList<Integer>> prioritiesByBuffer =
                getPrioritiesByBuffer(propertiesByBufferByPriority);

        priorityPairsByBuffer = getPriorityPairsByBuffer(prioritiesByBuffer);

        LOGGER.info("{} Processors configured", properties.size());
    }

    private static Map<Integer, Processor> getProcessorsById(Map<Integer, List<ProcProperties>> propertiesByProcessor) {
        Map<Integer, Processor> procs = new HashMap<>();
        propertiesByProcessor.forEach((procId, propPair) -> {
            Processor proc;
            if (propPair.size() == 2) {
                proc = new Processor(propPair.get(0), propPair.get(1));
            } else {
                proc = new Processor(propPair.get(0));
            }
            procs.put(procId, proc);
        });
        return procs;
    }

    private static Map<Integer, Map<Integer, List<ProcProperties>>> getPropertiesByBufferByPriority(List<ProcProperties> properties) {
        return properties
                .stream()
                .collect(
                        Collectors.groupingBy(ProcProperties::getBufferId,
                                Collectors.groupingBy(ProcProperties::getPriority)
                        ));
    }

    private static Map<Integer, ArrayList<Integer>> getPrioritiesByBuffer(Map<Integer,
            Map<Integer, List<ProcProperties>>> props) {

        Map<Integer, ArrayList<Integer>> sortedPriorities = new HashMap<>();

        props.forEach((bufferId, priorities) -> {
            TreeSet<Integer> priorityKeys = new TreeSet<>(priorities.keySet());
            sortedPriorities.put(bufferId, new ArrayList<>(priorityKeys));
        });

        return sortedPriorities;
    }

    private static Map<Integer, Map<Integer, Integer>> getPriorityPairsByBuffer(Map<Integer, ArrayList<Integer>> prioritiesByBuffer) {
        Map<Integer, Map<Integer, Integer>> pairs = new HashMap<>();
        prioritiesByBuffer.forEach((bufferId, priorities) -> {
            Map<Integer, Integer> priorityPairs = new HashMap<>();
            priorities.forEach((priority) -> {
                int paired = priorities.get((priorities.indexOf(priority) + 1) % priorities.size());
                priorityPairs.put(priority, paired);
            });
            pairs.put(bufferId, priorityPairs);
        });

        return pairs;
    }

    private static Map<Integer, List<ProcProperties>> getPropertiesByProcessorId(List<ProcProperties> properties) {
        return properties
                .stream()
                .collect(Collectors.groupingBy(ProcProperties::getProcId));
    }


    public static void start() {
        LOGGER.info("Spin up all processors");
        processorsById.forEach((id, proc) ->
                procFutureById.put(id, CompletableFuture.supplyAsync(proc.processLoop))
        );
    }

    public static Map<Integer, ProcessorStatus> getProcStatus() {
        Map<Integer, ProcessorStatus> statuses = new HashMap<>();
        processorsById.forEach((id, proc) -> {
            statuses.put(id, proc.getStatus());
        });
        return statuses;
    }

    public static void shutdown() {
        LOGGER.info("Shutting down all processors");
        processorsById.forEach((id, proc) ->
                proc.shutdown()
        );
    }

    public static int getLeadPos(int bufferId, int priority) {
        int leadProcessorPriority = priorityPairsByBuffer.get(bufferId).get(priority);
        List<ProcProperties> props =
                propertiesByBufferByPriority.get(bufferId).get(leadProcessorPriority);

        Optional<Integer> leadProcPos = props
                .stream()
                .map(ProcProperties::getPos)
                .reduce(Integer::min);

        return leadProcPos.get();
    }
}
