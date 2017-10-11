//package com.thecrunchycorner.lmax.processorproperties;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
//import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
//import com.thecrunchycorner.lmax.workflow.ProcessorId;
//import com.thecrunchycorner.lmax.services.SystemProperties;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class ProcPropertiesProcTest {
//
//    private ProcProperties procProps;
//    private int initialHead = 20;
//
//    @Before
//    public void setup() {
//        RingBufferStore<Integer> buffer = new RingBufferStore<>(SystemProperties.getAsInt
//                ("threshold.buffer.minimum.size").getAsInt());
//        ProcessorId trailProc = ProcessorId.BUSINESS_PROCESSOR;
//        ProcessorId leadProc = ProcessorWorkflow.getLeadProc(trailProc);
//
//        procProps = new ProcProperties.Builder(buffer, trailProc, leadProc, initialHead);
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
//        assertThat(procProps.getProc(), is(ProcessorId.BUSINESS_PROCESSOR));
//    }
//
//}