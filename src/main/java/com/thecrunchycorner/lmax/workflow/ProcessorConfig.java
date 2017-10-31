package com.thecrunchycorner.lmax.workflow;

import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.services.SystemProperties;
import com.thecrunchycorner.lmax.storehandler.BufferReaderWriter;
import java.util.HashMap;
import java.util.OptionalInt;

public class ProcessorConfig {

    private static HashMap<ProcessorId, ProcProperties> configMap = new HashMap<>();

    static {
        OptionalInt optInputSize = SystemProperties.getAsInt("input.buffer.size");
        int inputBufferSize = 0;

        if (optInputSize.isPresent()) {
            inputBufferSize = optInputSize.getAsInt();
        }

        RingBufferStore ring = new RingBufferStore(inputBufferSize);

        ProcProperties.Builder builder = new ProcProperties.Builder();

        ProcProperties businessProc =
                builder.setProcessorId(ProcessorId.BUSINESS_PROCESSOR)
                        .setLeadProcessorId(ProcessorId.IN_UNMARSHALL)
                        .setBuffer(ring)
                        .setAccessor(new BufferReaderWriter())
                        .setInitialHead(inputBufferSize)
                        .createProcProperties();

        configMap.put(ProcessorId.BUSINESS_PROCESSOR, businessProc);
    }

    public static ProcProperties getProps(ProcessorId id) {
        return configMap.get(id);
    }
}
