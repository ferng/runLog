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

        processorsById = properties
                .stream()
                .collect(Collectors.toConcurrentMap(ProcProperties::getId, Processor::new));

        propertiesByBufferByPriority = properties
                .stream()
                .collect(
                        Collectors.groupingBy(ProcProperties::getBufferId,
                                Collectors.groupingBy(ProcProperties::getPriority)
                        ));

        Map<Integer, ArrayList<Integer>> prioritiesByBuffer = new HashMap<>();
        propertiesByBufferByPriority.forEach((bufferId, priorities) -> {
            TreeSet<Integer> priorityKeys = new TreeSet<>(priorities.keySet());
            prioritiesByBuffer.put(bufferId, new ArrayList<>(priorityKeys));
        });

        prioritiesByBuffer.forEach((bufferId, priorities) -> {
            Map<Integer, Integer> priorityPairs = new HashMap<>();
            priorities.forEach((priority) -> {
                int paired = priorities.get((priorities.indexOf(priority) + 1) % priorities.size());
                priorityPairs.put(priority, paired);
            });
            priorityPairsByBuffer.put(bufferId, priorityPairs);

        });

        LOGGER.info("{} Processors configured", properties.size());
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
