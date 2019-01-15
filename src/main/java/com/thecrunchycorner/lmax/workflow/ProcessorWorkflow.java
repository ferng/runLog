package com.thecrunchycorner.lmax.workflow;

import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.processors.Processor;
import com.thecrunchycorner.lmax.processors.ProcessorStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    private static int lastPriority;

    private static Map<Integer, Processor> processorsById;
    private static Map<Integer, CompletableFuture<ProcessorStatus>> procFutureById =
            new HashMap<>();
    private static Map<Integer, List<ProcProperties>> propertiesByPriority;

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

        propertiesByPriority = properties
                .stream()
                .collect(Collectors.groupingBy(ProcProperties::getPriority));

        lastPriority = properties
                .stream()
                .map(ProcProperties::getPriority)
                .reduce(0, Integer::max);

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

    public static int getLeadPos(int priority) {
        int leadProcessorPriority = getLeaderProcPriority(priority);
        List<ProcProperties> props = propertiesByPriority.get(leadProcessorPriority);

        Optional<Integer> leadProcPos = props
                .stream()
                .map(ProcProperties::getPos)
                .reduce(Integer::min);

        return leadProcPos.get();
    }


    private static int getLeaderProcPriority(int currentProcPriority) {
        return (currentProcPriority + 1) % (lastPriority + 1);
    }
}
