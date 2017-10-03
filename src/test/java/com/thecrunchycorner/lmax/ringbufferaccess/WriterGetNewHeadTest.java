//package com.thecrunchycorner.lmax.ringbufferaccess;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
//import com.thecrunchycorner.lmax.msgstore.OpStatus;
//import com.thecrunchycorner.lmax.processors.ProcessorWorkflow;
//import com.thecrunchycorner.lmax.ringbufferaccess.enums.ProcessorID;
//import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
//import com.thecrunchycorner.lmax.processorproperties.ProcPropertiesBuilder;
//import com.thecrunchycorner.lmax.services.SystemProperties;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//
//public class WriterGetNewHeadTest {
//
//    private RingBufferStore<Integer> buffer;
//    private BufferWriter writer;
//    private ProcProperties procProps;
//    private int bufferSize;
//    private int busProcHead;
//    private PosController proc;
//    private ProcessorId trailProc = ProcessorId.IN_BUSINESS_PROCESSOR;
//    private ProcessorID leadProc = ProcessorWorkflow.getLeadProc(trailProc);
//
//    @Before
//    public void setup() {
//        bufferSize = Integer.parseInt(SystemProperties.get("threshold.buffer.minimum.size"));
//        buffer = new RingBufferStore(bufferSize);
//        busProcHead = 10;
//
//        proc = PosControllerFactory.getController();
//        proc.setPos(trailProc, 0);
//
//        procProps = new ProcPropertiesBuilder()
//                .setBuffer(buffer)
//                .setProcessor(trailProc)
//                .setLeadProc(leadProc)
//                .setHead(busProcHead)
//                .createProcProperties();
//
//        proc.setPos(leadProc, busProcHead);
//
//        writer = new BufferWriter(procProps);
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
//        //write to head
//        for (int i = 0; i < busProcHead; i++) {
//            writer.write(new Integer((i)));
//        }
//
//        busProcHead += 10;
//        proc.setPos(leadProc, busProcHead);
//
//        for (int i = 10; i < busProcHead; i++) {
//            writer.write(new Integer((i)));
//        }
//
//        //attempt to go beyond head
//        assertThat(writer.write(new Integer((600))), is(OpStatus.HEADER_REACHED));
//    }
//
//}