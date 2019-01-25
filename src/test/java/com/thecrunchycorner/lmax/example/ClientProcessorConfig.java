package com.thecrunchycorner.lmax.example;

import com.thecrunchycorner.lmax.buffer.BufferReader;
import com.thecrunchycorner.lmax.buffer.BufferWriter;
import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.buffer.RingBuffer;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.services.SystemProperties;
import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.UnaryOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientProcessorConfig {
    private static Logger LOGGER = LogManager.getLogger(ClientProcessorConfig.class);

    private static List<ProcProperties> props = new ArrayList<>();

    public static void init() {
        OptionalInt inputSizeOpt = SystemProperties.getAsInt("input.buffer.size");
        int thresholdBufferSize = SystemProperties.getThresholdBufferSize();
        int inputBufferSize = inputSizeOpt.orElse(thresholdBufferSize);

        RingBuffer<Message> ring = new RingBuffer<>(1, inputBufferSize);
        BufferWriter<Message> writer = new BufferWriter<>(ring);
        BufferReader<Message> reader = new BufferReader<>(ring);

        ProcProperties.Builder builder = new ProcProperties.Builder();

        //left buffer unmarshall
        ProcProperties inUnMarshall =
                builder.setId(0)
                        .setPriority(0)
                        .setReader(reader)
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
                        .setWriter(writer)
                        .setInitialHead(0)
                        .setProcess(getSimpleprocessor())
                        .build();
        props.add(businessProc);

        ProcessorWorkflow.init(props);
    }


    private static UnaryOperator<Message> getSimpleprocessor() {
        return (m) -> {
            LOGGER.debug(m);
            return m;
        };
    }
}
