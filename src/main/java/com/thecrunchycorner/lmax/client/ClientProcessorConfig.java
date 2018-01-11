package com.thecrunchycorner.lmax.client;

import com.thecrunchycorner.lmax.msgstore.Message;
import com.thecrunchycorner.lmax.msgstore.RingBuffer;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.services.SystemProperties;
import com.thecrunchycorner.lmax.storehandler.BufferReader;
import com.thecrunchycorner.lmax.storehandler.BufferWriter;
import com.thecrunchycorner.lmax.storehandler.Reader;
import com.thecrunchycorner.lmax.storehandler.Writer;
import com.thecrunchycorner.lmax.workflow.ProcessorConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.UnaryOperator;

public class ClientProcessorConfig {
    private static List<ProcProperties> props = new ArrayList<>();

    static {
        OptionalInt inputSizeOpt = SystemProperties.getAsInt("input.buffer.size");
        int thresholdBufferSize = SystemProperties.getThresholdBufferSize();
        int inputBufferSize = inputSizeOpt.orElse(thresholdBufferSize);

        RingBuffer<Message> ring = new RingBuffer<>(inputBufferSize);
        Writer<Message> writer = new BufferWriter<Message>(ring);
        Reader<Message> reader = new BufferReader<>(ring);

        ProcProperties.Builder builder = new ProcProperties.Builder();

        //left buffer unmarshall
        ProcProperties inUnMarshall =
                builder.setId(0)
                        .setPriority(0)
                        .setWriter(writer)
                        .setInitialHead(inputBufferSize)
                        .setProcess(getSimpleprocessor())
                        .createProcProperties();
        props.add(inUnMarshall);

        //left buffer processor
        ProcProperties businessProc =
                builder.setId(1)
                        .setPriority(1)
                        .setReader(reader)
                        .setInitialHead(0)
                        .setProcess(getSimpleprocessor())
                        .createProcProperties();
        props.add(businessProc);

        ProcessorConfig.setProperties(props);
    }


    private static UnaryOperator<Message> getSimpleprocessor() {
        return (m) -> m;
    }
}
