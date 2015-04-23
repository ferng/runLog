//package com.thecrunchycorner.runlog.ringbufferaccess;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//import com.thecrunchycorner.runlog.ringbufferprocessor.ProcProperties;
//import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
//import com.thecrunchycorner.runlog.ringbuffer.enums.BufferType;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class BufferWriteReadTest {
//
//    private RingBuffer<Integer> buffer;
//    private static final int BUFFER_SIZE = 4;
//    private Writer writer;
//    private Reader reader;
//    private ProcProperties procProp;
//
//    @Before
//    public void setup() {
//        procProp
//        buffer = new RingBuffer(BUFFER_SIZE, BufferType.INPUT);
//        writer = new Writer(buffer);
//        reader = new Reader(buffer);
//
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
//        Integer testObj1 = 3;
//        Integer testObj2 = 4;
//
//        writer.write(testObj1);
//        writer.write(testObj2);
//
//        assertThat(reader.read(), is(testObj1));
//        assertThat(reader.read(), is(testObj2));
//    }
//
//}