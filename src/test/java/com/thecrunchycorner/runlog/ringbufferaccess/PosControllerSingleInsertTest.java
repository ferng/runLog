package com.thecrunchycorner.runlog.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PosControllerSingleInsertTest {
    private PosController posController;


    @Before
    public void setup() {
        posController = PosControllerFactory.getController();
    }


    @After
    public void tearDOWN() {

    }


    @Test
    public void Test() {
        int inputIndex = 45;

        posController.setPos(ProcessorType.INPUT_PROCESSOR, inputIndex);

        assertThat(posController.getPos(ProcessorType.INPUT_PROCESSOR), is(inputIndex));
    }

}