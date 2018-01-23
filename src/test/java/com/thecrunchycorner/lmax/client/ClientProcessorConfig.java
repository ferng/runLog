package com.thecrunchycorner.lmax.client;

import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.buffer.RingBuffer;
import com.thecrunchycorner.lmax.buffer.BufferReader;
import com.thecrunchycorner.lmax.buffer.BufferWriter;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.services.SystemProperties;
import com.thecrunchycorner.lmax.workflow.ProcessorConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.UnaryOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientProcessorConfig {
    private static Logger logger = LogManager.getLogger(ClientProcessorConfig.class);

    private static List<ProcProperties> props = new ArrayList<>();

    static {
        OptionalInt inputSizeOpt = SystemProperties.getAsInt("input.buffer.size");
        int thresholdBufferSize = SystemProperties.getThresholdBufferSize();
        int inputBufferSize = inputSizeOpt.orElse(thresholdBufferSize);

        RingBuffer<Message> ring = new RingBuffer<>(inputBufferSize);
        BufferWriter<Message> writer = new BufferWriter<>(ring);
        BufferReader<Message> reader = new BufferReader<>(ring);

        ProcProperties.Builder builder = new ProcProperties.Builder();

        //left buffer unmarshall
        ProcProperties inUnMarshall =
                builder.setId(0)
                        .setPriority(0)
                        .setWriter(writer)
                        .setInitialHead(inputBufferSize)
                        .setProcess(getSimpleprocessor())
                        .build();
        props.add(inUnMarshall);

        //left buffer processor
        ProcProperties businessProc =
                builder.setId(1)
                        .setPriority(1)
                        .setReader(reader)
                        .setInitialHead(0)
                        .setProcess(getSimpleprocessor())
                        .build();
        props.add(businessProc);

        ProcessorConfig.setProperties(props);
    }


    private static UnaryOperator<Message> getSimpleprocessor() {
        return (m) -> {
            logger.debug(m);
            return m;
        };
    }
}
