package com.thecrunchycorner.runlog.ringbufferaccess;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.thecrunchycorner.runlog.ringbufferaccess.enums.ProcessorID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PosControllerMultipleInsertTest {
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
        int inputIndex = 146;
        int unMarshallerIndex = 84;
        int businessProcIndex = 72;

        posController.setPos(ProcessorID.INPUT_QUEUE_PROCESSOR, inputIndex);
        posController.setPos(ProcessorID.UNMARSHALER, unMarshallerIndex);
        posController.setPos(ProcessorID.BUSINESS_PROCESSOR, businessProcIndex);
        assertThat(posController.getPos(ProcessorID.INPUT_QUEUE_PROCESSOR), is(inputIndex));


    }

}