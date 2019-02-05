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
        OptionalInt inputSizeOpt = SystemProperties.getAsOptInt("input.buffer.size");
        int thresholdBufferSize = SystemProperties.getThresholdBufferSize();
        int inputBufferSize = inputSizeOpt.orElse(thresholdBufferSize);
        ProcPropertiesBuilder builder = new ProcPropertiesBuilder();


        int leftBufferId = 1;
        int rightBufferId = 2;
        RingBuffer leftBuffer = new RingBuffer(leftBufferId, inputBufferSize);
        RingBuffer rightBuffer = new RingBuffer(rightBufferId, inputBufferSize);


        //================================================
        //processor 0: receiver: reader from the outside world
        FileReader receiverFileReader = new FileReader(leftBufferId);
        ProcProperties receiverReader =
                builder.setId(0)
                        .setProcId(0)
                        .setStage(0)
                        .setReader(receiverFileReader)
                        .setProcess(getSimpleprocessorReceiver())
                        .setExternal(true)
                        .build();
        props.add(receiverReader);

        //processor 0: writer to LEFT buffer
        Writer receiverBuffWriter = new BufferWriter(leftBuffer);
        ProcProperties receiverWriter =
                builder.setId(1)
                        .setProcId(0)
                        .setStage(0)
                        .setInitialHead(0)
                        .setWriter(receiverBuffWriter)
                        .build();
        props.add(receiverWriter);


        //================================================
        //processor 1: unMarshaller: reader from LEFT buffer
        Reader unMarshallerBuffReader = new BufferReader(leftBuffer);
        ProcProperties unMarshallerReader =
                builder.setId(2)
                        .setProcId(1)
                        .setStage(1)
                        .setInitialHead(0)
                        .setReader(unMarshallerBuffReader)
                        .setProcess(unMarshallInboundMessage())
                        .build();
        props.add(unMarshallerReader);

        //processor 1: writer to LEFT buffer
        Writer unMarshallerBuffWriter = new BufferWriter(leftBuffer);
        ProcProperties unMarshallerWriter =
                builder.setId(3)
                        .setProcId(1)
                        .setStage(1)
                        .setInitialHead(0)
                        .setWriter(unMarshallerBuffWriter)
                        .build();
        props.add(unMarshallerWriter);


        //================================================
        //processor 2: logger: log data just written into LEFT buffer
        //
        //when looking in the logs you will notice that sometimes the logged message is
        // unMarshalled and other times it is still raw, this is because logger and unmarshaller
        // have the same priority so either can go first, normally you wouldn't do this you'd
        // probably do a logger and replicator for example as both read, but do not update the
        // data so it will always be consistent.
        Reader loggerBuffReader = new BufferReader(leftBuffer);
        ProcProperties loggerReader =
                builder.setId(4)
                        .setProcId(2)
                        .setStage(1)
                        .setInitialHead(0)
                        .setReader(loggerBuffReader)
                        .setProcess(logMessage())
                        .build();
        props.add(loggerReader);


        //================================================
        //processor 3: processor: reader from LEFT buffer
        Reader processorBuffReader = new BufferReader(leftBuffer);
        ProcProperties processorReader =
                builder.setId(5)
                        .setProcId(3)
                        .setStage(2)
                        .setInitialHead(0)
                        .setReader(processorBuffReader)
                        .setProcess(process())
                        .build();
        props.add(processorReader);


        //processor 3: writer to RIGHT buffer
        Writer processorBuffWriter = new BufferWriter(rightBuffer);
        ProcProperties processorWriter =
                builder.setId(6)
                        .setProcId(3)
                        .setStage(2)
                        .setInitialHead(0)
                        .setWriter(processorBuffWriter)
                        .build();
        props.add(processorWriter);


        //================================================
        //processor 4: marshaller: reader from RIGHT buffer
        Reader marshallerBuffReader = new BufferReader(rightBuffer);
        ProcProperties marshallerReader =
                builder.setId(7)
                        .setProcId(4)
                        .setStage(3)
                        .setInitialHead(0)
                        .setReader(marshallerBuffReader)
                        .setProcess(marshallOutboundMessage())
                        .build();
        props.add(marshallerReader);

        //processor 4: writer to the RIGHT buffer
        Writer marshallerBuffWriter = new BufferWriter(rightBuffer);
        ProcProperties marshallerWriter =
                builder.setId(8)
                        .setProcId(4)
                        .setStage(3)
                        .setInitialHead(0)
                        .setWriter(marshallerBuffWriter)
                        .build();
        props.add(marshallerWriter);


        //================================================
        //processor 5: sender: reader from RIGHT buffer
        Reader senderBuffReader = new BufferReader(rightBuffer);
        ProcProperties senderReader =
                builder.setId(9)
                        .setProcId(5)
                        .setStage(4)
                        .setInitialHead(0)
                        .setReader(senderBuffReader)
                        .setProcess(getSimpleprocessorSender())
                        .build();
        props.add(senderReader);

        //processor 5: writer to the RIGHT buffer
        FileWriter senderFileWriter = new FileWriter(rightBufferId);
        ProcProperties senderWriter =
                builder.setId(10)
                        .setProcId(5)
                        .setStage(4)
                        .setInitialHead(0)
                        .setWriter(senderFileWriter)
                        .setExternal(true)
                        .build();
        props.add(senderWriter);


        ProcessorWorkflow.init(props);
    }


    private static UnaryOperator<Message> getSimpleprocessorReceiver() {
        return (m) -> {
            LOGGER.debug("Reading message: {}", m.getPayload());
            return m;
        };
    }

    private static UnaryOperator<Message> unMarshallInboundMessage() {
        return (m) -> {
            String text = (String) m.getPayload();
            LOGGER.debug("Unmarshalling: {}", text);
            return new Message(text.replace("<", "").replace(">", ""));
        };
    }

    private static UnaryOperator<Message> logMessage() {
        return (m) -> {
            LOGGER.debug("Log message {}", m.getPayload());
            return m;
        };
    }

    private static UnaryOperator<Message> process() {
        return (m) -> {
            String text = (String) m.getPayload();
            int processedVal = Integer.parseInt(text) + 100;
            LOGGER.debug("Processing, was {} now {}", text, processedVal);
            return new Message(processedVal);
        };
    }

    private static UnaryOperator<Message> marshallOutboundMessage() {
        return (m) -> {
            int val = (int) m.getPayload();
            LOGGER.debug("Marshalling: {}", val);
            return new Message("[" + val + "]");
        };
    }

    private static UnaryOperator<Message> getSimpleprocessorSender() {
        return (m) -> {
            LOGGER.debug("Writing message: {}", m.getPayload());
            return m;
        };
    }


}
