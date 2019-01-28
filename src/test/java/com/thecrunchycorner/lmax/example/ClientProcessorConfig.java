package com.thecrunchycorner.lmax.example;


import com.thecrunchycorner.lmax.buffer.BufferReader;
import com.thecrunchycorner.lmax.buffer.BufferWriter;
import com.thecrunchycorner.lmax.buffer.Message;
import com.thecrunchycorner.lmax.buffer.RingBuffer;
import com.thecrunchycorner.lmax.handlers.Reader;
import com.thecrunchycorner.lmax.handlers.Writer;
import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
import com.thecrunchycorner.lmax.processorproperties.ProcPropertiesBuilder;
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
        ProcPropertiesBuilder builder = new ProcPropertiesBuilder();


        int leftBufferId = 1;
        int rightBufferId = 2;
        RingBuffer<Message> leftBuffer = new RingBuffer<>(1, inputBufferSize);
        RingBuffer<Message> rightBuffer = new RingBuffer<>(2, inputBufferSize);


        //================================================
        //processor 0: receiver: reader from the outside world
        FileReader receiverFileReader = new FileReader(leftBufferId);
        ProcProperties receiverReader =
                builder.setId(0)
                        .setProcId(0)
                        .setPriority(0)
                        .setReader(receiverFileReader)
                        .setProcess(getSimpleprocessor())
                        .build();
        props.add(receiverReader);

        //processor 0: writer to LEFT buffer
        Writer<Message> receiverBuffWriter = new BufferWriter<>(leftBuffer);
        ProcProperties receiverWriter =
                builder.setId(1)
                        .setProcId(0)
                        .setPriority(0)
                        .setWriter(receiverBuffWriter)
                        .build();
        props.add(receiverWriter);


        //================================================
        //processor 1: unMarshaller: reader from LEFT buffer
        Reader<Message> unMarshallerBuffReader = new BufferReader<>(leftBuffer);
        ProcProperties unMarshallerReader =
                builder.setId(2)
                        .setProcId(1)
                        .setPriority(1)
                        .setReader(unMarshallerBuffReader)
                        .setProcess(unMarshallInboundMessage())
                        .build();
        props.add(unMarshallerReader);

        //processor 1: writer to LEFT buffer
        Writer<Message> unMarshallerBuffWriter = new BufferWriter<>(leftBuffer);
        ProcProperties unMarshallerWriter =
                builder.setId(3)
                        .setProcId(1)
                        .setPriority(1)
                        .setWriter(unMarshallerBuffWriter)
                        .build();
        props.add(unMarshallerWriter);


        //================================================
        //processor 2: logger: log data just written into LEFT buffer
        Reader<Message> loggerBuffReader = new BufferReader<>(leftBuffer);
        ProcProperties loggerReader =
                builder.setId(4)
                        .setProcId(2)
                        .setPriority(1)
                        .setReader(loggerBuffReader)
                        .setProcess(logMessage())
                        .build();
        props.add(loggerReader);


        //================================================
        //processor 3: processor: reader from LEFT buffer
        Reader<Message> processorBuffReader = new BufferReader<>(leftBuffer);
        ProcProperties processorReader =
                builder.setId(5)
                        .setProcId(3)
                        .setPriority(2)
                        .setReader(processorBuffReader)
                        .setProcess(process())
                        .build();
        props.add(processorReader);


        //processor 3: writer to RIGHT buffer
        Writer<Message> processorBuffWriter = new BufferWriter<>(rightBuffer);
        ProcProperties processorWriter =
                builder.setId(6)
                        .setProcId(3)
                        .setPriority(2)
                        .setWriter(processorBuffWriter)
                        .build();
        props.add(processorWriter);


        //================================================
        //processor 4: marshaller: reader from RIGHT buffer
        Reader<Message> marshallerBuffReader = new BufferReader<>(rightBuffer);
        ProcProperties marshallerReader =
                builder.setId(7)
                        .setProcId(4)
                        .setPriority(3)
                        .setReader(marshallerBuffReader)
                        .setProcess(marshallOutboundMessage())
                        .build();
        props.add(marshallerReader);

        //processor 4: writer to the RIGHT buffer
        Writer<Message> marshallerBuffWriter = new BufferWriter<>(rightBuffer);
        ProcProperties marshallerWriter =
                builder.setId(8)
                        .setProcId(4)
                        .setPriority(3)
                        .setWriter(marshallerBuffWriter)
                        .build();
        props.add(marshallerWriter);


        //================================================
        //processor 5: marshaller: reader from RIGHT buffer
        Reader<Message> senderBuffReader = new BufferReader<>(rightBuffer);
        ProcProperties senderReader =
                builder.setId(9)
                        .setProcId(5)
                        .setPriority(4)
                        .setReader(senderBuffReader)
                        .setProcess(getSimpleprocessor())
                        .build();
        props.add(senderReader);

        //processor 5: writer to the RIGHT buffer
        FileWriter senderFileWriter = new FileWriter(rightBufferId);
        ProcProperties senderWriter =
                builder.setId(10)
                        .setProcId(5)
                        .setPriority(4)
                        .setWriter(senderFileWriter)
                        .build();
        props.add(senderWriter);



        ProcessorWorkflow.init(props);
    }




    private static UnaryOperator<Message> getSimpleprocessor() {
        return (m) -> m;
    }

    private static UnaryOperator<Message> unMarshallInboundMessage() {
        return (m) -> {
            String text = (String) m.getPayload();
            return new Message(text.replace("<", "").replace(">", ""));
        };
    }

    private static UnaryOperator<Message> logMessage() {
        return (m) -> {
            LOGGER.debug(m.getPayload());
            return m;
        };
    }

    private static UnaryOperator<Message> process() {
        return (m) -> {
            String text = (String) m.getPayload();
            int processedVal = Integer.parseInt(text) + 100;
            return new Message(processedVal);
        };
    }

    private static UnaryOperator<Message> marshallOutboundMessage() {
        return (m) -> {
            int val = (int) m.getPayload();
            return new Message("[" + val + "]");
        };
    }


}
