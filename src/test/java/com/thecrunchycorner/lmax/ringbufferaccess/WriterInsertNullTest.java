//package com.thecrunchycorner.lmax.ringbufferaccess;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
//import com.thecrunchycorner.lmax.msgstore.enums.OpStatus;
//import com.thecrunchycorner.lmax.processors.ProcessorWorkflow;
//import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorID;
//import com.thecrunchycorner.lmax.ringbufferprocessor.ProcProperties;
//import com.thecrunchycorner.lmax.ringbufferprocessor.ProcPropertiesBuilder;
//import com.thecrunchycorner.lmax.services.SystemProperties;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class WriterInsertNullTest {
//
//    private RingBufferStore<Integer> buffer;
//    private Writer writer;
//    private ProcProperties procProps;
//    private int bufferSize;
//    private int busProcHead;
//
//    @Before
//    public void setup() {
//        bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));
//        buffer = new RingBufferStore(bufferSize);
//        busProcHead = 10;
//
//        ProcessorId trailProc = ProcessorId.IN_BUSINESS_PROCESSOR;
//        ProcessorID leadProc = ProcessorWorkflow.getLeadProc(trailProc);
//
//        PosController proc = PosControllerFactory.getController();
//        proc.setPos(leadProc, 0);
//
//        procProps = new ProcPropertiesBuilder()
//                .setBuffer(buffer)
//                .setProcessor(leadProc)
//                .setLeadProc(trailProc)
//                .setInitialHead(busProcHead)
//                .createProcProperties();
//
//        writer = new Writer(procProps);
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
//        assertThat(writer.write(null), is(OpStatus.ERROR));
//    }
//
//}