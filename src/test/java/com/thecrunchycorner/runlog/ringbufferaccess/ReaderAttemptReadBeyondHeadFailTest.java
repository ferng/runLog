//package com.thecrunchycorner.runlog.ringbufferaccess;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
//import com.thecrunchycorner.runlog.ringbuffer.enums.OpStatus;
//import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;
//import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
//import com.thecrunchycorner.runlog.ringbufferprocessor.ProcPropertiesBuilder;
//import com.thecrunchycorner.runlog.services.SystemProperties;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class ReaderAttemptReadBeyondHeadFailTest {
//
//    private RingBuffer<Integer> buffer;
//    private Writer writer;
//    private Reader reader;
//    private ProcProperties busProcProps;
//    private ProcProperties inputProcProps;
//    private int bufferSize;
//    private int busProcHead;
//    private int inputProcHead;
//    private PosController proc;
//
//    @Before
//    public void setup() {
//        bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));
//        buffer = new RingBuffer(bufferSize);
//        inputProcHead = bufferSize;
//        busProcHead = 10;
//
//        proc = PosControllerFactory.getController();
//                proc.setPos(ProcessorType.BUSINESS_PROCESSOR, 0);
//
//        busProcProps = new ProcPropertiesBuilder()
//                .setBuffer(buffer)
//                .setProcessor(ProcessorType.BUSINESS_PROCESSOR)
//                .setLeadProc(ProcessorType.INPUT_PROCESSOR)
//                .setInitialHead(inputProcHead)
//                .createProcProperties();
//
//        writer = new Writer(busProcProps);
//
//        inputProcProps = new ProcPropertiesBuilder()
//                .setBuffer(buffer)
//                .setProcessor(ProcessorType.INPUT_PROCESSOR)
//                .setLeadProc(ProcessorType.BUSINESS_PROCESSOR)
//                .setInitialHead(busProcHead)
//                .createProcProperties();
//
//        reader = new Reader(inputProcProps);
//    }
//
//
//    @After
//    public void tearDOWN() {
//
//    }
//
//
//    @Test
//    public void Test() {
//        for (int i = 0; i < busProcHead; i++) {
//            writer.write(new Integer((i)));
//        }
//
//        busProcHead = proc.getPos(ProcessorType.BUSINESS_PROCESSOR);
//        for (int i = 0; i < busProcHead; i++) {
//            reader.read();
//        }
//
//        assertThat(reader.read(), is(OpStatus.HEADER_REACHED));
//    }
//
//}