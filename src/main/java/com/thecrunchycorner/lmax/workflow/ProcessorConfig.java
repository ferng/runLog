package com.thecrunchycorner.lmax.workflow;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.services.SystemProperties;
import com.thecrunchycorner.lmax.storehandler.BufferReaderWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.OptionalInt;

public class ProcessorConfig {
    private static HashMap<ProcessorId, ProcProperties> propsById = new HashMap<>();
    private static HashMap<Integer, ArrayList<ProcProperties>> propsByPriority = new HashMap<>();


    static {
        OptionalInt inputSizeOpt = SystemProperties.getAsInt("input.buffer.size");

        int thresholdBufferSize = SystemProperties.getThresholdBufferSize();
        int inputBufferSize = inputSizeOpt.orElse(thresholdBufferSize);
        RingBufferStore ring = new RingBufferStore(inputBufferSize);

        ProcProperties.Builder builder = new ProcProperties.Builder();

        ProcProperties inUnMarshall =
                builder.setProcessorId(ProcessorId.IN_UNMARSHALL)
                        .setBuffer(ring)
                        .setHandler(new BufferReaderWriter())
                        .setInitialHead(0)
                        .createProcProperties();

        propsById.put(ProcessorId.IN_UNMARSHALL, inUnMarshall);
        addToPropsArray(inUnMarshall);
        // if the only properties that differ are the head and the accessor, that could be set in
        // processorId via some sort of identifier and this could be parameterised and made a
        // tool of some sort, mind you the processor implementation and unmarsheller will be
        // specific to what this lmax is doing and working with

        ProcProperties businessProc =
                builder.setProcessorId(ProcessorId.BUSINESS_PROCESSOR)
                        .setBuffer(ring)
                        .setHandler(new BufferReaderWriter())
                        .setInitialHead(inputBufferSize)
                        .createProcProperties();

        propsById.put(ProcessorId.BUSINESS_PROCESSOR, businessProc);
        addToPropsArray(businessProc);
    }


    public static HashMap<ProcessorId, ProcProperties> getPropertiesById() {
        return propsById;
    }


    public static HashMap<Integer, ArrayList<ProcProperties>> getPropertiesByPriority() {
        return propsByPriority;
    }


    private static void addToPropsArray(ProcProperties props) {
        int priority = props.getProcessorId().getPriority();
        ArrayList priorityProcs = propsByPriority.getOrDefault(priority, new
                ArrayList<>());

        priorityProcs.add(props);
        propsByPriority.put(priority, priorityProcs);
    }

}
