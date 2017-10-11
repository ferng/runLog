//package com.thecrunchycorner.lmax.processorproperties;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
//import com.thecrunchycorner.lmax.services.SystemProperties;
//import com.thecrunchycorner.lmax.workflow.ProcessorId;
//import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
//import java.util.OptionalInt;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//public class ProcPropertiesBuilderBufferTest {
//
//    private RingBufferStore<Integer> buffer;
//    private ProcProperties procProps;
//
//    @Before
//    public void setUp() {
//        final OptionalInt bufferSize = SystemProperties.getAsInt("threshold.buffer.minimum.size");
//        if (bufferSize.isPresent()) {
//            buffer = new RingBufferStore<>(bufferSize.getAsInt());
//        } else {
//            Assert.fail();
//        }
//
//        final ProcessorId trailProc = ProcessorId.BUSINESS_PROCESSOR;
//        final ProcessorId leadProc = ProcessorWorkflow.getLeadProc(trailProc);
//
//        final int initialHead = 20;
//        procProps = new ProcProperties.Builder()
//                .setBuffer(buffer)
//                .setProcessor(trailProc)
//                .setLeadProc(leadProc)
//                .setInitialHead(initialHead)
//                .createProcProperties();
//    }
//
//
//    @Test
//    public void test() {
//        final RingBufferStore actualBuffer = procProps.getBuffer();
//        assertTrue(actualBuffer instanceof RingBufferStore);
//        assertEquals(actualBuffer.size(), 16);
//    }
//
//}