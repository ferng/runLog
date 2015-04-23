//package com.thecrunchycorner.runlog.ringbufferaccess;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//import com.thecrunchycorner.runlog.ringbuffer.RingBuffer;
//import com.thecrunchycorner.runlog.ringbuffer.enums.OpStatus;
//import com.thecrunchycorner.runlog.ringbuffer.enums.BufferType;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class WriterInsertNullTest {
//
//    private RingBuffer<Integer> buffer;
//    private static final int BUFFER_SIZE = 4;
//    private Writer writer;
//    private Reader reader;
//
//    @Before
//    public void setup() {
//        buffer = new RingBuffer(BUFFER_SIZE, BufferType.INPUT);
//        writer = new Writer(buffer);
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