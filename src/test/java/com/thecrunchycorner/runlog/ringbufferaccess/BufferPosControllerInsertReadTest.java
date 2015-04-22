package com.thecrunchycorner.runlog.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbufferaccess.types.ProcessorType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferPosControllerInsertReadTest {
    private BufferPosController posController;


    @Before
    public void setup() {
        posController = BufferPosControllerFactory.getInstance();
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        int inputIndex = 45;

        posController.updatePos(ProcessorType.INPUT_PROCESSOR, inputIndex);
        assertThat(posController.getPos(ProcessorType.INPUT_PROCESSOR), is(inputIndex));
    }

}