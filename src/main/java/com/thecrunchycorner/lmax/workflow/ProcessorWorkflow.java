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
import java.util.concurrent.Executors;
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
    private static Map<Integer, Map<Integer, List<ProcProperties>>> propertiesByBufferByStage;
    private static Map<Integer, Map<Integer, Integer>> stagePairsByBuffer = new HashMap<>();
    private static Map<Integer, CompletableFuture<ProcessorStatus>> procFutureById =
            new HashMap<>();


    private ProcessorWorkflow() {
    }


    /**
     * Preps up all the processors, their data and head position calculations.
     *
     * @param properties the processor properties which will define this LMAX inmplementation
     */
    public static void init(List<ProcProperties> properties) {
        Objects.requireNonNull(properties, "Processor properties cannot be null");
        properties.forEach((p) -> {
            if (p == null) {
                throw new NullPointerException("Processor properties cannot be null");
            }
        });

        Map<Integer, List<ProcProperties>> propertiesByProcessor =
                calcPropertiesByProcessorId(properties);

        processorsById = calcProcessorsById(propertiesByProcessor);

        propertiesByBufferByStage = calcPropertiesByBufferByStage(properties);

        Map<Integer, ArrayList<Integer>> stageByBuffer =
                calcStagesByBuffer(propertiesByBufferByStage);

        stagePairsByBuffer = calcStageDataByBuffer(stageByBuffer);

        LOGGER.info("{} Processors configured", properties.size());
    }


    private static Map<Integer, Processor> calcProcessorsById(Map<Integer, List<ProcProperties>> propertiesByProcessor) {
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


    private static Map<Integer, Map<Integer, List<ProcProperties>>> calcPropertiesByBufferByStage(List<ProcProperties> properties) {
        return properties
                .stream()
                .collect(
                        Collectors.groupingBy(ProcProperties::getBufferId,
                                Collectors.groupingBy(ProcProperties::getStage)
                        ));
    }


    private static Map<Integer, ArrayList<Integer>> calcStagesByBuffer(Map<Integer,
            Map<Integer, List<ProcProperties>>> props) {

        Map<Integer, ArrayList<Integer>> sortedStages = new HashMap<>();

        props.forEach((bufferId, stages) -> {
            TreeSet<Integer> stageKeys = new TreeSet<>(stages.keySet());
            sortedStages.put(bufferId, new ArrayList<>(stageKeys));
        });

        return sortedStages;
    }


    private static Map<Integer, Map<Integer, Integer>> calcStageDataByBuffer(Map<Integer,
            ArrayList<Integer>> stagesByBuffer) {
        Map<Integer, Map<Integer, Integer>> stageData = new HashMap<>();
        stagesByBuffer.forEach((bufferId, stages) -> {
            Map<Integer, Integer> stagePairs = new HashMap<>();
            stages.forEach((stage) -> {
                int lead = stages.get((stages.indexOf(stage) + 1) % stages.size());
                stagePairs.put(lead, stage);
            });
            stageData.put(bufferId, stagePairs);
        });
        return stageData;
    }


    private static Map<Integer, List<ProcProperties>> calcPropertiesByProcessorId(List<ProcProperties> properties) {
        return properties
                .stream()
                .collect(Collectors.groupingBy(ProcProperties::getProcId));
    }


    /**
     * This starts up the LMAX framework, which simply means starting up all the processors
     */
    public static void start() {
        LOGGER.info("Spin up all processors");
        processorsById.forEach((id, proc) ->
                procFutureById.put(id, CompletableFuture.supplyAsync(proc.processLoop,
                        Executors.newFixedThreadPool(processorsById.size())))
        );
    }


    /**
     * How are all the processors doing?
     *
     * @return Map with the current processor status (by processorId)
     */
    public static Map<Integer, ProcessorStatus> getProcStatus() {
        Map<Integer, ProcessorStatus> statuses = new HashMap<>();
        processorsById.forEach((id, proc) -> statuses.put(id, proc.getStatus()));
        return statuses;
    }


    /**
     * Shutdown all processors & by default the whole of LMAX
     */
    public static void shutdown() {
        LOGGER.info("Shutting down all processors");
        processorsById.forEach((id, proc) ->
                proc.shutdown()
        );
    }


    /**
     * Return the leader's position (which becomes the head for processors in this stage)
     *
     * @param bufferId What ring buffer are we talking about?
     * @param stage what's the stage the current processor is in (so we what the next stage is
     * and get the position that one is in
     * @return the leading stage position (anything before that is then free for anyone else to use)
     */
    public static int getLeadPos(int bufferId, int stage) throws IndexOutOfBoundsException {
        int leadProcessorStage = stagePairsByBuffer.get(bufferId).get(stage);
        List<ProcProperties> props =
                propertiesByBufferByStage.get(bufferId).get(leadProcessorStage);

        Optional<Integer> leadProcPos = props
                .stream()
                .filter((prop) -> !prop.isExternal())
                .map(ProcProperties::getPos)
                .reduce(Integer::min);

        // This can never be null as ProcPropertiesBuilder prohibits such a state
        //noinspection OptionalGetWithoutIsPresent
        return leadProcPos.get();
    }
}
